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
                    <div class="layui-input-inline" style="width: auto;">
                        <input type="checkbox" name="organizationExact" lay-skin="primary" title="精确到本部门" value="1" checked>
                    </div>
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
                <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="move"><i class="layui-icon layui-icon-transfer"></i>移动</button>
                <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_DELETE}">
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            </@shiro.hasPermission>
        </div>
        </script>
        <script type="text/html" id="operator">
        <@shiro.hasPermission name="${PrivCode.BTN_CODE_USER_DELETE}">
            {{# if (!d.systemAdmin) { }}
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
            {{# } }}
        </@shiro.hasPermission>
        </script>
        <table class="layui-hide" id="user-list-tab" lay-filter="user"></table>
    </div>
</div>
</body>
<script>
layui.use(['table', 'form', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let tableId = 'user-list-tab';
    let layFilter = 'user';
    let nodeId = '${RequestParameters["nodeId"]}';
    let context = '${RequestParameters["context"]}';

    let tableIns = table.render({
        elem: '#' + tableId,
        url: ctx + '/user/getUserList?&organizationIdsStr=' + nodeId,
        where: {
            organizationExact: form.val('user-search').organizationExact
        },
        orderName: 'username',
        order: 'desc',
        height: 'full-101',
        page: true,
        toolbar: '#toolbar',
        defaultToolbar: ['filter', {
            title: '导入',
            layEvent: OperateType.IMPORT,
            icon: 'layui-icon-upload'
        }, {
            title: '导出',
            layEvent: OperateType.EXPORT,
            icon: 'layui-icon-export'
        }, 'print'],
        cols: [[
            {type: 'checkbox', excel: false},
            {field: 'username', title: '用户名', width: '10%', sort: true},
            {field: 'loginname', title: '登录名', width: '10%', sort: true},
            {field: 'userSexName', title: '性别', width: '5%', exportSelect: true, exportSelectArray: ['男', '女']},
            {field: 'phone', title: '电话', width: '10%'},
            {field: 'emailAddr', title: '邮箱', width: '10%', sort: true},
            {field: 'lockedName', title: '状态', width: '5%', exportSelect: true, exportSelectArray: ['正常', '锁定']},
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
        let event = obj.event;

        if (event === 'move' || event === 'delete') {
            let checked = table.checkStatus(tableId).data;
            if (checked.length === 0) {
                top.layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                return;
            }

            let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['id', 'username']);
            let ids = groupAttrs[0];
            let usernames = groupAttrs[1];
            switch (event) {
                case 'move':
                    move(ids);
                    break;
                case 'delete':
                    remove(ids, usernames);
                    break;
            }
        }

        switch (event) {
            case 'add':
                open();
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
            case OperateType.IMPORT:
                layer.open({
                    type: 2,
                    title: "导入",
                    content: ctx + "/importExcelPage?act=" + encodeURIComponent("/user/importExcel"),
                    area: ['45%', '55%']
                });
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

    /**
     * 移动至另一个部门
     */
    function move(userIds) {
        CommonUtil.chooseDept({
            success: function (param) {
                let deptId = param.nodeId;

                CommonUtil.postAjax(ctx + "/user-organization/moveUserToAnotherDept", {
                    userIds: userIds,
                    deptId: deptId
                }, function (result) {
                    LayerUtil.respMsg(result, Msg.OPERATE_SUCCESS, Msg.OPERATE_FAILURE, function () {
                        reloadFrame();
                    });
                });
            }
        });
    }

    function open() {
        top.layer.open({
            type: 2,
            title: "添加用户",
            content: ctx + "/user/userDetail?parentId=" + nodeId + "&parentName=" + context,
            area: ['30%', '85%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除用户：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/user/delUser", {
                userIds: CommonUtil.joinMulti(ids)
            }, function (result) {
                top.layer.close(index);
                if (CommonUtil.respSuccess(result)) {
                    reloadFrame();
                }
            })
        });
    }

    window.tableReload = function () {
        layuiTableReload(table, tableId, form.val('user-search'));
    }

    window.downloadTemplate = function () {
        let templateParam = {
            colNames: getColNames(tableIns),
            generateName: "用户列表模板.xls"
        };
        CommonUtil.downloadTemplate(templateParam);
    }

});
</script>
</html>
