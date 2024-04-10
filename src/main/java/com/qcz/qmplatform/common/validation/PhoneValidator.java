package com.qcz.qmplatform.common.validation;

import com.qcz.qmplatform.common.utils.RegexPools;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * 手机号格式校验器
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.compile(RegexPools.PHONE).matcher(value).matches();
    }
}
