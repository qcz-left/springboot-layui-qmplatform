<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-fluid {
        height: -webkit-fill-available;
    }

    .layui-tab {
        margin: unset;
        padding-top: 10px;
        height: -webkit-fill-available;
    }

    .layui-tab-content {
        height: calc(100% - 71px);
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-tab layui-tab-brief" lay-filter="org-detail">
        <ul class="layui-tab-title">
            <li lay-id="base-info" lay-href="${ctx}/organization/baseInfoPage" class="layui-this">基本信息</li>
            <#if isDept>
            <li lay-id="dept-manage" lay-href="${ctx}/organization/deptManagePage">部门管理</li>
            <li lay-id="user-manage" lay-href="${ctx}/organization/userManagePage">用户管理</li>
            </#if>
        </ul>
        <div class="layui-tab-content">
            <iframe id="org-content" frameborder="0" style="width: 100%;height: 100%;"></iframe>
        </div>
    </div>
</div>
</body>
<script>
    layui.use('element', function () {
        let element = layui.element;
        let tabLayFilter = 'org-detail';

        let isDept = ${isDept?c};
        let nodeId = '${RequestParameters["nodeId"]}';
        let context = '${RequestParameters["context"]}';

        element.on("tab({0})".format(tabLayFilter), function () {
            $("#org-content").attr("src", "{0}?isDept={1}&nodeId={2}&context={3}".format(this.getAttribute("lay-href"), isDept, nodeId, context));
        });
        // 默认显示基本信息
        element.tabChange(tabLayFilter, "base-info");
    });
</script>
</html>
