<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .main-content {
        height: calc(100% - 10px);
    }
    .layui-tab {
        height: calc(100% - 20px);
    }

    .layui-tab-content {
        height: calc(100% - 71px);
        text-align: center;
    }

    .edit-btn {
        background-color: #2f363c;
        position: absolute;
        top: 15px;
        right: 5px;
    }

</style>
<script type="text/javascript" src="${ctx}/static/module/operation/loginSetting.js"></script>
<body style="background: #fff; padding: 5px; width: calc(100% - 10px); height: calc(100% - 10px);">
<div class="main-content">
    <button type="button" class="layui-btn hide edit-btn">
        编辑
    </button>
    <div class="layui-tab" lay-allowclose="true">
        <ul class="layui-tab-title">
            <li id="loginPageTitle" class="layui-this" lay-id="1">登录系统</li>
        </ul>
        <div class="layui-tab-content">
            <#include "/include/loginInclude.ftl">
        </div>
    </div>
</div>
</body>
</html>