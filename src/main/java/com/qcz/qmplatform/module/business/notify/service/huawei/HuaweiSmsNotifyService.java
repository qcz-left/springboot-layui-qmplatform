package com.qcz.qmplatform.module.business.notify.service.huawei;

import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HuaweiSmsNotifyService implements INotifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HuaweiSmsNotifyService.class);

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
            String body = buildRequestBody(smsConfig.getChannelNumber(), phone, templateId, templateParas, null, smsConfig.getSign());
            if (null == body || body.isEmpty()) {
                LOGGER.info("body is null.");
                return "bodyIsNull";
            }

            // 请求Headers中的X-WSSE参数值
            String wsseHeader = buildWsseHeader(smsConfig.getSecretId(), smsConfig.getSecretKey());
            if (null == wsseHeader || wsseHeader.isEmpty()) {
                LOGGER.info("wsse header is null.");
                return "wsseHeaderIsNull";
            }

            // 为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
            CloseableHttpClient client = HttpClients.custom().setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build()).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

            HttpResponse response = client.execute(RequestBuilder.create("POST")// 请求方法POST
                    .setUri(SERVER_ADDRESS).addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded").addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE).addHeader("X-WSSE", wsseHeader).setEntity(new StringEntity(body)).build());

            String httpEntity = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            LOGGER.info("send to " + phone + " result:" + httpEntity);
            if (statusCode == HttpStatus.SC_OK) {
                String code = (String) JSONUtil.toBean(httpEntity, Map.class).get("code");
                return StringUtils.equals(code, "000000") ? "ok" : code;
            }
        } catch (Exception e) {
            LOGGER.error(null, e);
        }

        return null;
    }

    /**
     * 构造请求Body体
     *
     * @param sender            通道号
     * @param receiver          接收短信的手机号
     * @param templateId        模板ID
     * @param templateParas     模板参数
     * @param statusCallbackUrl 短信状态报告接收地址，推荐使用域名，为空或者不填表示不接收状态报告
     * @param signature         | 签名名称,使用国内短信通用模板时填写
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas, String statusCallbackUrl, String signature) {
        if (null == receiver || null == templateId || receiver.isEmpty() || templateId.isEmpty()) {
            LOGGER.info("buildRequestBody(): receiver or templateId is null.");
            return null;
        }
        List<NameValuePair> keyValues = new ArrayList<>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }
        if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
            keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        }
        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, StandardCharsets.UTF_8);
    }

    /**
     * 构造X-WSSE参数值
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            LOGGER.info("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date()); // Created
        String nonce = UUID.randomUUID().toString().replace("-", ""); // Nonce

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);

        String passwordDigestBase64Str = Base64.encodeBase64String(hexDigest.getBytes(StandardCharsets.UTF_8)); // PasswordDigest
        // 若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
        passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");

        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
