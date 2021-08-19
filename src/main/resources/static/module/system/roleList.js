layui.use(['table'], function () {
    let table = layui.table;
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['roleId', 'roleName']);
                remove(groupAttrs[0], groupAttrs[1]);
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
                open(roleId);
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
        LayerUtil.openLayer({
            title: id ? "编辑角色" : "添加角色",
            content: ctx + "/role/roleDetailPage?id=" + id,
            area: ['30%', '80%'],
            loaded: function(iframeWin) {
                let form = iframeWin.layui.form;
                // 表单数据校验
                form.verify({
                    roleSign: function (value) {
                        let valid;
                        CommonUtil.getSync(ctx + '/role/validateRoleSign', {
                            roleSign: value,
                            roleId: id
                        }, function (result) {
                            valid = result.ok;
                        });
                        if (!valid) {
                            return '角色标识已存在，请更换一个！';
                        }
                    }
                });

                if (id) {
                    CommonUtil.getSync(ctx + '/role/getRoleOne/' + id, {}, function (result) {
                        form.val('role-form', result.data);
                    })
                }
            },
            submit: function (iframeWin) {
                let form = iframeWin.layui.form;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }
                top.layer.load(2);
                CommonUtil.postOrPut(id, ctx + '/role/' + (id ? 'updateRole' : 'addRole'), form.val('role-form'), function (result) {
                    top.layer.closeAll();
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                        tableReload();
                    })
                });
                return false;
            }
        }, true);
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
