<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-fluid {
        height: -webkit-fill-available;
    }

    .layui-tab {
        margin: unset;
        padding: 10px 0;
        height: -webkit-fill-available;
    }

    .layui-tab-content {
        height: calc(100% - 51px);
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

        element.on("tab({0})".format(tabLayFilter), function () {
            $("#org-content").attr("src", this.getAttribute("lay-href") + "?isDept=" + isDept + "&nodeId=" + nodeId);
        });
        // 默认显示基本信息
        element.tabChange(tabLayFilter, "base-info");
    });
</script>
</html>
