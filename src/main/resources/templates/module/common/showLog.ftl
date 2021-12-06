<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-fluid {
        height: 100%;
        overflow: hidden;
    }

    .log-container {
        line-height: 1.5;
        font-size: 18px;
        padding: 10px;
        word-wrap: break-word;
        background: #343232;
        color: wheat;
        height: calc(100% - 20px);
        overflow-y: auto;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="log-container">
        <div class="hide detail-operator">
            <a href="${ctx}/loginPage" class="layui-btn layui-btn-normal">重新登录</a>
            <a href="javascript:;" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">关闭</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    let socketLogUrl = CommonUtil.getWsProtocol() + "://" + window.location.host + ctx + "/socket/log?logPath=${RequestParameters['logPath']}";
    let socketLog = new WebSocket(socketLogUrl);
    //获得消息事件
    socketLog.onmessage = function (msg) {
        if (msg.data === "$FINISH$") {
            // 完成标志
            socketLog.close();
            // 完成后是否需要重新登录
            <#if RequestParameters["needReLogin"]??>
                CommonUtil.postAjax(ctx + "/clearLoginInfo", {}, null, null, false);
            </#if>
            $(".detail-operator").show();
        } else {
            $(".log-container").append('<p>' + msg.data + '</p>');
        }
    };
</script>
</body>
</html>
