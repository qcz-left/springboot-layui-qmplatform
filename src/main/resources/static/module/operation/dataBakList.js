layui.use(['table', 'form', 'element', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let tableId = 'dataBak-list-tab';
    let layFilter = 'dataBak';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/operation/data-bak/getDataBakList',
        where: {
            orderName: 'createTime'
        },
        height: 'full-30',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {type: 'checkbox'},
            {field: 'bakName', title: '备份名称', width: '20%', sort: true},
            {
                field: 'createTime', title: '创建时间', width: '20%', sort: true, templet: function (row) {
                    return new Date(row.createTime).format('yyyy-MM-dd hh:mm:ss.S');
                }
            },
            {field: 'bakPath', title: '备份路径', width: '20%'},
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
            case 'strategy':
                strategy();
                break;
            case 'add':
                backup();
                break;
            case 'delete':
                let checked = table.checkStatus(tableId).data;
                if (checked.length === 0) {
                    layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                    return;
                }
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['bakId', 'bakName']);
                remove(groupAttrs[0], groupAttrs[1])
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let bakId = data.bakId;
        let bakName = data.bakName;
        switch (obj.event) {
            case 'recover':
                recover(bakId, bakName);
                break;
            case 'delete':
                remove([bakId], [bakName]);
                break;
        }
    });

    /**
     * 备份策略配置
     */
    function strategy() {
        top.layer.open({
            type: 2,
            title: '备份策略配置',
            content: ctx + "/operation/data-bak/dataBakStrategyPage",
            area: ['40%', '60%']
        });
    }

    /**
     * 立即备份
     */
    function backup() {
        let index = top.layer.loadingWithText('正在备份，请稍后...');
        CommonUtil.postAjax(ctx + '/operation/data-bak/exeBackup', {}, function (result) {
            LayerUtil.respMsg(result, null, null, function () {
                tableReload();
            });
            top.layer.close(index);
        });
    }

    /**
     * 恢复备份
     * @param bakId
     * @param bakName
     */
    function recover(bakId, bakName) {
        top.layer.confirm("是否要从：<span class='text-success'>" + bakName + "</span>，恢复备份？", {
            title: "警告"
        }, function () {
            let index = top.layer.loadingWithText('正在恢复备份，请稍后...');
            CommonUtil.postAjax(ctx + "/operation/data-bak/recoverDataBak/" + bakId, {}, function (data) {
                LayerUtil.respMsg(data, "恢复备份成功！", "恢复备份失败！");
                top.layer.closeAll();
            })
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除备份：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/operation/data-bak/deleteDataBak", {
                dataBakIds: CommonUtil.joinMulti(ids)
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
            }
        });
    }

});
