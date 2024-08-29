layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;
    let tableId = 'notepad-list-tab';
    let layFilter = 'notepad';
    let baseUrl = ctx + '/other/notepad';

    table.render({
        elem: '#' + tableId,
        url: baseUrl + '/list',
        height: 'full-88',
        page: true,
        toolbar: '#toolbar',
        orderName: 'createTime',
        order: 'desc',
        cols: [[
            {type: 'checkbox'},
            {field: 'title', title: '标题', width: '20%', sort: true},
            {field: 'createTime', title: '发布时间', width: '20%', sort: true},
            {field: 'createUserName', title: '发布人', width: '20%'},
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['id', 'title']);
                remove(groupAttrs[0], groupAttrs[1]);
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
                remove([data.id], [data.title]);
                break;
        }
    });

    function open(id) {
        id = id || '';
        LayerUtil.openLayer({
            title: id ? "编辑记事本" : "添加记事本",
            content: baseUrl + "/detailPage?id=" + id,
            area: ['100%', '100%'],
            loaded: function (iframeWin) {
                iframeWin.layui.use(['form'], function () {
                    let form = iframeWin.layui.form;
                    let tinymce = iframeWin.tinymce;

                    if (id) {
                        CommonUtil.getAjax(baseUrl + '/getOne/' + id, {}, function (result) {
                            form.val('notepad-form', result.data);
                            tinymce.get('content').setContent(result.data.content);
                        })
                    }
                });
            },
            submit: function (iframeWin) {
                let form = iframeWin.layui.form;
                let tinymce = iframeWin.tinymce;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }
                let params = form.val('notepad-form');
                params.content = tinymce.get('content').getContent();
                CommonUtil.postAjax(baseUrl + (id ? '/update' : '/insert'), params, function (result) {
                    layer.closeAll();
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                        tableReload();
                    })
                });
            }
        });
    }

    function remove(ids, names) {
        layer.confirm("是否要删除记事本：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(baseUrl + "/delete", ids, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                layer.close(index);
            });
        });
    }

    function tableReload() {
        layuiTableReload(table, tableId, form.val('notepad-search'));
    }

});
