<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    form {
        height: calc(100% - 10px);;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="notepad-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label required">标题</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="required" placeholder="请输入标题" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <textarea id="content" lay-verify="content"></textarea>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'layedit']);
</script>
</body>
</html>
