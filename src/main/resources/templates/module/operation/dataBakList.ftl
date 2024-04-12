<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/operation/dataBakList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <@sa.hasPermission name="${PrivCode.BTN_CODE_DATA_BAK_STRATEGY_SAVE}">
                    <button class="layui-btn layui-btn-sm" lay-event="strategy"><i class="layui-icon layui-icon-edit"></i>备份策略</button>
                </@sa.hasPermission>
                <@sa.hasPermission name="${PrivCode.BTN_CODE_DATA_BAK_SAVE}">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>立即备份</button>
                </@sa.hasPermission>
                <@sa.hasPermission name="${PrivCode.BTN_CODE_DATA_BAK_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@sa.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <@sa.hasPermission name="${PrivCode.BTN_CODE_DATA_BAK_RECOVER}">
                <button class="layui-btn layui-btn-sm" lay-event="recover"><i class="layui-icon layui-icon-edit"></i>恢复备份</button>
            </@sa.hasPermission>
            <@sa.hasPermission name="${PrivCode.BTN_CODE_DATA_BAK_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@sa.hasPermission>
        </script>
        <table class="layui-hide" id="dataBak-list-tab" lay-filter="dataBak"></table>
    </div>
</div>
</body>
</html>
