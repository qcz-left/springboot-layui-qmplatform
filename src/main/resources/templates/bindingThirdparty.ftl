<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<link rel="stylesheet" href="${ctx}/static/css/login.css"/>
<script type="text/javascript">
    top.rsaPublicKey = "${rsaPublicKey!}";
</script>
<script type="text/javascript" src="${ctx}/static/login.js"></script>
<title>完善信息-Qu管理平台</title>
<style>
    #binding-tip {
        font-weight: 600;
        font-size: 20px;
        padding-bottom: 20px;
    }
    #binding-tip2 {
        font-size: 16px;
        padding-bottom: 20px;
    }

    #pic-tip {
        padding-bottom: 20px;
    }

    .iconfont {
        font-size: 40px;
    }
</style>
<body>
<div class="user-login-main vh-center layui-row">
    <div class="vh-center layui-col-md6">
        <div id="binding-tip">继续以完成第三方帐号绑定</div>
        <div id="pic-tip">
            <div class="layui-inline">
                <i class="iconfont icon-Q_round"></i>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div class="layui-inline">
                <i class="iconfont icon-exchange" style="font-size: 20px"></i>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div class="layui-inline">
                <i id="thirdpartyLogoIcon"></i>
            </div>
        </div>
        <div id="binding-tip2">你已通过 <span id="thirdpartyName"></span> 授权，完善以下登录信息即可完成绑定</div>
        <form class="layui-form pc-login" id="login-form" action="javascript:void(0);" method="post">
            <input type="hidden" id="thirdparty" name="thirdparty" value="${thirdparty!}">
            <input type="hidden" name="thirdparyId" value="${thirdpartyId!}">
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
                <img id="codeImg"/>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">绑定并登入</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
