layui.use(['table', 'form', 'element', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let tableId = 'role-list-tab';
    let layFilter = 'role';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/role/getRoleList',
        height: 'full-30',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {type: 'checkbox'},
            {field: 'roleName', title: '角色名称', width: '10%', sort: true},
            {field: 'roleSign', title: '角色标识', width: '10%', sort: true},
            {field: 'remark', title: '描述', width: '20%'},
            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
        ]]
    });

    sortEventListen(table, layFilter, tableId);

    // 头工具栏监听事件
    table.on('toolbar(' + layFilter + ')', function (obj) {
        let data = obj.data;
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['roleId', 'roleName']);
                remove(groupAttrs[0], groupAttrs[1])
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let roleId = data.roleId;
        let roleName = data.roleName;
        switch (obj.event) {
            case 'allot':
                allotPermission(roleId, roleName);
                break;
            case 'edit':
                open(roleId)
                break;
            case 'delete':
                remove([roleId], [roleName]);
                break;
        }
    });

    function allotPermission(roleId, roleName) {
        top.layer.open({
            type: 2,
            title: "分配权限 - " + roleName,
            content: ctx + "/role/permissionTreePage?roleId=" + roleId,
            area: ['30%', '80%']
        });
    }

    function open(id) {
        id = id || '';
        top.layer.open({
            type: 2,
            title: id ? "编辑角色" : "添加角色",
            content: ctx + "/role/roleDetailPage?id=" + id,
            area: ['30%', '80%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除角色：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/role/deleteRole", {
                roleIds: CommonUtil.joinMulti(ids)
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
            }
        });
    }

});
