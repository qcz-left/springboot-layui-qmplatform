<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    html, body {
        margin: 0;
        padding: 0;
        border: 0;
        width: 100%;
        height: 100%;
    }
    body {
        background: unset;
    }
</style>
<script type="text/javascript">
    let code = ${code!0};
    let dingTalkConfigAppKey = "${dingTalkConfigAppKey!}";
    top.rsaPublicKey = "${rsaPublicKey!}";
</script>
<script type="text/javascript" src="${ctx}/static/login.js"></script>
<title></title>
<body>
<#include "/include/loginInclude.ftl">
</body>
</html>
