<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-item label {
        width: 100px;
    }
    .layui-form-item .layui-input-block {
        margin-left: 130px;
    }
</style>
<body>
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="billType-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">账单类型名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" placeholder="请输入账单类型名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">所属上级</label>
            <div class="layui-input-block">
                <div id="parentId" name="parentId"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="remark" name="remark"></textarea>
            </div>
        </div>
    </form>
</div>
</body>
</html>
