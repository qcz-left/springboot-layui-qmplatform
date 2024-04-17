<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/thirdpartyAppList.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
            </div>
        </script>
        <script type="text/html" id="operator">
            <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
        </script>
        <table class="layui-hide" id="thirdpartyApp-list-tab" lay-filter="thirdpartyApp"></table>
    </div>
</div>
</body>
</html>
