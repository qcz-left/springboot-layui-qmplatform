<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/orgManage.js"></script>
<style>
    body > div {
        height: 100%;
        background: radial-gradient(#98b3b1, transparent);
    }
</style>
<body>
<div class="layui-col-xs2">
    <div id="orgTree"></div>
</div>
<div class="layui-col-xs10">
    <iframe id="orgContent" name="orgContent" frameborder="0" style="width: 100%;height: 100%;"></iframe>
</div>
</body>
</html>
