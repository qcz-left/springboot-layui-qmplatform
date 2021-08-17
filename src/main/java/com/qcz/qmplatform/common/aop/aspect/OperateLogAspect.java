package com.qcz.qmplatform.common.aop.aspect;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.system.domain.OperateLog;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.mapper.OperateLogMapper;
import com.qcz.qmplatform.module.system.service.UserService;
import com.qcz.qmplatform.module.system.vo.PasswordVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 操作日志 拦截
 *
 * @author changzhongq
 */
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private UserService userService;

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 线程池服务
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Pointcut("@annotation(com.qcz.qmplatform.common.aop.annotation.RecordLog)")
    public void operateLogPointcut() {

    }

    /**
     * 异常切入点
     */
    @Pointcut("execution(* com.qcz.qmplatform.module..*.controller..*.*(..))")
    public void operateExceptionLogPointCut() {

    }

    @Around(value = "operateLogPointcut()")
    public Object operateLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final HttpServletRequest request = getHttpServletRequest();
        String requestUrl = request.getServletPath();
        String ipAddress = HttpServletUtils.getIpAddress(request);
        Object proceed;
        User currentUser = SubjectUtils.getUser();
        try {
            proceed = joinPoint.proceed();
            executorService.submit(() -> {
                insertOperateLog(1, currentUser, null, requestUrl, ipAddress, joinPoint);
            });
        } catch (Exception e) {// 原逻辑程序有异常，这里抛回
            throw new CommonException(e.getMessage(), e);
        }
        return proceed;
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operateExceptionLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        final HttpServletRequest request = getHttpServletRequest();
        String requestUrl = request.getServletPath();
        String ipAddress = HttpServletUtils.getIpAddress(request);
        User currentUser = SubjectUtils.getUser();
        executorService.submit(() -> {
            String stackTrace = stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace());
            insertOperateLog(0, currentUser, stackTrace, requestUrl, ipAddress, joinPoint);
        });

    }

    /**
     * 插入操作日志
     *
     * @param operateStatus 操作状态
     * @param currentUser   当前操作人
     * @param expMsg        异常信息
     * @param requestUrl    请求路径
     * @param ipAddress     请求ip地址
     * @param joinPoint     切点
     */
    private void insertOperateLog(int operateStatus, User currentUser, String expMsg, String requestUrl, String ipAddress, JoinPoint joinPoint) {
        RecordLog recordLog = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RecordLog.class);
        String moduleName = null;
        String description = null;
        OperateType type = OperateType.INSTANCE;
        if (recordLog != null) {
            type = recordLog.type();
            switch (type) {
                case LOGIN:
                    moduleName = "登录系统";
                    PasswordVO formUser = (PasswordVO) joinPoint.getArgs()[0];
                    String loginname = formUser.getLoginname();
                    String password = formUser.getPassword();
                    currentUser = userService.findByLoginNameAndPassword(loginname, SecureUtils.simpleMD5(loginname, password));
                    if (currentUser == null) {
                        description = loginname + " 尝试登录系统，但失败了";
                    } else {
                        description = currentUser.getUsername() + " 进入了系统";
                    }
                    break;
                case LOGOUT:
                    moduleName = "退出系统";
                    description = currentUser.getUsername() + " 退出了系统";
                    break;
                default:
                    Module module = joinPoint.getTarget().getClass().getAnnotation(Module.class);
                    if (module != null) {
                        moduleName = module.value();
                    }
                    description = recordLog.description();
                    break;
            }
        }
        OperateLog log = new OperateLog();
        log.setOperateModule(moduleName);
        log.setLogId(IdUtils.simpleUUID());
        if (currentUser != null) {
            log.setOperateUserId(currentUser.getId());
            log.setOperateUserName(currentUser.getUsername());
        }
        log.setOperateTime(DateUtils.getCurrTimestamp());
        log.setOperateType(type.getType());
        log.setRequestUrl(requestUrl);
        log.setIpAddr(ipAddress);
        log.setOperateStatus(operateStatus);
        log.setDescription(description);
        log.setExpMsg(expMsg);

        operateLogMapper.insert(log);
    }

    /**
     * 获取request
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder strBuff = new StringBuilder();
        for (StackTraceElement stet : elements) {
            strBuff.append(stet).append("\n");
        }
        String stackTrace = exceptionName + ":" + exceptionMessage + "\n\t" + strBuff.toString();
        return StringUtils.omit(stackTrace, 999);
    }

}
