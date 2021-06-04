package com.qcz.qmplatform.filter;

import cn.hutool.core.io.IoUtil;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestWrapper extends HttpServletRequestWrapper {

    private static final String ENC_REG = "ENC\\([a-zA-Z0-9\\+/=]+\\)";

    private static final Pattern ENC_PATTERN = Pattern.compile(ENC_REG);

    private final HttpServletRequest request;

    public RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        request = servletRequest;
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            String value = values[i];
            if (StringUtils.isBlank(value)) {
                encodedValues[i] = value;
            } else {
                encodedValues[i] = stripXSS(stripENC(value));
            }
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (StringUtils.isBlank(value)) {
            return value;
        }
        return stripXSS(stripENC(value));
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isBlank(value)) {
            return value;
        }
        return stripXSS(value);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String body;
        ServletInputStream stream;

        stream = request.getInputStream();
        body = IoUtil.read(stream, StandardCharsets.UTF_8);
        body = URLDecoder.decode(body, StandardCharsets.UTF_8.name());
        body = stripXSS(stripENC(body));

        byte[] buffer;
        buffer = body.getBytes(StandardCharsets.UTF_8);
        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    private String stripENC(String value) {
        Matcher matcher = ENC_PATTERN.matcher(value);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            matches.add(SecureUtils.rsaDecrypt(group.substring(4, group.length() - 1)));
        }
        if (!matches.isEmpty()) {
            value = value.replaceAll(ENC_REG, "{}");
            for (String match : matches) {
                value = StringUtils.format(value, match);
            }
        }
        return value;
    }

    private String stripXSS(String value) {
        // NOTE: It's highly recommended to use the ESAPI library and
        // uncomment the following line to
        // avoid encoded attacks.
        // value = ESAPI.encoder().canonicalize(value);
        // Avoid null characters
        value = value.replaceAll("", "");
        // Avoid anything between script tags
        Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid anything in a
        // src="http://www.yihaomen.com/article/java/..." type of
        // e­xpression
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Remove any lonesome </script> tag
        scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Remove any lonesome <script ...> tag
        scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid eval(...) e­xpressions
        scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid e­xpression(...) e­xpressions
        scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid javascript:... e­xpressions
        scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid vbscript:... e­xpressions
        scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");
        // Avoid onload= e­xpressions
        scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        scriptPattern = Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        return value;
    }
}
