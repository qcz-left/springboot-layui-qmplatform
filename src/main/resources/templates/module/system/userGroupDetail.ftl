<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-item label {
        width: 100px;
    }

    .layui-form-item .layui-input-block {
        margin-left: 130px;
        width: 35%;
    }

</style>
<script>
let userGroupId = '${RequestParameters["nodeId"]!}';
let userGroupName = '${RequestParameters["context"]!}';
let parentId = '${RequestParameters["parentId"]!}';
</script>
<body>
<div class="layui-fluid">
    <#if isInsert>
        <blockquote class="layui-elem-quote">
            新增
        </blockquote>
        <#include "./userGroupBaseInfo.ftl" />
    <#else>
        <div class="layui-tab layui-tab-brief" lay-filter="userGroup-detail">
            <ul class="layui-tab-title">
                <li lay-id="base-info" lay-href="${ctx}/system/user-group/baseInfoPage" class="layui-this">基本信息</li>
                <li lay-id="user-manage" lay-href="${ctx}/system/user-group/userManagePage">用户管理</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <#include "./userGroupBaseInfo.ftl" />
                </div>
                <div class="layui-tab-item">
                    <#include "./userGroupUserManager.ftl" />
                </div>
            </div>
        </div>
    </#if>
</div>
</body>
<script>
layui.use(['form', 'element'], function () {
    let form = layui.form;

    if (userGroupId) {
        CommonUtil.getAjax(ctx + '/system/user-group/get/' + userGroupId, {}, function (result) {
            let data = result.data;
            form.val("userGroup-form", data);
        });
    }
    if (parentId) {
        $("#parentDiv").removeClass("hide");
    }

    form.on('submit(userGroup-submit)', function (data) {
        layer.load(2);
        CommonUtil.postAjax(ctx + '/system/user-group' + (userGroupId ? '/update' : '/insert'), data.field, function (result) {
            top.layer.closeAll();
            LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                reloadFrame();
            })
        });
        return false;
    });
});
</script>
</html>
