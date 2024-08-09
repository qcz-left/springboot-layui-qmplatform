layui.use(['table', 'treetable'], function () {
    let table = layui.table;
    let treetable = layui.treetable;

    let tableId = 'billType-list-tab';
    let layFilter = 'billType';
    let baseUrl = ctx + '/other/bill-type';
    let tableReload = function () {
        treetable.render({
            elem: '#' + tableId,
            url: baseUrl + '/getBillTypeList',
            height: 'full-30',
            treeColIndex: 0,	//树形图标显示在第几列
            treeSpid: '',		//最上级的父级id
            treeIdName: 'id',	//id字段的名称
            treePidName: 'parentId',	//父级节点字段
            treeDefaultClose: false,	//是否默认折叠
            treeLinkage: false,		//父级展开时是否自动展开所有子级
            toolbar: '#toolbar',
            cols: [[
                {field: 'name', title: '账单类型名称', width: '20%'},
                {field: 'remark', title: '备注', width: '40%'},
                {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
            ]]
        });
    }

    tableReload();

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
            title: id ? "编辑账单类型" : "添加账单类型",
            content: baseUrl + "/billTypeDetailPage?id=" + id,
            area: ['30%', '50%'],
            loaded: function (iframeWin) {
                iframeWin.layui.use(['form', 'xmSelect'], function () {
                    let iframeForm = iframeWin.layui.form;
                    let iframeXmSelect = iframeWin.layui.xmSelect;
                    // 表单数据校验
                    iframeForm.verify({});

                    let detail = {
                        parentId: ''
                    };
                    if (id) {
                        CommonUtil.getSync(baseUrl + '/get/' + id, {}, function (result) {
                            iframeForm.val('billType-form', result.data);
                            detail = result.data;
                        })
                    }

                    // 上级类型数据加载
                    let parentIdSelect = SelectUtil.render(iframeXmSelect, {
                        el: '#parentId',
                        name: 'parentId',
                        radio: true,
                        tree: true,
                        data: []
                    });
                    CommonUtil.getAjax(baseUrl + '/getBillTypeTree', {
                        id: id
                    }, function (result) {
                        parentIdSelect.update({
                            initValue: [detail.parentId],
                            data: result.data
                        })
                    });
                });
            },
            submit: function (iframeWin) {
                let form = iframeWin.layui.form;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }
                layer.load(2);
                CommonUtil.postAjax(baseUrl + (id ? '/update' : '/insert'), form.val('billType-form'), function (result) {
                    layer.closeAll();
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                        tableReload();
                    })
                });
            }
        });
    }

    function remove(ids, names) {
        layer.confirm("是否要删除账单类型：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(baseUrl + "/delete", ids, function (data) {
                LayerUtil.respMsg(data, Msg.DELETE_SUCCESS, Msg.DELETE_FAILURE, function () {
                    tableReload();
                });
                layer.close(index);
            });
        });
    }
});
