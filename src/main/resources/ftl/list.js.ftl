layui.use(['table'], function () {
    let table = layui.table;
    let tableId = '${entity?uncap_first}-list-tab';
    let layFilter = '${entity?uncap_first}';
    let baseUrl = ctx + '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>';
    table.render({
        elem: '#' + tableId,
        url: baseUrl + '/get${entity}List',
        height: 'full-108',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {type: 'checkbox'},
            {field: 'name', title: '名称', width: '10%', sort: true},
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['id', 'name']);
                remove(groupAttrs[0], groupAttrs[1]);
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let id = data.id;
        let name = data.name;
        switch (obj.event) {
            case 'edit':
                open(id);
                break;
            case 'delete':
                remove([id], [name]);
                break;
        }
    });

    function open(id) {
        id = id || '';
        LayerUtil.openLayer({
            title: id ? "编辑${table.comment!}" : "添加${table.comment!}",
            content: baseUrl + "/${entity?uncap_first}DetailPage?id=" + id,
            area: ['30%', '80%'],
            loaded: function (iframeWin) {
                let form = iframeWin.layui.form;
                // 表单数据校验
                form.verify({});

                if (id) {
                    CommonUtil.getSync(baseUrl + '/get/' + id, {}, function (result) {
                        form.val('${table.entityPath}-form', result.data);
                    })
                }
            },
            submit: function (iframeWin) {
                let form = iframeWin.layui.form;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }
                layer.load(2);
                CommonUtil.postAjax(baseUrl + (id ? '/update' : '/insert'), form.val('${table.entityPath}-form'), function (result) {
                    layer.closeAll();
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                        tableReload();
                    })
                });
            }
        });
    }

    function remove(ids, names) {
        layer.confirm("是否要删除${table.comment!}：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(baseUrl + "/delete", {
                ids: CommonUtil.joinMulti(ids)
            }, function (data) {
                LayerUtil.respMsg(data, Msg.DELETE_SUCCESS, Msg.DELETE_FAILURE, function () {
                    tableReload();
                });
                layer.close(index);
            }, null, true, false);
        });
    }

    function tableReload() {
        layuiTableReload(table, tableId, {});
    }

});
