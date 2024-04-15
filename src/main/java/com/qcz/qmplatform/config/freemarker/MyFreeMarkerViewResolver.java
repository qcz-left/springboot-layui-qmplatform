package com.qcz.qmplatform.config.freemarker;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class MyFreeMarkerViewResolver extends FreeMarkerViewResolver {

    public MyFreeMarkerViewResolver() {
        setViewClass(requiredViewClass());
    }

    public MyFreeMarkerViewResolver(String prefix, String suffix) {
        this();
        setPrefix(prefix);
        setSuffix(suffix);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return MyFreeMarkerView.class;
    }

    @Override
    protected AbstractUrlBasedView instantiateView() {
        return (getViewClass() == MyFreeMarkerView.class ? new MyFreeMarkerView() : super.instantiateView());
    }
}
