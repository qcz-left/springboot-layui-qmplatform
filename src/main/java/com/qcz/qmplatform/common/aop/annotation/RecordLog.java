package com.qcz.qmplatform.common.aop.annotation;

import com.qcz.qmplatform.common.aop.assist.OperateType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志方法注解
 *
 * @author changzhongq
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordLog {
    OperateType type();

    String description() default "";
}
