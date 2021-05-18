<!DOCTYPE html>
<html>
<#assign params = ["layui"]>
<#include "/include/include.ftl">
<link rel="stylesheet" href="${ctx}/static/css/login.css"/>
<script type="text/javascript">
    let code = "${RequestParameters["code"]!}";
</script>
<script type="text/javascript" src="${ctx}/static/login.js"></script>
<title>Qu管理平台-登录</title>
<body>
<div class="user-login-main">
    <form class="layui-form" id="login-form" action="javascript:void(0);" method="post">
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-username" for="user-login-username"></label>
            <input type="text" name="loginname" id="user-login-username" lay-verify="required" placeholder="用户名" class="layui-input" value="admin">
        </div>
        <div class="layui-form-item">
            <label class="layui-icon layui-icon-password" for="user-login-password"></label>
            <input type="password" name="password" id="user-login-password" lay-verify="required" placeholder="密码" class="layui-input" value="admin">
        </div>
        <div id="codeDiv" class="layui-form-item hide">
            <label class="layui-icon layui-icon-vercode" for="user-login-code"></label>
            <input type="text" name="validateCode" id="user-login-code" placeholder="验证码" lay-filter="validateCode" class="layui-input layui-input-inline">
            <img id="codeImg"/>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
        </div>
        <div class="layui-form-item">
            <a href="${ctx}/user/noNeedLogin/retrievePasswordPage" class="layui-btn" style="color: red; background: unset; font-weight: bold;">忘记密码？快速找回</a>
        </div>
    </form>
    <div class="layui-trans user-login-footer">
        <p>
            © 2020 <span><a href="https://github.com/qcz-left/qmplatform" target="_blank">GitHub</a></span>
        </p>
    </div>
</div>
</body>
</html>
