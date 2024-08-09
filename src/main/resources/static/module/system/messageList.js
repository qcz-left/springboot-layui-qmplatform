layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    let tableId = 'message-list-tab';
    let layFilter = 'message';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/system/message/getMessageList',
        height: 'full-88',
        page: true,
        toolbar: '#toolbar',
        defaultToolbar: [],
        orderName: 'readed,createTime',
        order: 'asc,desc',
        cols: [[
            {type: 'checkbox'},
            {field: 'typeName', title: '类型', width: '10%', sort: false},
            {field: 'content', title: '内容', width: '15%', sort: false},
            {field: 'createTime', title: '创建时间', width: '15%'},
            {field: 'lastUpdateTime', title: '上次更新时间', width: '15%'},
            {field: 'senderName', title: '发送人', width: '10%', sort: false},
            {
                field: 'readed', title: '状态', width: '10%', templet: function (row) {
                    return row.readed === 1 ? '<span class="layui-badge layui-bg-green">已读</span>' : '<span style="cursor:pointer;" class="layui-badge layui-bg-orange" lay-event="fail">未读</span>';
                }
            },
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
        let checked = table.checkStatus(tableId).data;
        if (checked.length === 0) {
            top.layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
            return;
        }
        let ids = CommonUtil.groupAttrFromArray(checked, ['messageId'])[0];
        switch (obj.event) {
            case 'read':
                top.layer.confirm("是否将所选未读消息设置为已读？", {
                    title: "警告",
                    skin: "my-layer-danger"
                }, function (index) {
                    CommonUtil.postAjax(ctx + "/system/message/setHasRead", ids, function (data) {
                        LayerUtil.respMsg(data, null, null, function () {
                            tableReload();
                        });
                        top.layer.close(index);
                    })
                });
                break;
            case 'delete':
                remove(ids);
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        switch (obj.event) {
            case 'delete':
                remove([data.messageId]);
                break;
        }
    });

    function remove(ids) {
        top.layer.confirm("是否要删除消息？删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(ctx + "/system/message/delete", ids, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                top.layer.close(index);
            })
        });
    }

    window.tableReload = function () {
        layuiTableReload(table, tableId, form.val('message-search'));
    }
});