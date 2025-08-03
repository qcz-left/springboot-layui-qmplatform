<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <div class="layui-col-xs12">
        <script type="text/html" id="operator">
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
        </script>
        <table class="layui-hide" id="manageBindAccount-list-tab" lay-filter="manageBindAccount"></table>
    </div>
</div>
</body>
</html>