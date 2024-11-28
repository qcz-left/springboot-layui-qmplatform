package com.qcz.qmplatform.module.business.notify.service;

import cn.hutool.core.util.ReUtil;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 通知服务
 */
public interface INotifyService {

    Logger LOGGER = LoggerFactory.getLogger(INotifyService.class);

    /**
     * 发送
     *
     * @return 状态码code
     */
    String send();

    /**
     * 设置短信网关参数，大多可以从控制台界面获取
     *
     * @param smsConfig the config of sms
     */
    default void setSmsConfig(SmsConfig smsConfig) {

    }

    /**
     * 模板内容变量名称开始位置
     */
    default String templateVarStart() {
        return "${";
    }

    /**
     * 模板内容变量名称结束位置
     */
    default String templateVarEnd() {
        return "}";
    }

    /**
     * 获取模板参数名称列表
     *
     * @param templateContent 模板内容
     */
    default List<String> getTemplateParamNames(String templateContent) {
        List<String> templateParamNames = new ArrayList<>();
        String templateVarStart = templateVarStart();
        String templateVarEnd = templateVarEnd();
        String templateVarRegexStr = StringUtils.format("{}[a-zA-Z0-9]+{}", ReUtil.escape(templateVarStart), ReUtil.escape(templateVarEnd));
        ReUtil.findAll(Pattern.compile(templateVarRegexStr), templateContent, matcher -> {
            String group = matcher.group();
            String varName = group.substring(templateVarStart.length(), group.length() - templateVarEnd.length());
            LOGGER.debug("get template params name: " + varName);
            templateParamNames.add(varName);
        });

        return templateParamNames;
    }

}
