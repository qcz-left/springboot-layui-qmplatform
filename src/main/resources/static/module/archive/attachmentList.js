layui.use(['table', 'form', 'element'], function () {
    let table = layui.table;
    let form = layui.form;
    let tableId = 'attachment-list-tab';
    let layFilter = 'attachment';

    table.render({
        height: 'full-88',
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
            {field: 'attachmentName', title: '文件名称', width: '20%', sort: true},
            {field: 'uploadUserName', title: '上传人', width: '10%', sort: true},
            {field: 'uploadTime', title: '上传时间', width: '15%', sort: true},
            {
                field: 'size', title: '文件大小', width: '10%', templet: function (row) {
                    return CommonUtil.convertByte(row.size);
                }
            },
            {field: 'description', title: '文件说明'},
            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
        ]]
    });

    LayerUtil.tips({
        content: enableJodConverter ? '当前 OpenOffice 服务已开启，您可以使用文件预览功能' : '当前 OpenOffice 服务未开启，暂不提供文件预览功能',
        follow: '#attachmentHelp',
        trigger: 'click'
    }, {
        tips: [4, enableJodConverter ? '#5FB878' : '#FFB800']
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['attachmentId', 'attachmentName']);
                remove(groupAttrs[0], groupAttrs[1]);
                break;
        }
    });

    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        switch (obj.event) {
            case 'preview':
                window.open(ctx + "/previewFilePage?filePath=" + data.attachmentUrl);
                break;
            case 'download':
                location.href = ctx + '/downloadFile?filePath=' + data.attachmentUrl;
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
            CommonUtil.postAjax(ctx + "/archive/attachment/delAttachment", ids, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                top.layer.close(index);
            })
        });
    }

    window.tableReload = function () {
        layuiTableReload(table, tableId, form.val('attachment-search'));
    }
});
