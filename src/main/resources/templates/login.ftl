<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript">
    let code = "${code!}";
    let dingTalkConfigAppKey = "${dingTalkConfigAppKey!}";
    top.rsaPublicKey = "${rsaPublicKey!}";
</script>
<script type="text/javascript" src="${ctx}/static/login.js"></script>
<title></title>
<body class="vh-center">
<#include "/include/loginInclude.ftl">
</body>
</html>
