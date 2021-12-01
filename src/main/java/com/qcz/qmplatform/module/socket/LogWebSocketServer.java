package com.qcz.qmplatform.module.socket;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.qcz.qmplatform.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@ServerEndpoint("/socket/log")
@Component
public class LogWebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogWebSocketServer.class);

    private Process process;
    private InputStream inputStream;

    @OnOpen
    public void onOpen(Session session) {
        process = RuntimeUtil.exec("tail -f " + FileUtils.WEB_PATH + "/logs/qmplatform.log");
        inputStream = process.getInputStream();
        LogThread logThread = new LogThread(session);
        logThread.start();
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.debug("onClose: " + session.getId());
        IoUtil.close(inputStream);
        if (process != null) {
            process.destroy();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        onClose(session);
        LOGGER.error("onError: ", throwable);
    }

    class LogThread extends Thread {

        private BufferedReader bufferedReader;
        private Session session;

        private LogThread(Session session) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            this.session = session;
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    session.getBasicRemote().sendText(line);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }

    }

}
