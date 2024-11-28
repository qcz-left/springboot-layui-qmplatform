package com.qcz.qmplatform.module.business.notify.service.tencent;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.util.HexUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.JSONUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import com.qcz.qmplatform.module.business.notify.service.tencent.bean.TencentCloudSmsError;
import com.qcz.qmplatform.module.business.notify.service.tencent.bean.TencentCloudSmsResponse;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

/**
 * 腾讯云短信服务
 */
@Slf4j
public class TencentCloudSmsNotifyService implements INotifyService {

    private SmsConfig smsConfig;

    /**
     * 接口请求域名
     */
    private static final String HOST = "sms.tencentcloudapi.com";

    @Override
    public String send() {
        return sendByOldVersion();
    }

    /**
     * 新版短信发送接口
     */
    private String sendByNewVersion() {
        String responseHtml;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("SmsSdkAppId", smsConfig.getAppId());
            params.put("SignName", smsConfig.getSign());
            params.put("TemplateId", smsConfig.getTemplateID());
            params.put("TemplateParamSet", smsConfig.getTemplateParams().values());
            params.put("PhoneNumberSet", smsConfig.getPhones());

            HttpRequest request = HttpUtil.createRequest(Method.POST, "https://" + HOST);
            request.header("Host", HOST);
            request.header("Content-Type", "application/json; charset=utf-8");
            setCommonHeader(request, params);
            request.body(JSONUtils.toJsonStr(params));

            HttpResponse response = request.execute();
            responseHtml = response.body();
        } catch (Exception e) {
            log.error("send tx sms failed!", e);
            return e.getMessage();
        }

        log.info("response result of the url [" + HOST + "]: " + JSONUtils.formatJsonStr(responseHtml));
        JSONObject responseJSONObject = JSONUtils.parseObj(responseHtml);
        TencentCloudSmsResponse response = responseJSONObject.get("Response", TencentCloudSmsResponse.class);
        TencentCloudSmsError error = response.getError();
        if (Objects.nonNull(error)) {
            return error.getCode();
        }
        return response.getSendStatusSet().get(0).getCode();
    }

    /**
     * 旧版短信发送接口
     */
    private String sendByOldVersion() {
        String phoneNumber = smsConfig.getPhones().get(0);
        int templateId = Integer.parseInt(smsConfig.getTemplateID());
        ArrayList<String> params = new ArrayList<>(smsConfig.getTemplateParams().values());
        String sign = smsConfig.getSign();
        try {
            String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";
            long now = DateUtils.currentSeconds();
            long random = (new Random(now)).nextInt(900000) + 100000L;

            Map<String, Object> body = new HashMap<>();
            Map<String, Object> tel = new HashMap<>();
            tel.put("nationcode", "86");
            tel.put("mobile", phoneNumber);
            String sig = "appkey=" + smsConfig.getAppKey() +
                    "&random=" + random +
                    "&time=" + now +
                    "&mobile=" + phoneNumber;
            body.put("tel", tel);
            body.put("sig", SecureUtils.sha256Hex(sig));
            body.put("tpl_id", templateId);
            body.put("params", params);
            body.put("sign", sign);
            body.put("time", now);

            HttpRequest request = HttpUtil.createRequest(Method.POST, url + "?sdkappid=" + smsConfig.getAppId() + "&random=" + random);
            request.timeout(60 * 1000);
            request.header("Content-Type", "application/json; charset=utf-8");
            request.body(JSONUtils.toJsonStr(body));

            HttpResponse response = request.execute();
            String responseHtml = response.body();
            log.info("response result of the url [" + url + "]: " + JSONUtils.formatJsonStr(responseHtml));
            Map responseMap = JSONUtils.toBean(responseHtml, Map.class);
            int code = (int) responseMap.get("result");
            return code == 0 ? "ok" : String.valueOf(code);
        } catch (Exception e) {
            log.error("send tx sms failed!", e);
        }

        return null;
    }

    /**
     * 设置公共请求头
     *
     * @param params 请求参数
     */
    private void setCommonHeader(HttpRequest request, Map<String, Object> params) {
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json; charset=utf-8\n" + "host:" + HOST + "\n";
        String signedHeaders = "content-type;host";
        String algorithm = "TC3-HMAC-SHA256";
        String service = "sms";
        String requestSign = "tc3_request";

        request.header("X-TC-Action", "SendSms");
        request.header("X-TC-Region", "ap-guangzhou");
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime() / 1000);
        request.header("X-TC-Timestamp", timestamp);
        request.header("X-TC-Version", "2021-01-11");

        // 计算签名
        String utcDate = DateUtils.format(date, FastDateFormat.getInstance(DatePattern.NORM_DATE_PATTERN, TimeZone.getTimeZone("UTC")));
        byte[] secretDate = SecureUtils.hmac256(("TC3" + smsConfig.getSecretKey()).getBytes(StandardCharsets.UTF_8), utcDate);
        byte[] secretService = SecureUtils.hmac256(secretDate, service);
        byte[] secretSigning = SecureUtils.hmac256(secretService, requestSign);
        String credentialScope = utcDate + "/" + service + "/" + requestSign;
        String payload = JSONUtils.toJsonStr(params);
        String hashedRequestPayload = SecureUtils.sha256Hex(payload);
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n"
                + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        String hashedCanonicalRequest = SecureUtils.sha256Hex(canonicalRequest);
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        String signature = HexUtil.encodeHexStr(SecureUtils.hmac256(secretSigning, stringToSign)).toLowerCase();
        String authorization = algorithm + " " + "Credential=" + smsConfig.getSecretId() + "/" + credentialScope + ", "
                + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;
        request.header("Authorization", authorization);
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
