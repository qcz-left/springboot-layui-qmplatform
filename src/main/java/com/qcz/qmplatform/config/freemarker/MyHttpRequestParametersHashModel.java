package com.qcz.qmplatform.config.freemarker;

import freemarker.template.Configuration;
import freemarker.template.SimpleCollection;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class MyHttpRequestParametersHashModel implements TemplateHashModelEx {
    private final HttpServletRequest request;
    private List<String> keys;
    public static final SimpleObjectWrapper SAFE_OBJECT_WRAPPER;

    static {
        SAFE_OBJECT_WRAPPER = new SimpleObjectWrapper(Configuration.VERSION_2_3_0);
        SAFE_OBJECT_WRAPPER.writeProtect();
    }

    public MyHttpRequestParametersHashModel(HttpServletRequest request) {
        this.request = request;
    }

    public TemplateModel get(String key) {
        String value = this.request.getParameter(key);
        return value == null ? null : new SimpleScalar(value);
    }

    public boolean isEmpty() {
        return !this.request.getParameterNames().hasMoreElements();
    }

    public int size() {
        return this.getKeys().size();
    }

    public TemplateCollectionModel keys() {
        return new SimpleCollection(this.getKeys().iterator(), SAFE_OBJECT_WRAPPER);
    }

    public TemplateCollectionModel values() {
        final Iterator<String> iter = this.getKeys().iterator();
        return new SimpleCollection(new Iterator<>() {
            public boolean hasNext() {
                return iter.hasNext();
            }

            public Object next() {
                return MyHttpRequestParametersHashModel.this.request.getParameter(iter.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }, SAFE_OBJECT_WRAPPER);
    }

    private synchronized List<String> getKeys() {
        if (this.keys == null) {
            this.keys = new ArrayList<>();
            Enumeration<String> enumeration = this.request.getParameterNames();

            while (enumeration.hasMoreElements()) {
                this.keys.add(enumeration.nextElement());
            }
        }

        return this.keys;
    }
}

