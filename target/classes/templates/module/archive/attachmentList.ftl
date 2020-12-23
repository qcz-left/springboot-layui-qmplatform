<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/archive/attachmentList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="attachment-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">文件名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="attachmentName" autocomplete="off" class="layui-input">
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
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_FILE_UPLOAD}">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>上传</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_FILE_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@shiro.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <button class="layui-btn layui-btn-sm" lay-event="download"><i class="layui-icon layui-icon-download-circle"></i>下载</button>
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_FILE_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@shiro.hasPermission>
        </script>
        <table class="layui-hide" id="attachment-list-tab" lay-filter="attachment"></table>
    </div>
</div>
</body>
</html>
