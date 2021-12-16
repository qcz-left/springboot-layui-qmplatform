package com.qcz.qmplatform.common.aop.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Header;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class RequestAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger("");

    @Pointcut("execution(* com.qcz.qmplatform.module..*.controller..*.*(..))")
    public void requestPointcut() {

    }

    /**
     * 打印请求参数
     *
     * @param joinPoint
     */
    @Before(value = "requestPointcut()")
    public void paramsLog(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        LOGGER.info("【start】 - [{}] {}", request.getMethod(), request.getRequestURL().toString());
        LOGGER.info("{}", joinPoint.getSignature());
        Map<String, String> paramMap = ServletUtil.getParamMap(request);

        String param = "";
        String body = "";

        if (!paramMap.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String paramKey : paramMap.keySet()) {
                String value = paramMap.get(paramKey);
                sb.append(StringUtils.format("\n    {}: {}", paramKey, checkPwdFieldHidden(paramKey, value)));
            }
            param = StringUtils.format("\n  [param] ==>{}\n", sb.toString());
        }

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Annotation[][] allParameterAnnotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                Annotation[] itemParameterAnnotation = allParameterAnnotations[i];
                for (Annotation annotation : itemParameterAnnotation) {
                    if (annotation.annotationType() == RequestBody.class) {
                        Class<?> argClass = arg.getClass();
                        Map argMap = BeanUtil.beanToMap(arg);
                        if (arg instanceof Map) {
                            argMap = (Map) arg;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (Object paramKey : argMap.keySet()) {
                            Object paramValue = argMap.get(paramKey);
                            sb.append(StringUtils.format("\n    {}: {}", paramKey, checkPwdFieldHidden(String.valueOf(paramKey), paramValue)));
                        }
                        body = StringUtils.format("\n  [body] {} ==>{}\n", argClass.getSimpleName(), sb.toString());
                    }
                }
            }
        }
        LOGGER.info("<<{}>> {}{}{}", HttpServletUtils.getIpAddress(request), request.getHeader(Header.USER_AGENT.toString()), param, body);
    }

    @Around(value = "requestPointcut()")
    public Object requestAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = DateUtil.current(false);
        Object proceed = joinPoint.proceed();
        long endTime = DateUtil.current(false);
        LOGGER.info("【 end 】 - cost: {} ms", endTime - startTime);
        return proceed;
    }

    /**
     * 检查参数名称是否密码字段，是则隐藏，不打印到日志里面
     *
     * @param fieldName  参数名称
     * @param fieldValue 参数值
     * @return 处理后的参数值，是密码字段则只打印字符长度
     */
    private String checkPwdFieldHidden(String fieldName, Object fieldValue) {
        String strFieldValue = String.valueOf(fieldValue);
        if (fieldValue instanceof Object[]) {
            Object[] arrFieldValue = (Object[]) fieldValue;
            strFieldValue = StringUtils.format("[{}]", ArrayUtil.join(arrFieldValue, ","));
        }
        strFieldValue = StringUtils.isNullOrUndefined(strFieldValue) ? "" : strFieldValue;
        List<String> pwdFields = ConfigLoader.getPwdFields();
        for (String pwdField : pwdFields) {
            if (StringUtils.containsIgnoreCase(fieldName, pwdField)) {
                return StringUtils.format("({})", strFieldValue.length());
            }
        }
        return StringUtils.omit(strFieldValue, 99);
    }

}
