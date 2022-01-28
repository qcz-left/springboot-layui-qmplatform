<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="thirdpartyApp-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label required">应用名称</label>
            <div class="layui-input-block">
                <select name="name" lay-verify="required|name">
                    <option value=""></option>
                    <option value="dingtalk-code">钉钉扫码</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">应用ID</label>
            <div class="layui-input-block">
                <input type="text" name="appKey" lay-verify="required" placeholder="请输入应用ID" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">应用秘钥</label>
            <div class="layui-input-block">
                <input type="password" autocomplete="new-password" style="display:none">
                <input type="password" name="appSecret" lay-verify="required" placeholder="请输入应用秘钥" class="layui-input">
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
<script type="text/javascript">
    layui.use(['form']);
</script>
</body>
</html>
