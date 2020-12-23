<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/userList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="user-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input type="text" name="username" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                        <select name="userSex">
                            <option value=""></option>
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
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
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_SAVE}">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@shiro.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_SAVE}">
                <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@shiro.hasPermission>
        </script>
        <table class="layui-hide" id="user-list-tab" lay-filter="user"></table>
    </div>
</div>
</body>
</html>
