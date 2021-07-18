<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <div class="layui-col-xs12">
        <blockquote class="layui-elem-quote">
            总共 <span id="total"></span> 条数据，导入成功 <span id="successCount" class="text-success"></span> 条，以下 <span id="failCount" class="text-danger"></span> 导入失败
        </blockquote>
        <table class="layui-hide" id="importResult-list-tab"></table>
    </div>
</div>
</body>
</html>
