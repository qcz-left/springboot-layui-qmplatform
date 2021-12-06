package com.qcz.qmplatform.module.socket;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.qcz.qmplatform.common.exception.CommonException;
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
import java.util.List;


@ServerEndpoint("/socket/log")
@Component
public class LogWebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogWebSocketServer.class);

    private Process process;
    private InputStream inputStream;
    private LogThread logThread;

    @OnOpen
    public void onOpen(Session session) {
        List<String> logPathList = session.getRequestParameterMap().get("logPath");
        if (CollectionUtil.isEmpty(logPathList)) {
            onClose(session);
            throw new CommonException("日志路径参数缺失！");
        }
        String logPath = logPathList.get(0);
        if (!FileUtils.exist(logPath)) {
            onClose(session);
            throw new CommonException("日志文件不存在！");
        }
        process = RuntimeUtil.exec("tail -f " + logPath);
        inputStream = process.getInputStream();
        logThread = new LogThread(session);
        logThread.start();
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.debug("onClose: " + session.getId());
        logThread.interrupt();
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
                while (!Thread.currentThread().isInterrupted() && (line = bufferedReader.readLine()) != null) {
                    session.getBasicRemote().sendText(line);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }

    }

}
