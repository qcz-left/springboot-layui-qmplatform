<!DOCTYPE html>
<html lang="en">
${a!'<#include "/include/include.ftl">'}
<script type="text/javascript" src="${a!'$\{ctx}'}/static/module/${config.injectionConfig.customMap.modulePrefix}${package.ModuleName}/${entity?uncap_first}.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="${entity?uncap_first}-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <label class="layui-form-label">名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" autocomplete="off" class="layui-input">
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
                <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </div>
        </script>
        <script type="text/html" id="operator">
            <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
        </script>
        <table class="layui-hide" id="${entity?uncap_first}-list-tab" lay-filter="${entity?uncap_first}"></table>
    </div>
</div>
</body>
</html>
