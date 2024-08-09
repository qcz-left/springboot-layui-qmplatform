layui.use(['table', 'form', 'element', 'layer', 'xmSelect'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let xmSelect = layui.xmSelect;
    let tableId = 'user-list-tab';
    let layFilter = 'user';

    // 组织机构数据加载
    let organizationIdsSelect = SelectUtil.render(xmSelect, {
        el: '#organizationIdsStr',
        name: 'organizationIdsStr',
        tree: true,
        data: []
    });
    CommonUtil.getAjax(ctx + '/organization/getOrgTree', {}, function (result) {
        organizationIdsSelect.update({
            data: result.data
        })
    });

    let tableIns = table.render({
        elem: '#' + tableId,
        url: ctx + '/user/getUserList',
        height: 'full-88',
        page: true,
        toolbar: '#toolbar',
        defaultToolbar: ['filter', {
            title: '导出',
            layEvent: OperateType.EXPORT,
            icon: 'layui-icon-export'
        }, 'print'],
        orderName: 'username',
        order: 'desc',
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
                    layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
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

    function open(id) {
        id = id || '';
        top.layer.open({
            type: 2,
            title: id ? "编辑用户" : "添加用户",
            content: ctx + "/user/userDetail?id=" + id,
            area: ['30%', '80%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除用户：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(ctx + "/user/delUser", ids, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                top.layer.close(index);
            })
        });
    }

    window.tableReload = function () {
        layuiTableReload(table, tableId, form.val('user-search'));
    }

});
