package com.qcz.qmplatform.common.validation;

import com.qcz.qmplatform.common.utils.IpUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * IP地址格式校验器
 */
public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return IpUtils.isIp(value);
    }
}
