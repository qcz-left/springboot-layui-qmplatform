package com.qcz.qmplatform.common.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qcz.qmplatform.common.aop.assist.OperateType;

/**
 * 自定义方法注解
 * @author changzhongq
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomOperateLog {
	OperateType type();
}
