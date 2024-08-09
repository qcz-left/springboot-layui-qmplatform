layui.use(['table'], function () {
    let table = layui.table;
    let tableId = 'loginRecord-list-tab';
    let layFilter = 'loginRecord';
    table.render({
        elem: '#' + tableId,
        url: ctx + '/operation/loginRecord/getLoginRecordList',
        where: {
            orderName: 'lastLoginTime',
            order: 'desc'
        },
        height: 'full-30',
        page: true,
        toolbar: '#toolbar',
        defaultToolbar: [],
        cols: [[
            {type: 'checkbox'},
            {field: 'loginName', title: '登录名', width: '20%', sort: true},
            {field: 'errorTimes', title: '登录错误次数', width: '10%', sort: true},
            {field: 'lastLoginTime', title: '最近登录时间', width: '15%', sort: true},
            {field: 'lastLoginIp', title: '最近登录IP', width: '15%'},
            {field: 'remark', title: '备注', width: '15%'},
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
            case 'delete':
                let checked = table.checkStatus(tableId).data;
                if (checked.length === 0) {
                    layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                    return;
                }
                let groupAttrs = CommonUtil.groupAttrFromArray(checked, ['recordId', 'loginName']);
                remove(groupAttrs[0], groupAttrs[1]);
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        let recordId = data.recordId;
        let loginName = data.loginName;
        switch (obj.event) {
            case 'delete':
                remove([recordId], [loginName]);
                break;
        }
    });

    /**
     * 登录策略配置
     */
    function strategy() {
        top.layer.open({
            type: 2,
            title: '登录策略配置',
            content: ctx + "/operation/loginRecord/loginStrategyPage",
            area: ['40%', '60%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除登录记录：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.postAjax(ctx + "/operation/loginRecord/deleteLoginRecord", ids, function (data) {
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
