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
tinymce.init({
    selector: '#content',
    language: 'zh_CN',
    height: '450px',
    plugins: 'preview searchreplace autolink directionality visualchars fullscreen image link media code table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount emoticons',
    toolbar: 'code forecolor backcolor bold italic underline strikethrough link anchor | \
                alignleft aligncenter alignright indent2em lineheight | \
                bullist numlist | blockquote subscript superscript removeformat | \
                table image media bdmap emoticons charmap hr pagebreak insertdatetime \
                | fullscreen',
    fontsize_formats: '11px 12px 14px 16px 18px 24px 36px 48px',
    menubar: false,
    autosave_ask_before_unload: false
});
</script>
</html>
