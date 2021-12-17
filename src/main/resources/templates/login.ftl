<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<link rel="stylesheet" href="${ctx}/static/css/login.css"/>
<script type="text/javascript">
    let code = "${RequestParameters["code"]!}";
    top.rsaPublicKey = "${rsaPublicKey!}";
</script>
<script type="text/javascript" src="${ctx}/static/login.js"></script>
<title>Qu管理平台-登录</title>
<body>
<div class="user-login-main vertical-horizontal-center layui-row">
    <div class="login-left layui-col-md6">
        <div class="vertical-horizontal-center">
            <p class="system-cn">QU管理平台</p>
            <br>
            <p class="system-en">QUGUANLIPINGTAI</p>
        </div>
    </div>
    <div class="layui-col-md6">
        <div class="login-header">
            <div class="login-title">
                <h1 class="pc-login">账户登录</h1>
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
                <input type="password" name="password" id="user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
            </div>
            <div id="codeDiv" class="layui-form-item hide">
                <label class="layui-icon layui-icon-vercode" for="user-login-code"></label>
                <input type="text" name="validateCode" id="user-login-code" placeholder="验证码" lay-filter="validateCode" class="layui-input layui-input-inline">
                <img id="codeImg"/>
            </div>
            <div class="layui-form-item">
                <a href="${ctx}/user/noNeedLogin/retrievePasswordPage" class="layui-btn" style="color: #1760D7; background: unset; font-weight: bold; float: right;">忘记密码？</a>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
            </div>
        </form>
        <fieldset class="layui-elem-field layui-field-title">
            <legend style="font-size: unset;">其他方式登录</legend>
        </fieldset>
        <div id="otherLoginWayContainer">
            <i id="dingdingLogin" class="iconfont icon-dingding-o" title="使用钉钉账号登录"></i>
        </div>
    </div>
</div>
<div class="layui-trans user-login-footer">
    <p>
        © 2021 <span><a href="https://github.com/qcz-left/springboot-layui-qmplatform" target="_blank">GitHub</a></span>
    </p>
</div>
</body>
</html>
