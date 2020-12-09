<!DOCTYPE html>
<html>
<#assign params = ["layui"]>
<#include "/include/include.ftl">
<link rel="stylesheet" href="${ctx}/static/css/login.css"/>
<script type="text/javascript" src="${ctx}/static/login.js"></script>
<title>Qu管理平台-登录</title>
<body>
    <div class="user-login-main">
        <form class="layui-form" id="login-form" action="javascript:void(0);" method="post">
            <div class="layui-form-item">
                <label class="layui-icon layui-icon-username" for="user-login-username"></label>
                <input type="text" name="loginname" id="user-login-username" lay-verify="required" placeholder="用户名"
                       class="layui-input" value="admin">
            </div>
            <div class="layui-form-item">
                <label class="layui-icon layui-icon-password" for="user-login-password"></label>
                <input type="password" name="password" id="user-login-password" lay-verify="required" placeholder="密码"
                       class="layui-input" value="admin">
            </div>
            <div class="text-danger" v-text="error.loginFail"></div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
            </div>
        </form>
        <div class="layui-trans user-login-footer">
            <p>
                © 2020 <a href="http://www.zhongcenter.cn" target="_blank">www.zhongcenter.cn</a>
            </p>
            <p>
                <span><a href="https://github.com/qcz-left/slv-init-manage" target="_blank">GitHub</a></span>
                <span><a href="http://www.zhongcenter.cn" target="_blank">个人博客</a></span>
            </p>
        </div>
    </div>
</body>
</html>
