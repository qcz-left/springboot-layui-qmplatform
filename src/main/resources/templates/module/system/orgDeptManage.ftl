<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_ORG_SAVE}">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
                </@shiro.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_ORG_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@shiro.hasPermission>
        </script>
        <table class="layui-hide" id="org-list-tab" lay-filter="org"></table>
    </div>
</div>
</body>
<script>
    layui.use(['treetable', 'table'], function () {
        let treetable = layui.treetable;
        let table = layui.table;
        let tableId = 'org-list-tab';
        let layFilter = 'org';
        let nodeId = '${RequestParameters["nodeId"]}';
        let context = '${RequestParameters["context"]}';

        treetable.render({
            elem: '#' + tableId,
            url: ctx + '/organization/getOrgList',
            where: {
                parentId: nodeId
            },
            height: 'full-30',
            treeColIndex: 0,	//树形图标显示在第几列
            treeSpid: nodeId,		//最上级的父级id
            treeIdName: 'id',	//id字段的名称
            treePidName: 'parentId',	//父级节点字段
            treeDefaultClose: false,	//是否默认折叠
            treeLinkage: false,		//父级展开时是否自动展开所有子级
            page: false,
            toolbar: '#toolbar',
            cols: [[
                {field: 'name', title: '部门名称', width: '20%'},
                {field: 'code', title: '部门编码', width: '10%'},
                {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
            ]]
        });

        // 头工具栏监听事件
        table.on('toolbar(' + layFilter + ')', function (obj) {
            switch (obj.event) {
                case 'add':
                    open();
                    break;
            }
        });

        // 操作监听事件
        table.on('tool(' + layFilter + ')', function (obj) {
            let data = obj.data;
            switch (obj.event) {
                case 'delete':
                    remove([data.id], [data.name]);
                    break;
            }
        });

        function open() {
            top.layer.open({
                type: 2,
                title: "添加部门",
                content: ctx + "/organization/organizationDetailPage?parentId=" + nodeId + "&parentName=" + context,
                area: ['30%', '80%']
            });
        }

        function remove(ids, names) {
            top.layer.confirm("是否要删除部门：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后该部门及其子部门将不可恢复！", {
                title: "警告",
                skin: "my-layer-danger"
            }, function () {
                CommonUtil.deleteAjax(ctx + "/organization/deleteOrg", {
                    orgIds: CommonUtil.joinMulti(ids)
                }, function (data) {
                    LayerUtil.respMsg(data, null, null, function () {
                        reloadFrame();
                    });
                })
            });
        }
    });
</script>
</html>
