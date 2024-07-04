<!DOCTYPE html>
<html lang="en">
<#assign params = ["tinymce"]>
<#include "/include/include.ftl">
<style>
    form {
        height: calc(100% - 10px);
    }
</style>
<body class="detail-body">
<div>
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="notepad-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label required">标题</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="required" placeholder="请输入标题" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">是否公开</label>
            <div class="layui-input-block">
                <input type="radio" name="isPublic" value="1" title="公开" checked>
                <input type="radio" name="isPublic" value="0" title="私密">
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
</body>
<script type="text/javascript">
EditorUtil.init({
    selector: '#content',
    height: '450px'
});
</script>
</html>
