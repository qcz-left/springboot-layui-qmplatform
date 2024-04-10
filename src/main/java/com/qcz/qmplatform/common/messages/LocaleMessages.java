package com.qcz.qmplatform.common.messages;

import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化
 */
@Component
public class LocaleMessages {

    private LocaleMessages() {

    }

    @Resource
    private MessageSource messageSource;

    /**
     * 获取国际化
     *
     * @param code 国际化码
     */
    public String getMessage(String code) {
        return this.getMessage(code, new Object[]{}, LocaleContextHolder.getLocale());
    }

    /**
     * 获取国际化
     *
     * @param code           国际化码
     * @param defaultMessage code中找不到对应国际化信息时默认的国际化
     */
    public String getMessage(String code, String defaultMessage) {
        return this.getMessage(code, null, defaultMessage);
    }

    /**
     * 获取国际化
     *
     * @param code           国际化码
     * @param defaultMessage code中找不到对应国际化信息时默认的国际化
     * @param locale         语言对象
     */
    public String getMessage(String code, String defaultMessage, Locale locale) {
        return this.getMessage(code, null, defaultMessage, locale);
    }

    /**
     * 获取国际化
     *
     * @param code   国际化码
     * @param locale 语言对象
     */
    public String getMessage(String code, Locale locale) {
        return this.getMessage(code, null, "", locale);
    }

    /**
     * 获取国际化
     *
     * @param code 国际化码
     * @param args code中的参数
     */
    public String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, "");
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return this.getMessage(code, args, "", locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(code, args, defaultMessage, locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

}
