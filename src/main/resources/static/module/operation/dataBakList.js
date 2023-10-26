layui.use(['table'], function () {
    let table = layui.table;
    let tableId = 'dataBak-list-tab';
    let layFilter = 'dataBak';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/operation/data-bak/getDataBakList',
        where: {
            orderName: 'createTime',
            order: 'desc'
        },
        height: 'full-50',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {type: 'checkbox'},
            {field: 'bakName', title: '备份名称', width: '20%', sort: true},
            {field: 'createTime', title: '创建时间', width: '15%', sort: true},
            {
                field: 'fileSize', title: '备份大小', width: '10%', templet: function (row) {
                    let byteSize = row.fileSize;
                    if (!byteSize) {
                        return Common.NULL;
                    }
                    return CommonUtil.convertByte(byteSize);
                }
            },
            {field: 'remark', title: '备份描述', width: '30%'},
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
                    top.layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                    return;
                }
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['bakId', 'bakName']);
                remove(groupAttrs[0], groupAttrs[1]);
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
        top.layer.prompt({
            formType: 2,
            title: '请填写备份描述'
        }, function (val, index) {
            top.layer.close(index);
            let backIndex = top.layer.loadingWithText('正在备份，请稍后...');
            CommonUtil.postAjax(ctx + '/operation/data-bak/exeBackup', {
                remark: val
            }, function (result) {
                LayerUtil.respMsg(result, null, null, function () {
                    tableReload();
                });
                top.layer.close(backIndex);
            });
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
            top.layer.loadingWithText('正在恢复备份，请稍后...');
            CommonUtil.postAjax(ctx + "/operation/data-bak/recoverDataBak/" + bakId, {}, function (data) {
                top.layer.closeAll();
                if (CommonUtil.respSuccess(data)) {
                    LayerUtil.openLayer({
                        title: "恢复备份",
                        content: ctx + "/showLogPage?needReLogin=true&cmdId=" + data.data.cmdId + "&logPath=" + data.data.logPath,
                        area: ['50%', '65%'],
                        closeBtn: 0,
                        loaded: function () {
                            // 完成备份3分钟后自动跳转登录页
                            window.reloadEvent = function () {
                                window.setTimeout(function () {
                                    gotoLoginPage(ResponseCode.DATA_BAK_RECOVER);
                                }, 3 * 60 * 1000);
                            }
                        }
                    }, true);
                } else {
                    top.layer.error(data.msg || "恢复备份失败！");
                }
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
        layuiTableReload(table, tableId, {});
    }

});
