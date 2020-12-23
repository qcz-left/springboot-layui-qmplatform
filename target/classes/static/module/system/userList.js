layui.use(['table', 'form', 'element', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let tableId = 'user-list-tab';
    let layFilter = 'user';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/user/getUserList',
        height: 'full-80',
        page: true,
        toolbar: '#toolbar',
        where: {
            orderName: 'username'
        },
        cols: [[
            {type: 'checkbox'},
            {field: 'username', title: '用户名', width: '10%', sort: true},
            {field: 'loginname', title: '登录名', width: '10%', sort: true},
            {field: 'userSexName', title: '性别', width: '10%'},
            {field: 'phone', title: '电话', width: '20%'},
            {field: 'emailAddr', title: '邮箱', width: '20%', sort: true},
            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
        ]]
    });

    sortEventListen(table, layFilter, tableId);

    // 搜索
    $("#btnSearch").click(function () {
        tableReload();
    });

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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['id', 'username']);
                remove(groupAttrs[0], groupAttrs[1])
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        switch (obj.event) {
            case 'edit':
                open(data.id)
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
            where: form.val('user-search')
        });
    }

});
