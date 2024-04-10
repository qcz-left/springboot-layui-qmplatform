package com.qcz.qmplatform.common.aop.aspect;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.mail.MailException;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.ServletUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.business.system.domain.OperateLog;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.domain.dto.PasswordDTO;
import com.qcz.qmplatform.module.business.system.mapper.OperateLogMapper;
import com.qcz.qmplatform.module.business.system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;

/**
 * 操作日志 拦截
 *
 * @author changzhongq
 */
@Aspect
@Component
public class OperateLogAspect {

    @Resource
    private UserService userService;

    @Resource
    private OperateLogMapper operateLogMapper;

    /**
     * 线程池服务
     */
    private final ExecutorService executorService = ThreadUtil.newExecutor(50);

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
        final HttpServletRequest request = ServletUtils.getCurrRequest();
        String requestUrl = request.getServletPath();
        String ipAddress = ServletUtils.getIpAddress(request);
        Object proceed;
        User currentUser = SubjectUtils.getUser(false);
        Timestamp currTimestamp = DateUtils.getCurrTimestamp();
        try {
            proceed = joinPoint.proceed();
            executorService.submit(() -> insertOperateLog(1, currentUser, null, requestUrl, ipAddress, joinPoint, currTimestamp));
        } catch (Exception e) {// 原逻辑程序有异常，这里抛回
            String msg;
            if (e instanceof MailException && StringUtils.containsAny(e.getMessage(), "AuthenticationFailedException", "535")) {
                msg = "邮箱账号验证失败";
            } else {
                msg = e.getMessage();
            }
            throw new CommonException(msg, e);
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
        final HttpServletRequest request = ServletUtils.getCurrRequest();
        String requestUrl = request.getServletPath();
        String ipAddress = ServletUtils.getIpAddress(request);
        User currentUser = SubjectUtils.getUser(false);
        Timestamp currTimestamp = DateUtils.getCurrTimestamp();
        executorService.submit(() -> {
            String stackTrace = stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace());
            insertOperateLog(0, currentUser, stackTrace, requestUrl, ipAddress, joinPoint, currTimestamp);
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
     * @param operateTime   操作时间
     */
    private void insertOperateLog(int operateStatus, User currentUser, String expMsg, String requestUrl, String ipAddress, JoinPoint joinPoint, Timestamp operateTime) {
        RecordLog recordLog = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RecordLog.class);
        String moduleName = null;
        String description = null;
        OperateType type = OperateType.INSTANCE;
        if (recordLog != null) {
            type = recordLog.type();
            switch (type) {
                case LOGIN -> {
                    moduleName = "登录系统";
                    PasswordDTO formUser = (PasswordDTO) joinPoint.getArgs()[0];
                    String loginName = formUser.getLoginname();
                    String password = formUser.getPassword();
                    currentUser = userService.queryUserByName(loginName);
                    if (currentUser == null || !SecureUtils.accountCheck(password, currentUser.getPassword())) {
                        description = loginName + " 尝试登录系统，但失败了";
                    } else {
                        description = currentUser.getUsername() + " 进入了系统";
                    }
                }
                case LOGOUT -> {
                    moduleName = "退出系统";
                    description = currentUser.getUsername() + " 退出了系统";
                }
                default -> {
                    Module module = joinPoint.getTarget().getClass().getAnnotation(Module.class);
                    if (module != null) {
                        moduleName = module.value();
                    }
                    description = recordLog.description();
                }
            }
        }
        OperateLog log = new OperateLog();
        log.setOperateModule(moduleName);
        log.setLogId(IdUtils.getUUID());
        if (currentUser != null) {
            log.setOperateUserId(currentUser.getId());
            log.setOperateUserName(currentUser.getUsername());
        }
        log.setOperateTime(operateTime);
        log.setOperateType(type.getType());
        log.setRequestUrl(requestUrl);
        log.setIpAddr(ipAddress);
        log.setOperateStatus(operateStatus);
        log.setDescription(description);
        log.setExpMsg(expMsg);

        operateLogMapper.insert(log);
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
        String stackTrace = exceptionName + ":" + exceptionMessage + "\n\t" + strBuff;
        return StringUtils.omit(stackTrace, 999);
    }

}
