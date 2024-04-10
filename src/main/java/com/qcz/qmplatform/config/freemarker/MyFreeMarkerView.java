package com.qcz.qmplatform.config.freemarker;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.SimpleHash;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import java.util.Map;

/**
 * 适配兼容 jakarta
 */
public class MyFreeMarkerView extends FreeMarkerView {

    @NotNull
    @Override
    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        fmModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new MyHttpRequestParametersHashModel(request));
        return fmModel;
    }

}
