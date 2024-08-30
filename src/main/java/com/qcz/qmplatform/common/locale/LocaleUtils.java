package com.qcz.qmplatform.common.locale;

import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化
 */
@Component
public class LocaleUtils {

    private static MessageSource messageSource;

    @Resource
    public void setMessageSource(MessageSource messageSource) {
        LocaleUtils.messageSource = messageSource;
    }

    private LocaleUtils() {

    }

    /**
     * 获取国际化
     *
     * @param code 国际化码
     */
    public static String getMessage(String code) {
        return getMessage(code, new Object[]{}, LocaleContextHolder.getLocale());
    }

    /**
     * 获取国际化
     *
     * @param code   国际化码
     * @param locale 语言对象
     */
    public static String getMessage(String code, Locale locale) {
        return getMessage(code, null, locale);
    }

    /**
     * 获取国际化
     *
     * @param code 国际化码
     * @param args 国际化参数
     */
    public static String getMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(code, args, locale);
    }

    /**
     * 获取国际化
     *
     * @param code   国际化码
     * @param args   国际化参数
     * @param locale 语言对象
     */
    public static String getMessage(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, "", locale);
    }

}
