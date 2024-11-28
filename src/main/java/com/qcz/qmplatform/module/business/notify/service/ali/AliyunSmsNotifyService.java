package com.qcz.qmplatform.module.business.notify.service.ali;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.JSONUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

/**
 * 阿里云短信服务
 */
@Slf4j
public class AliyunSmsNotifyService implements INotifyService {

    /**
     * 接口请求域名
     */
    private static final String HOST = "dysmsapi.aliyuncs.com";

    private SmsConfig smsConfig;

    @Override
    public String send() {
        String responseHtml;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("PhoneNumbers", smsConfig.getPhones().get(0));
            params.put("SignName", smsConfig.getSign());
            params.put("TemplateCode", smsConfig.getTemplateID());
            params.put("TemplateParam", buildTemplateParams());

            responseHtml = sendHttpRequest("SendSms", params);
        } catch (Exception e) {
            log.error("send ali sms failed!", e);
            return e.getMessage();
        }

        JSONObject jsonResponse = JSONUtils.parseObj(responseHtml);
        return jsonResponse.getStr("Code");
    }

    /**
     * 构建模板参数
     */
    private String buildTemplateParams() {
        Map<String, String> templateParamMap = new HashMap<>();
        List<String> templateParams = smsConfig.getTemplateParams();
        List<String> templateParamNames = getTemplateParamNames(getTemplateContent());
        for (int i = 0; i < templateParams.size(); i++) {
            templateParamMap.put(templateParamNames.get(i), templateParams.get(i));
        }

        return JSONUtils.toJsonStr(templateParamMap);
    }

    /**
     * 获取模板内容
     */
    private String getTemplateContent() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("TemplateCode", smsConfig.getTemplateID());
        String templateDetail = sendHttpRequest("QuerySmsTemplate", params);
        return JSONUtils.parseObj(templateDetail).getStr("TemplateContent");
    }

    /**
     * 发送http请求
     *
     * @param action 请求动作
     * @param params 请求参数
     * @return 响应信息
     */
    private String sendHttpRequest(String action, Map<String, Object> params) {
        Map<String, Object> commonParams = getCommonParams(params, action);
        HttpRequest request = HttpUtil.createRequest(Method.POST, "https://" + HOST + "?" + HttpUtil.toParams(commonParams, StandardCharsets.UTF_8, true));
        request.header(Header.CONTENT_TYPE, ContentType.FORM_URLENCODED.getValue());
        request.form(params);

        HttpResponse response = request.execute();
        String responseHtml = response.body();
        log.debug("response result of the url [{} -> {}]: {}", HOST, action, JSONUtils.formatJsonStr(responseHtml));
        return responseHtml;
    }

    /**
     * 设置公共请参数
     *
     * @param params 请求参数
     * @param action API名称
     */
    private Map<String, Object> getCommonParams(Map<String, Object> params, String action) {
        Date date = new Date();
        String version = "2017-05-25"; // API版本号
        String timestamp = DateUtils.format(date, DateUtils.newSimpleFormat(DatePattern.UTC_PATTERN, null, new SimpleTimeZone(0, "GMT")));
        String signatureNonce = IdUtils.getUUID();

        Map<String, Object> common = new HashMap<>();
        common.put("Action", action);
        common.put("Version", version);
        common.put("Format", "json");
        common.put("AccessKeyId", smsConfig.getSecretId());
        common.put("SignatureNonce", signatureNonce);
        common.put("Timestamp", timestamp);
        common.put("SignatureMethod", "HMAC-SHA1");
        common.put("SignatureVersion", "1.0");
        Map<String, Object> signatureParams = new HashMap<>();
        signatureParams.putAll(params);
        signatureParams.putAll(common);
        String rpcSignature = getRPCSignature(signatureParams, smsConfig.getSecretKey());
        common.put("Signature", rpcSignature);

        return common;
    }

    private String getRPCSignature(Map<String, Object> signedParams, String secret) {
        if (signedParams != null && StringUtils.isNotBlank(secret)) {
            String[] sortedKeys = signedParams.keySet().toArray(new String[0]);
            Arrays.sort(sortedKeys);
            StringBuilder canonicalizedQueryString = new StringBuilder();

            for (String key : sortedKeys) {
                if (StringUtils.isNotBlank((String) signedParams.get(key))) {
                    canonicalizedQueryString.append("&").append(percentEncode(key)).append("=").append(percentEncode((String) signedParams.get(key)));
                }
            }

            String stringToSign = "POST" +
                    "&" +
                    percentEncode("/") +
                    "&" +
                    percentEncode(canonicalizedQueryString.substring(1));
            Mac mac = null;
            try {
                mac = Mac.getInstance("HmacSHA1");
                mac.init(new SecretKeySpec((secret + "&").getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            } catch (Exception e) {
                log.error(null, e);
            }
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return Base64.encode(signData);
        } else {
            return secret;
        }
    }

    private String percentEncode(String value) {
        return value != null ? URLEncoder.encode(value, StandardCharsets.UTF_8).replace(" ", "%20").replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
