package com.qcz.qmplatform.common.log;

import ch.qos.logback.core.PropertyDefinerBase;
import com.qcz.qmplatform.common.utils.FileUtils;

public class WebHomeDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        return FileUtils.WEB_PATH;
    }
}
