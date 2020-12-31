package com.qcz.qmplatform.common.aop.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
public class RequestAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAspect.class);

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
        LOGGER.info("[{}] {}", request.getMethod(), request.getRequestURL().toString());
        LOGGER.info("{}", joinPoint.getSignature());
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        for (String paramKey : paramMap.keySet()) {
            LOGGER.info("{}: {}", paramKey, paramMap.get(paramKey));
        }
    }

    @Around(value = "requestPointcut()")
    public Object requestAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = DateUtil.current(false);
        Object proceed = joinPoint.proceed();
        long endTime = DateUtil.current(false);
        LOGGER.info("cost: {} ms", endTime - startTime);
        return proceed;
    }
}
