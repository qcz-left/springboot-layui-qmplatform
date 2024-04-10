package com.qcz.qmplatform.config.freemarker;

import freemarker.template.SimpleCollection;
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
    private List keys;

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
        return new SimpleCollection(this.getKeys().iterator());
    }

    public TemplateCollectionModel values() {
        final Iterator iter = this.getKeys().iterator();
        return new SimpleCollection(new Iterator() {
            public boolean hasNext() {
                return iter.hasNext();
            }

            public Object next() {
                return MyHttpRequestParametersHashModel.this.request.getParameter((String) iter.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        });
    }

    protected String transcode(String string) {
        return string;
    }

    private synchronized List getKeys() {
        if (this.keys == null) {
            this.keys = new ArrayList();
            Enumeration enumeration = this.request.getParameterNames();

            while (enumeration.hasMoreElements()) {
                this.keys.add(enumeration.nextElement());
            }
        }

        return this.keys;
    }
}

