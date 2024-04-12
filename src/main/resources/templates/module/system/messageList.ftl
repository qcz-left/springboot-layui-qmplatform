<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/messageList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="message-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">内容</label>
                    <div class="layui-input-inline">
                        <input type="text" name="content" class="layui-input">
                    </div>
                </div>

            </span>
            <#--搜索栏-->
            <span class="searcher">
                <div class="layui-inline">
                    <button id="btnSearch" type="button" class="layui-btn layui-btn-normal"><i class="layui-icon layui-icon-search"></i>搜索</button>
                </div>
            </span>
        </div>
    </div>
    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <@sa.hasPermission name="${PrivCode.BTN_CODE_MESSAGE_SET_READ}">
                    <button class="layui-btn layui-btn-sm" lay-event="read"><i class="layui-icon layui-icon-read"></i>设置已读</button>
                </@sa.hasPermission>
                <@sa.hasPermission name="${PrivCode.BTN_CODE_MESSAGE_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@sa.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <div class="layui-btn-container">
                <@sa.hasPermission name="${PrivCode.BTN_CODE_MESSAGE_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@sa.hasPermission>
            </div>
        </script>
        <table class="layui-hide" id="message-list-tab" lay-filter="message"></table>
    </div>
</div>
</body>
</html>