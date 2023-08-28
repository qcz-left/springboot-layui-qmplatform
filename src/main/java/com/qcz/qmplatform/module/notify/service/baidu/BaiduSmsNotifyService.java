package com.qcz.qmplatform.module.notify.service.baidu;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.notify.service.INotifyService;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiduSmsNotifyService implements INotifyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaiduSmsNotifyService.class);

    private SmsConfig smsConfig;

    private static final BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static final String[] PERCENT_ENCODED_STRINGS = new String[256];
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final String HTTP_BASE_URL = "http://smsv3.bj.baidubce.com";

    /**
     * 缓存模板变量名称，如果修改了模板变量名称，需要重启重新读取
     */
    private static List<String> templateParamNames;
    /**
     * 上一次读取到的模板ID
     */
    private static String lastTemplateId;

    @Override
    public String send() {
        String signatureId = smsConfig.getSign();
        String templateId = smsConfig.getTemplateID();

        addTemplateParamNames(templateId);

        Map<String, Object> contentVar = new HashMap<>();
        int paramsSize = templateParamNames.size();
        if (paramsSize > 0) {
            if (paramsSize != smsConfig.getTemplateParamCnt()) {
                LOGGER.warn("The number of template parameters does not match!");
            }

            for (int i = 0; i < paramsSize; i++) {
                contentVar.put(templateParamNames.get(i), smsConfig.getTemplateParams().get(i + 1 + ""));
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", smsConfig.getPhones().get(0));
        params.put("template", templateId);
        params.put("signatureId", signatureId);
        params.put("contentVar", contentVar);
        String responseHtml = sendHttpRequest(HTTP_BASE_URL + "/api/v3/sendsms", Method.POST, params);
        String code = (String) JSONUtil.toBean(responseHtml, Map.class).get("code");
        return "1000".equals(code) ? "ok" : String.valueOf(code);
    }

    private void addTemplateParamNames(String templateId) {
        if (!StringUtils.equals(lastTemplateId, templateId)) {
            if (templateParamNames == null) {
                templateParamNames = new ArrayList<>();
            } else {
                templateParamNames.clear();
            }

            String responseHtml = sendHttpRequest(HTTP_BASE_URL + "/sms/v3/template/" + templateId, Method.GET, null);

            String content = (String) JSONUtil.toBean(responseHtml, Map.class).get("content");
            if (StringUtils.isBlank(content)) {
                throw new RuntimeException("The template content cannot be empty!");
            }
            Matcher matcher = Pattern.compile("\\$\\{[a-zA-Z0-9]+}").matcher(content);
            while (matcher.find()) {
                String group = matcher.group();
                String name = group.substring(group.indexOf("{") + 1, group.lastIndexOf("}"));
                templateParamNames.add(name);
                LOGGER.debug("[BaiduSms] get template params name: " + name);
            }

            lastTemplateId = templateId;
        }
    }

    /**
     * 发送http请求
     *
     * @param url           http url
     * @param requestMethod 请求方式 GET or POST
     * @param bodyParams    body请求参数，会以json形式传递，如果是GET请求，不要传这个值，置为null
     * @return 响应内容
     */
    private String sendHttpRequest(String url, Method requestMethod, Map<String, Object> bodyParams) {
        String utcTime = getUTCTime();
        String responseHtml = null;
        try {
            HttpRequest request = HttpUtil.createRequest(requestMethod, url);

            SortedMap<String, String> headers = new TreeMap<>();
            headers.put("Host", "smsv3.bj.baidubce.com");
            headers.put("x-bce-date", utcTime);

            for (String key : headers.keySet()) {
                request.header(key, headers.get(key));
            }
            request.header("Content-Type", "application/json; charset=UTF-8");

            /*
             * 生成认证字符串
             */
            String authString = "bce-auth-v1/" + smsConfig.getSecretId() + "/" + utcTime + "/1800";
            String canonicalRequest = requestMethod + "\n" + getCanonicalURIPath(new URI(url).getPath()) + "\n\n" + getCanonicalHeaders(headers);
            String signingKey = sha256Hex(smsConfig.getSecretKey(), authString);
            String signature = sha256Hex(signingKey, canonicalRequest);
            String signedHeaders = "host;x-bce-date";
            String authorizationHeader = authString + "/" + signedHeaders + "/" + signature;

            request.header("Authorization", authorizationHeader);

            if (bodyParams != null) {
                request.body(JSONUtil.toJsonStr(bodyParams));
            }

            HttpResponse response = request.execute();
            responseHtml = response.body();
        } catch (Exception e) {
            LOGGER.error("[BaiduSms] Failed to call the HTTP interface: " + url, e);
        }

        LOGGER.debug("response result of the url [" + url + "]: " + responseHtml);

        return responseHtml;
    }

    /*
     * 以下所有方法均从百度云SDK源码复制过来
     */

    private String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes(UTF8))));
        } catch (Exception e) {
            throw new RuntimeException("Fail to generate the signature", e);
        }
    }

    private String getUTCTime() {
        SimpleDateFormat utcTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        utcTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return utcTimeFormat.format(new Date());
    }

    private String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        } else {
            List<String> headerStrings = new ArrayList<>();

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                if (key != null) {
                    String value = entry.getValue();
                    if (value == null) {
                        value = "";
                    }

                    headerStrings.add(normalize(key.trim().toLowerCase()) + ':' + normalize(value.trim()));
                }
            }

            Collections.sort(headerStrings);
            return StringUtils.join("\n", headerStrings);
        }
    }

    private String normalize(String value) {
        StringBuilder builder = new StringBuilder();
        byte[] valueBytes = value.getBytes(UTF8);
        for (byte b : valueBytes) {
            if (URI_UNRESERVED_CHARACTERS.get(b & 255)) {
                builder.append((char) b);
            } else {
                builder.append(PERCENT_ENCODED_STRINGS[b & 255]);
            }
        }

        return builder.toString();
    }

    private String getCanonicalURIPath(String path) {
        if (path == null) {
            return "/";
        } else {
            return path.startsWith("/") ? normalizePath(path) : "/" + normalizePath(path);
        }
    }

    public String normalizePath(String path) {
        return normalize(path).replace("%2F", "/");
    }

    static {
        int i;
        for (i = 97; i <= 122; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        for (i = 65; i <= 90; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        for (i = 48; i <= 57; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        URI_UNRESERVED_CHARACTERS.set(45);
        URI_UNRESERVED_CHARACTERS.set(46);
        URI_UNRESERVED_CHARACTERS.set(95);
        URI_UNRESERVED_CHARACTERS.set(126);

        for (i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
