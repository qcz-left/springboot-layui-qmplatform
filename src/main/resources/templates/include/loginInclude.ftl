<link rel="stylesheet" href="${ctx}/static/css/login.css" />
<script type="text/javascript">
CommonUtil.getAjax(ctx + '/operation/loginSetting/get', {}, function (result) {
    let data = result.data;
    $(".system-cn").text(data.systemChineseTitle);
    $(".system-en").text(data.systemEnglishTitle);
    $("#accountLoginTitle").text(data.accountLoginTitle);
    $("#loginPageTitle,title").text(data.loginPageTitle);
    $("#enableForgetPasswordDiv").toggle(data.enableForgetPassword === 1);
    if (data.otherLoginWay) {
        if (data.otherLoginWay.indexOf('dingtalk-code') !== -1) {
            $("#dingdingLogin").removeClass("hide");
        }
        if (data.otherLoginWay.indexOf('wechat-code') !== -1) {
            $("#wechatLogin").removeClass("hide");
        }
    }
    $(".user-login-footer").html(data.bottomInfo);
});
</script>
<div class="user-login-main layui-row">
    <div class="login-left vh-center layui-col-md6">
        <div>
            <p class="system-cn"></p>
            <br>
            <p class="system-en"></p>
        </div>
    </div>
    <div class="layui-col-md6">
        <div class="login-header">
            <div class="login-title">
                <h1 id="accountLoginTitle" class="pc-login"></h1>
                <h1 class="hide code-login">扫码登录</h1>
            </div>
            <div class="hide login-pattern">
                <div id="logo-code" class="login-pattern-logo pc-login"></div>
                <div id="logo-pc" class="hide login-pattern-logo code-login"></div>
                <i id="pc-login-tip" class="hide code-login"></i>
            </div>
        </div>
        <#-- 表单登录 -->
        <form class="layui-form pc-login" id="login-form" action="javascript:void(0);" method="post">
            <div class="layui-form-item">
                <label class="layui-icon layui-icon-username" for="user-login-username"></label>
                <input type="text" name="loginname" id="user-login-username" lay-verify="required" placeholder="用户名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layui-icon layui-icon-password" for="user-login-password"></label>
                <input type="password" autocomplete="new-password" style="display:none">
                <input type="password" name="password" id="user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
            </div>
            <div id="codeDiv" class="layui-form-item hide">
                <label class="layui-icon layui-icon-vercode" for="user-login-code"></label>
                <input type="text" name="validateCode" id="user-login-code" placeholder="验证码" lay-filter="validateCode" class="layui-input layui-input-inline">
                <img id="codeImg" />
            </div>
            <div id="enableForgetPasswordDiv" class="layui-form-item hide">
                <a href="${ctx}/user/nnl/retrievePasswordPage" class="layui-btn" style="color: #1760D7; background: unset; font-weight: bold; float: right;">忘记密码？</a>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
            </div>
        </form>
        <fieldset class="layui-elem-field layui-field-title">
            <legend style="font-size: unset;">其他方式登录</legend>
        </fieldset>
        <div id="otherLoginWayContainer">
            <i id="dingdingLogin" class="iconfont icon-dingding-o hide" title="使用钉钉登录"></i>
            <i id="wechatLogin" class="iconfont icon-weixin hide" title="使用微信登录"></i>
        </div>
    </div>
</div>
<div class="layui-trans user-login-footer">

</div>