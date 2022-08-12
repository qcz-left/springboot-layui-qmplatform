<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="bill-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">消费时间</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input" id="consumeTime" name="consumeTime">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">账单类型</label>
            <div class="layui-input-block">
                <div id="typeId"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">金额</label>
            <div class="layui-input-block">
                <input type="text" name="amount" lay-verify="required" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">消费人</label>
            <div class="layui-input-block">
                <input type="text" name="consumer" lay-verify="required" class="layui-input">
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
