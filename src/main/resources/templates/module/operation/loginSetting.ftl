<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .main-content, .layui-tab {
        height: 100%;
    }

    .layui-tab-content {
        background: rgb(242, 242, 242);
        height: calc(100% - 82px);
        text-align: center;
    }

    .edit-btn {
        background-color: #2f363c;
        position: absolute;
        top: 15px;
        right: 5px;
    }

    body {
        background: #fff !important;
        padding: 5px !important;
    }
</style>
<script type="text/javascript" src="${ctx}/static/module/operation/loginSetting.js"></script>
<body class="layui-fluid">
<div class="main-content">
    <button type="button" class="layui-btn hide edit-btn">
        编辑
    </button>
    <div class="layui-tab" lay-allowclose="true">
        <ul class="layui-tab-title">
            <li id="loginPageTitle" class="layui-this" lay-id="1">登录系统</li>
        </ul>
        <div class="layui-tab-content vh-center">
            <#include "/include/loginInclude.ftl">
        </div>
    </div>
</div>
</body>
</html>