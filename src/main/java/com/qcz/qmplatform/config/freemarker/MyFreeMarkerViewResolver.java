package com.qcz.qmplatform.config.freemarker;

import org.jetbrains.annotations.NotNull;
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

    @NotNull
    @Override
    protected Class<?> requiredViewClass() {
        return MyFreeMarkerView.class;
    }

    @NotNull
    @Override
    protected AbstractUrlBasedView instantiateView() {
        return (getViewClass() == MyFreeMarkerView.class ? new MyFreeMarkerView() : super.instantiateView());
    }
}
