<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/roleList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_ROLE_SAVE}">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_ROLE_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@shiro.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_ROLE_ALLOT}">
                <button class="layui-btn layui-btn-sm layui-btn-warm"  lay-event="allot"><i class="layui-icon layui-icon-auz"></i>分配权限</button>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_ROLE_SAVE}">
                <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_ROLE_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@shiro.hasPermission>
        </script>
        <table class="layui-hide" id="role-list-tab" lay-filter="role"></table>
    </div>
</div>
</body>
</html>
