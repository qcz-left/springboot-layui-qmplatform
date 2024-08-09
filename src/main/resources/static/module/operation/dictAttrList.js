layui.use(['table'], function () {
    let table = layui.table;
    let tableId = 'dictAttr-list-tab';
    let layFilter = 'dictAttr';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/operation/dict-attr/getDictAttrList/' + dictId,
        where: {
            orderName: 'attrValue'
        },
        height: 'full-30',
        page: true,
        toolbar: '#toolbar',
        defaultToolbar: [],
        cols: [[
            {type: 'checkbox'},
            {field: 'attrName', title: '属性名称', width: '20%', sort: true},
            {field: 'attrValue', title: '属性值', width: '20%'},
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['attrId', 'attrName']);
                remove(groupAttrs[0], groupAttrs[1]);
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let attrId = data.attrId;
        let attrName = data.attrName;
        switch (obj.event) {
            case 'edit':
                open(attrId);
                break;
            case 'delete':
                remove([attrId], [attrName]);
                break;
        }
    });

    function open(attrId) {
        attrId = attrId || '';
        top.layer.open({
            type: 2,
            title: attrId ? "编辑属性" : "添加属性",
            content: ctx + "/operation/dict-attr/dictAttrDetailPage?attrId=" + attrId + "&dictId=" + dictId,
            area: ['30%', '40%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除属性：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(ctx + "/operation/dict-attr/deleteDictAttr", ids, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                top.layer.close(index);
            })
        });
    }

    window.tableReload = function () {
        layuiTableReload(table, tableId, {});
    }

});
