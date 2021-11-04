layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;
    let tableId = 'notepad-list-tab';
    let layFilter = 'notepad';

    table.render({
        elem: '#' + tableId,
        url: ctx + '/notepad/list',
        height: 'full-80',
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
            case 'read':

                break;
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
            content: ctx + "/other/addPage?id=" + id,
            area: ['100%', '100%'],
            loaded: function(iframeWin) {
                let form = iframeWin.layui.form;
                let layedit = iframeWin.layui.layedit;
                //建立编辑器
                let content = layedit.build('content', {
                    height: $("form").height() - 200
                });
                form.render();
                form.verify({
                    content: function(){
                        layedit.sync(content);
                    }
                });
            },
            submit: function (iframeWin) {
                let form = iframeWin.layui.form;
                let layedit = iframeWin.layui.layedit;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }
                let params = data.field;
                params.content = layedit.getContent(content);
                CommonUtil.postAjax(ctx + '/other/notepad/save', params, function (result) {
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
            CommonUtil.deleteAjax(ctx + "/other/notepad/delete", {
                ids: CommonUtil.joinMulti(ids)
            }, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                layer.close(index);
            })
        });
    }

    function tableReload() {
        table.reload(tableId, {
            page: {
                curr: 1
            },
            where: form.val('notepad-search')
        });
    }

});
