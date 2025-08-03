layui.use(['form', 'table'], function () {
    let form = layui.form;
    let table = layui.table;
    let tableId = 'thirdpartyApp-list-tab';
    let layFilter = 'thirdpartyApp';
    let baseUrl = ctx + '/system/thirdparty-app';

    let nameDef = {
        "dingtalk-code": "钉钉扫码",
        "dingtalk-synchro": "钉钉组织架构同步",
        "work-wechat-synchro": "企业微信组织架构同步"
    }
    table.render({
        elem: '#' + tableId,
        url: baseUrl + '/getThirdpartyAppList',
        height: 'full-30',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {
                field: 'name', title: '应用名称', width: '20%', templet: function (row) {
                    return nameDef[row.name];
                }
            },
            {field: 'appKey', title: '应用ID', width: '20%', sort: true},
            // {field: 'status', title: '状态', align: 'center', width: '20%', templet: '#status'},
            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
        ]]
    });

    form.on('switch(status)', function (obj) {
        let operateName = obj.othis[0].innerText;
        CommonUtil.postAjax(baseUrl + "/updateStatus", {
            id: $(obj.elem).attr("data-id"),
            name: $(obj.elem).attr("data-name"),
            status: obj.elem.checked ? 1 : 0
        }, function (result) {
            LayerUtil.respMsg(result, operateName + "成功", operateName + "失败");
        });
    });

    sortEventListen(table, layFilter, tableId);

    // 头工具栏监听事件
    table.on('toolbar(' + layFilter + ')', function (obj) {
        switch (obj.event) {
            case 'add':
                open();
                break;
            case 'manageBindAccount':
                openManageBindAccountPage();
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
                remove(id, name);
                break;
        }
    });

    function open(id) {
        id = id || '';

        LayerUtil.openLayer({
            title: id ? "编辑第三方应用参数配置信息" : "添加第三方应用参数配置信息",
            content: baseUrl + "/thirdpartyAppDetailPage?id=" + id,
            area: ['30%', '50%'],
            loaded: function (iframeWin) {
                let form = iframeWin.layui.form;
                // 表单数据校验
                form.verify({
                    name: function (value) {
                        let valid;
                        CommonUtil.getSync(baseUrl + "/validateName", {
                            id: id,
                            name: value
                        }, function (result) {
                            valid = result.ok;
                        });
                        if (!valid) {
                            return "该应用已存在，请更换！";
                        }
                    }
                });

                if (id) {
                    CommonUtil.getSync(baseUrl + '/get/' + id, {}, function (result) {
                        result.data.appSecret = Password.UN_CHANGED_PASSWORD;
                        form.val('thirdpartyApp-form', result.data);
                    })
                }
            },
            submit: function (iframeWin) {
                let form = iframeWin.layui.form;
                if (!form.doVerify(iframeWin.$("form"))) {
                    return false;
                }

                let params = form.val('thirdpartyApp-form');
                params.appSecret = rsaEncrypt(params.appSecret);

                layer.load(2);
                CommonUtil.postAjax(baseUrl + (id ? '/update' : '/insert'), params, function (result) {
                    layer.closeAll();
                    LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                        tableReload();
                    })
                });
            }
        });
    }

    /**
     * 管理绑定账号页面
     */
    function openManageBindAccountPage() {
        LayerUtil.openLayer({
            title: "管理绑定账号",
            content: baseUrl + "/manageBindAccountPage",
            area: ['50%', '80%'],
            loaded: function (iframeWin) {
                iframeWin.layui.use(['table'], function () {
                    let iframeWinTable = iframeWin.layui.table;
                    let appNameDef = {
                        "dingtalk": "钉钉"
                    };
                    iframeWinTable.render({
                        elem: '#manageBindAccount-list-tab',
                        url: baseUrl + '/manageBindAccountList',
                        height: 'full-30',
                        page: true,
                        cols: [[
                            {
                                field: 'appName', title: '平台名称', width: '20%', templet: function (row) {
                                    return appNameDef[row.appName];
                                }
                            },
                            {field: 'accountName', title: '账号名称', width: '20%', sort: true},
                            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
                        ]]
                    });

                    function bindAccountTableReload() {
                        layuiTableReload(iframeWinTable, "manageBindAccount-list-tab", {});
                    }

                    function deleteBindAccount(id, name) {
                        iframeWin.layer.confirm("是否要删除第三方绑定账号：<span class='text-success'>" + name + "</span>，删除后将不可恢复！", {
                            title: "警告",
                            skin: "my-layer-danger"
                        }, function (index) {
                            CommonUtil.postAjax(baseUrl + "/deleteBindAccount/" + id, {
                                id: id
                            }, function (data) {
                                LayerUtil.respMsg(data, Msg.DELETE_SUCCESS, Msg.DELETE_FAILURE, function () {
                                    bindAccountTableReload();
                                });
                                iframeWin.layer.close(index);
                            });
                        });
                    }

                    iframeWinTable.on('tool(manageBindAccount)', function (obj) {
                        let data = obj.data;
                        let id = data.thirdpartyId
                        let name = appNameDef[data.appName] + "-" + data.accountName;
                        switch (obj.event) {
                            case 'delete':
                                deleteBindAccount(id, name);
                                break;
                        }
                    });
                });
            }
        });
    }

    function tableReload() {
        layuiTableReload(table, tableId, {});
    }

    function remove(id, name) {
        layer.confirm("是否要删除第三方应用参数配置信息：<span class='text-success'>" + nameDef[name] + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(baseUrl + "/delete", {
                id: id,
                name: name
            }, function (data) {
                LayerUtil.respMsg(data, Msg.DELETE_SUCCESS, Msg.DELETE_FAILURE, function () {
                    tableReload();
                });
                layer.close(index);
            });
        });
    }

});
