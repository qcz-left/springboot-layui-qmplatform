layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;
    let tableId = 'dict-list-tab';
    let layFilter = 'dict';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/operation/dict/getDictList',
        where: {
            orderName: 'sort'
        },
        height: 'full-80',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {type: 'checkbox'},
            {field: 'dictName', title: '字典名称', width: '10%', sort: true},
            {field: 'remark', title: '描述', width: '20%'},
            {field: 'sort', title: '排序', width: '10%', sort: true},
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['dictId', 'dictName']);
                remove(groupAttrs[0], groupAttrs[1]);
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let dictId = data.dictId;
        let dictName = data.dictName;
        switch (obj.event) {
            case 'allot':
                allotAttr(dictId, dictName);
                break;
            case 'edit':
                open(dictId);
                break;
            case 'delete':
                remove([dictId], [dictName]);
                break;
        }
    });

    function allotAttr(dictId, dictName) {
        top.layer.open({
            id: 'dictIframe',
            type: 2,
            title: "定义属性 - " + dictName,
            content: ctx + "/operation/dict-attr/dictAttrListPage/?dictId=" + dictId,
            area: ['50%', '80%']
        });
    }

    function open(id) {
        id = id || '';
        top.layer.open({
            type: 2,
            title: id ? "编辑字典" : "添加字典",
            content: ctx + "/operation/dict/dictDetailPage?id=" + id,
            area: ['30%', '40%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除字典：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/operation/dict/deleteDict", {
                dictIds: CommonUtil.joinMulti(ids)
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
            where: form.val('dict-search')
        });
    }

});
