<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <div class="log-container">

    </div>
</div>
<script type="text/javascript">
    let socketLogUrl = CommonUtil.getWsProtocol() + "://" + window.location.host + ctx + "/socket/log";
    let socketLog = new WebSocket(socketLogUrl);
    //获得消息事件
    socketLog.onmessage = function (msg) {
        $(".log-container").append('<p>' + msg.data + '</p>')
    };
</script>
</body>
</html>
