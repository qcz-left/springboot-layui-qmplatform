layui.use(['table', 'laydate', 'xmSelect', 'form'], function () {
    let table = layui.table;
    let laydate = layui.laydate;
    let xmSelect = layui.xmSelect;
    let form = layui.form;

    let tableId = 'bill-list-tab';
    let layFilter = 'bill';
    let baseUrl = ctx + '/other/bill';

    laydate.render({
        elem: '#consumeTimeStart',
        type: 'datetime'
    });
    laydate.render({
        elem: '#consumeTimeEnd',
        type: 'datetime'
    });

    let typeSelect = xmSelect.render({
        el: '#type',
        name: 'type',
        radio: true,
        clickClose: true,
        height: 'auto',
        model: {icon: 'hidden'},
        data: []
    });
    CommonUtil.getAjax(ctx + '/operation/dict-attr/getDictAttrListByCode', {
        code: 'bill-type'
    }, function (result) {
        typeSelect.update({
            data: result.data
        })
    });

    table.render({
        elem: '#' + tableId,
        url: baseUrl + '/getBillList',
        height: 'full-80',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {type: 'checkbox'},
            {field: 'typeName', title: '账单类型', width: '10%'},
            {field: 'amount', title: '金额', width: '10%', sort: true},
            {field: 'consumeTime', title: '消费时间', width: '15%', sort: true},
            {field: 'consumer', title: '消费人', width: '10%'},
            {field: 'remark', title: '备注', width: '20%'},
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
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['id', 'typeName']);
                remove(groupAttrs[0], groupAttrs[1]);
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let id = data.id;
        let typeName = data.typeName;
        switch (obj.event) {
            case 'edit':
                open(id);
                break;
            case 'delete':
                remove([id], [typeName]);
                break;
        }
    });

    // 搜索
    $("#btnSearch").click(function () {
        tableReload();
    });

    function open(id) {
        id = id || '';
        LayerUtil.openLayer({
            title: id ? "编辑" : "添加",
            content: baseUrl + "/billDetailPage?id=" + id,
            area: ['30%', '60%'],
            loaded: function (iframeWin) {
                iframeWin.layui.use(['form', 'laydate', 'xmSelect'], function () {
                    let iframeForm = iframeWin.layui.form;
                    let iframeLaydate = iframeWin.layui.laydate;
                    let iframeXmSelect = iframeWin.layui.xmSelect;

                    iframeLaydate.render({
                        elem: '#consumeTime',
                        type: 'datetime',
                        value: new Date().format('yyyy-MM-dd hh:mm:ss')
                    });

                    let iframeTypeSelect = iframeXmSelect.render({
                        el: '#typeId',
                        name: 'typeId',
                        radio: true,
                        clickClose: true,//选中关闭
                        tree: {
                            strict: false,
                            show: true,
                            showLine: false,
                            clickExpand: false
                        },
                        prop: {
                            value: 'id',
                            children: 'childes'
                        },
                        data: []
                    });

                    // 表单数据校验
                    iframeForm.verify({});

                    let detail = {
                        typeId: ''
                    };
                    if (id) {
                        CommonUtil.getSync(baseUrl + '/get/' + id, {}, function (result) {
                            iframeForm.val('bill-form', result.data);
                            detail = result.data
                        });
                    }

                    CommonUtil.getAjax(ctx + '/other/bill-type/getBillTypeTree', {}, function (result) {
                        iframeTypeSelect.update({
                            initValue: [detail.typeId],
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
                CommonUtil.postAjax(baseUrl + (id ? '/update' : '/insert'), form.val('bill-form'), function (result) {
                    layer.closeAll();
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                        tableReload();
                    })
                });
            }
        });
    }

    function remove(ids, names) {
        layer.confirm("是否要删除：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
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
        table.reload(tableId, {
            page: {
                curr: 1
            },
            where: form.val('bill-search')
        });
    }

});
