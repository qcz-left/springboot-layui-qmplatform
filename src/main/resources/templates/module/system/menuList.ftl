<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/menuList.js"></script>
<body>
<div class="layui-fluid">
    <script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <@shiro.hasPermission name="${PrivCode.BTN_CODE_MENU_SAVE}">
            <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
        </@shiro.hasPermission>
    </div>
    </script>
    <script type="text/html" id="operator">
    <@shiro.hasPermission name="${PrivCode.BTN_CODE_MENU_SAVE}">
        <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="${PrivCode.BTN_CODE_MENU_DELETE}">
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
    </@shiro.hasPermission>
    </script>
    <table class="layui-hide" id="menu-list-tab" lay-filter="menu"></table>
</div>
</body>
</html>
