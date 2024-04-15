package com.qcz.qmplatform.module.business.notify.service.huawei;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class HuaweiSmsNotifyService implements INotifyService {

    // 无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    // 无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
    // 短信API请求服务地址
    private static final String SERVER_ADDRESS = ConfigLoader.getHwSmsServerAddressConfig();

    private SmsConfig smsConfig;

    @Override
    public String send() {
        try {
            String templateId = smsConfig.getTemplateID(); // 模板ID

            Map<String, String> templateParamsMap = smsConfig.getTemplateParams();
            StringBuilder sbparams = new StringBuilder();
            sbparams.append("[");
            for (int i = 0; i < templateParamsMap.size(); i++) {
                sbparams.append("\"").append(templateParamsMap.get(i + 1 + "")).append("\"");
                if (i < templateParamsMap.size() - 1) {
                    sbparams.append(",");
                }
            }
            sbparams.append("]");
            /*
             * 选填,使用无变量模板时请赋空值 String templateParas = ""; 单变量模板示例:模板内容为"您的验证码是${1}"时,templateParas可填写为"[\"369751\"]" 双变量模板示例:模板内容为"您有${1}件快递请到${2}领取"时, templateParas可填写为"[\"3\",\"人民公园正门\"]" 模板中的每个变量都必须赋值，且取值不能为空
             */
            String templateParas = sbparams.toString();

            String phone = smsConfig.getPhones().get(0);
            // 请求Body,不携带签名名称时,signature请填null
            Map<String, Object> body = buildRequestBody(smsConfig.getChannelNumber(), phone, templateId, templateParas, smsConfig.getSign());
            if (null == body || body.isEmpty()) {
                log.info("body is null.");
                return "bodyIsNull";
            }

            // 请求Headers中的X-WSSE参数值
            String wsseHeader = buildWsseHeader(smsConfig.getSecretId(), smsConfig.getSecretKey());
            if (null == wsseHeader || wsseHeader.isEmpty()) {
                log.info("wsse header is null.");
                return "wsseHeaderIsNull";
            }

            HttpRequest request = HttpUtil.createRequest(Method.POST, SERVER_ADDRESS);
            request.header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded");
            request.header(Header.AUTHORIZATION, AUTH_HEADER_VALUE);
            request.header("X-WSSE", wsseHeader);
            request.form(body);

            HttpResponse response = request.execute();
            String responseHtml = response.body();

            log.info("send to " + phone + " result:" + responseHtml);
            int statusCode = response.getStatus();
            if (statusCode == HttpStatus.HTTP_OK) {
                String code = (String) JSONUtil.toBean(responseHtml, Map.class).get("code");
                return StringUtils.equals(code, "000000") ? "ok" : code;
            }
        } catch (Exception e) {
            log.error(null, e);
        }

        return null;
    }

    /**
     * 构造请求Body体
     *
     * @param sender        通道号
     * @param receiver      接收短信的手机号
     * @param templateId    模板ID
     * @param templateParas 模板参数
     * @param signature     | 签名名称,使用国内短信通用模板时填写
     */
    static Map<String, Object> buildRequestBody(String sender, String receiver, String templateId, String templateParas, String signature) {
        if (null == receiver || null == templateId || receiver.isEmpty() || templateId.isEmpty()) {
            log.info("buildRequestBody(): receiver or templateId is null.");
            return null;
        }
        Map<String, Object> body = new HashMap<>();
        body.put("from", sender);
        body.put("to", receiver);
        body.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            body.put("templateParas", templateParas);
        }
        if (null != signature && !signature.isEmpty()) {
            body.put("signature", signature);
        }

        return body;
    }

    /**
     * 构造X-WSSE参数值
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            log.info("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.UTC_PATTERN);
        String time = sdf.format(new Date()); // Created
        String nonce = UUID.randomUUID().toString().replace("-", "");

        String hexDigest = DigestUtil.sha256Hex(nonce + time + appSecret);

        String passwordDigestBase64Str = Base64.encode(hexDigest.getBytes(StandardCharsets.UTF_8));
        // 若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
        passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");

        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
