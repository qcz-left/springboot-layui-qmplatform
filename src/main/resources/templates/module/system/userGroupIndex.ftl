<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript" src="${ctx}/static/module/system/userGroupIndex.js"></script>
<style >
    body > div {
        height: 100%;
    }

    #userGroupTree {
        overflow: auto;
        height: calc(100% - 53px);
    }

    .org-search-form {
        padding-left: 16px;
    }
</style>
<body>
<div class="layui-col-xs2" style="border-right: 1px solid #eee; min-width: 260px;">
    <form class="layui-form org-search-form" action="javascript:void(0);">
        <div class="layui-form-item">
            <div class="layui-input-group">
                <input type="text" name="inputName" autocomplete="off" class="layui-input" placeholder="用户组关键字查询">
                <div id="btnSearch" class="layui-input-split layui-input-suffix" style="cursor: pointer;">
                    <i class="layui-icon layui-icon-search"></i>
                </div>
                <button id="addRootNode" class="layui-btn layui-btn-primary" title="添加节点"><i class="layui-icon layui-icon-addition"></i></button>
            </div>
        </div>
    </form>
    <div id="userGroupTree"></div>
</div>
<div class="layui-col-xs10 layui-fluid" style="height: 100%; max-width: calc(100% - 260px);">
    <iframe id="userGroupContent" frameborder="0" style="width: 100%;height: 100%;"></iframe>
</div>
</body>
</html>
