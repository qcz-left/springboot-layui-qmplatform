<!DOCTYPE html>
<html lang="en">
<#assign params = ["tinymce"]>
<#include "/include/include.ftl">
<style>
    .layui-form-item label {
        width: 120px;
    }

    .layui-form-item .layui-input-inline {
        width: 200px;
    }
</style>
<body class="detail-body">
<form class="layui-form detail-form" action="javascript:void(0);" lay-filter="login-setting-form">
    <div class="layui-form-item">
        <label class="layui-form-label required">登录页标签</label>
        <div class="layui-input-inline">
            <input type="text" name="loginPageTitle" lay-verify="required" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">系统中文标题</label>
        <div class="layui-input-inline">
            <input type="text" name="systemChineseTitle" lay-verify="required" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">建议不超过12个字符</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">系统英文标题</label>
        <div class="layui-input-inline">
            <input type="text" name="systemEnglishTitle" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">账号登录标题</label>
        <div class="layui-input-inline">
            <input type="text" name="accountLoginTitle" lay-verify="required" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">建议不超过6个字符</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">忘记密码</label>
        <div class="layui-input-inline">
            <input type="checkbox" name="enableForgetPassword" value="1" lay-skin="switch" lay-filter="forgetPassword-switch" title="开启|关闭">
        </div>
        <div class="layui-form-mid layui-word-aux">用于找回密码</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">其它方式登录</label>
        <div class="layui-input-inline">
            <input type="checkbox" name="otherLoginWay[dingtalk]" value="dingtalk-code" lay-skin="tag" title="钉钉">
            <input type="checkbox" name="otherLoginWay[wechat]" value="wechat-code" lay-skin="tag" title="微信">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">页尾信息</label>
        <div class="layui-input-block">
            <textarea id="bottomInfo"></textarea>
        </div>
    </div>
</form>
<script type="text/javascript">
EditorUtil.init({
    selector: '#bottomInfo',
    height: '200px'
})
</script>
</body>
</html>