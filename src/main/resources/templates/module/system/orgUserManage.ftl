<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body>
<div class="layui-fluid">
    <div class="layui-form" lay-filter="user-search">
        <div class="layui-form-item">
            <#--搜索条件-->
            <span class="search-where">
                <div class="layui-inline">
                    <input type="checkbox" name="organizationExact" lay-skin="primary" title="精确到本部门" value="1">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="username" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <select name="userSex">
                            <option value=""></option>
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </div>
                </div>
            </span>
            <#--搜索栏-->
            <span class="searcher">
                <div class="layui-inline">
                    <button id="btnSearch" type="button" class="layui-btn layui-btn-normal"><i class="layui-icon layui-icon-search"></i>搜索</button>
                </div>
            </span>
        </div>
    </div>

    <div class="layui-col-xs12">
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_SAVE}">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_DELETE}">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </@shiro.hasPermission>
            </div>
        </script>
        <script type="text/html" id="operator">
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@shiro.hasPermission>
        </script>
        <table class="layui-hide" id="user-list-tab" lay-filter="user"></table>
    </div>
</div>
</body>
<script>
    layui.use(['table', 'form', 'element'], function () {
        let table = layui.table;
        let form = layui.form;
        let tableId = 'user-list-tab';
        let layFilter = 'user';
        let nodeId = '${RequestParameters["nodeId"]}';

        let tableIns = table.render({
            elem: '#' + tableId,
            url: ctx + '/user/getUserList',
            where: {
                orderName: 'username',
                organizationIdsStr: nodeId
            },
            height: 'full-80',
            page: true,
            toolbar: '#toolbar',
            defaultToolbar: ['filter', {
                title: '导出',
                layEvent: OperateType.EXPORT,
                icon: 'layui-icon-export'
            }, 'print'],
            cols: [[
                {type: 'checkbox'},
                {field: 'username', title: '用户名', width: '10%', sort: true},
                {field: 'loginname', title: '登录名', width: '10%', sort: true},
                {field: 'userSexName', title: '性别', width: '5%'},
                {field: 'phone', title: '电话', width: '10%'},
                {field: 'emailAddr', title: '邮箱', width: '10%', sort: true},
                {field: 'lockedName', title: '状态', width: '5%'},
                {field: 'organizationName', title: '所属部门', width: '30%'},
                {fixed: 'right', title: '操作', excel: false, align: 'center', templet: '#operator'}
            ]]
        });

        sortEventListen(table, layFilter, tableId);

        // 搜索
        $("#btnSearch").click(function () {
            tableReload();
        });

        // 头工具栏监听事件
        table.on('toolbar(' + layFilter + ')', function (obj) {
            switch (obj.event) {
                case 'add':
                    open();
                    break;
                case 'delete':
                    let checked = table.checkStatus(tableId).data;
                    if (checked.length === 0) {
                        top.layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                        return;
                    }
                    let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['id', 'username']);
                    remove(groupAttrs[0], groupAttrs[1]);
                    break;
                case OperateType.EXPORT:
                    let exportParam = {
                        queryParam: form.val('user-search'),
                        queryUrl: tableIns.config.url,
                        colNames: getColNames(tableIns),
                        generateName: "用户管理.xls"
                    };
                    CommonUtil.exportExcel(exportParam);
                    break;
            }
        });

        // 操作监听事件
        table.on('tool(' + layFilter + ')', function (obj) {
            let data = obj.data;
            switch (obj.event) {
                case 'edit':
                    open(data.id);
                    break;
                case 'delete':
                    remove([data.id], [data.username]);
                    break;
            }
        });

        function open() {
            top.layer.open({
                type: 2,
                title: "添加用户",
                content: ctx + "/user/userDetail",
                area: ['30%', '80%']
            });
        }

        function remove(ids, names) {
            top.layer.confirm("是否要删除用户：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
                title: "警告",
                skin: "my-layer-danger"
            }, function (index) {
                CommonUtil.deleteAjax(ctx + "/user/delUser", {
                    userIds: CommonUtil.joinMulti(ids)
                }, function (data) {
                    LayerUtil.respMsg(data, null, null, function () {
                        tableReload();
                    });
                    top.layer.close(index);
                })
            });
        }

        window.tableReload = function () {
            table.reload(tableId, {
                page: {
                    curr: 1
                },
                done: function () {
                    this.where = {};
                },
                where: form.val('user-search')
            });
        }

    });
</script>
</html>
