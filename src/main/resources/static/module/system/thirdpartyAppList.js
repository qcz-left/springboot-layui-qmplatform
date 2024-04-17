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
        height: 'full-50',
        page: true,
        toolbar: '#toolbar',
        cols: [[
            {
                field: 'name', title: '应用名称', width: '20%', templet: function (row) {
                    return nameDef[row.name];
                }
            },
            {field: 'appKey', title: '应用ID', width: '20%', sort: true},
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

    function tableReload() {
        layuiTableReload(table, tableId, {});
    }

    function remove(id, name) {
        layer.confirm("是否要删除第三方应用参数配置信息：<span class='text-success'>" + CommonUtil.joinMultiByLen(nameDef[name], 3) + "</span>，删除后将不可恢复！", {
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
            }, null, true, false);
        });
    }

});
