package com.qcz.qmplatform.module.listen;

import com.qcz.qmplatform.common.log.LogUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 监听程序启动和停止
 */
@Component
public class ApplicationListener implements ApplicationRunner {

    /**
     * 程序启动后执行
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        LogUtils.resetLogLevel();
    }
}
