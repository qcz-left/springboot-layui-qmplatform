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
        height: calc(100% - 20px - 56px - 27px);
        overflow-y: auto;
        margin-top: 27px;
    }

    .log-tip {
        position: fixed;
        text-align: center;
        width: 100%;
        left: 0;
        font-size: 18px;
    }

    .log-main {
        width: 100%;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="log-tip">

    </div>
    <div class="log-container">
        <div class="log-main"></div>
    </div>
    <div class="hide detail-operator">
        <a id="reLogin" href="javascript:;" class="hide layui-btn layui-btn-normal" onclick="reLogin()">重新登录</a>
        <a href="javascript:;" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">关闭</a>
    </div>
</div>
<script type="text/javascript">
    let socketLogUrl = CommonUtil.getWsProtocol() + "://" + window.location.host + ctx + "/socket/log?logPath=${RequestParameters['logPath']}&cmdId=${RequestParameters['cmdId']}";
    let socketLog = new WebSocket(socketLogUrl);

    let $logMain = $(".log-main");
    let $logTip = $(".log-tip");
    $logTip.css("color", "red").text("正在执行，请勿关闭本页面...")
    //获得消息事件
    socketLog.onmessage = function (msg) {
        $logMain.append('<p>' + msg.data + '</p>').parent().scrollTop($logMain.height());
        if (msg.data === "$FINISH$") {
            // 完成标志
            socketLog.close();
            // 完成后是否需要重新登录
            <#if RequestParameters["needReLogin"]??>
                CommonUtil.postSync(ctx + "/clearLoginInfo", {});
                $("#reLogin").css("display", "inline-block");
            </#if>
            $logTip.css("color", "green").text("执行成功！");
            $(".detail-operator").show();
        }
    };

    function reLogin() {
        top.location.href = "${ctx}/loginPage";
    }
</script>
</body>
</html>
