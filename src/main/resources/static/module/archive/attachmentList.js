layui.use(['table', 'form', 'element', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let tableId = 'attachment-list-tab';
    let layFilter = 'attachment';

    table.render({
        height: 'full-80',
        elem: '#' + tableId,
        url: ctx + '/archive/attachment/getAttachmentList',
        page: true,
        where: {
            orderName: 'uploadTime',
            order: 'desc'
        },
        toolbar: '#toolbar',
        defaultToolbar: [],
        cols: [[
            {type: 'checkbox'},
            {type: 'numbers'},
            {field: 'attachmentName', title: '文件名称', width: '10%', sort: true},
            {field: 'uploadUserName', title: '上传人', width: '10%', sort: true},
            {
                field: 'uploadTime', title: '上传时间', width: '15%', sort: true, templet: function (row) {
                    return new Date(row.uploadTime).format('yyyy-MM-dd hh:mm:ss.S');
                }
            },
            {field: 'description', title: '文件说明'},
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
                    layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                    return;
                }
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['attachmentId', 'attachmentName']);
                remove(groupAttrs[0], groupAttrs[1])
                break;
        }
    });

    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        switch (obj.event) {
            case 'download':
                location.href = ctx + '/archive/attachment/downloadFile?filePath=' + data.attachmentUrl;
                break;
            case 'delete':
                remove([data.attachmentId], [data.attachmentName]);
                break;
        }
    });

    // 搜索
    $("#btnSearch").click(function () {
        tableReload();
    });

    function open() {
        top.layer.open({
            type: 2,
            title: '上传文件',
            content: ctx + "/archive/attachment/uploadPage",
            area: ['30%', '50%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除文件：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/archive/attachment/delAttachment", {
                attachmentIds: CommonUtil.joinMulti(ids)
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
            where: form.val('attachment-search')
        });
    }
})
