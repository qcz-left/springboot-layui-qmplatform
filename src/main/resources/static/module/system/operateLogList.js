layui.use(['table', 'form', 'laydate'], function () {
    let table = layui.table;
    let form = layui.form;
    let tableId = 'log-list-tab';
    let layFilter = 'log';

    let date = new Date();
    let nowDate = DateUtil.getDate(date) + " 00:00:00";

    let operateTypeData;
    CommonUtil.getSync(ctx + '/operation/dict-attr/getDictAttrListByCode', {
        code: 'operate-type'
    }, function (result) {
        operateTypeData = result.data
    });

    let operateStatusData;
    CommonUtil.getSync(ctx + '/operation/dict-attr/getDictAttrListByCode', {
        code: 'operate-status'
    }, function (result) {
        operateStatusData = result.data
    });

    $("#searchDiv").laySearch({
        layFilter: "log-search",
        data: [
            {key: "operateUserName", label: "操作人", type: "text", placeholder: "操作人关键字"},
            {key: "description", label: "描述内容", type: "text", placeholder: "描述内容关键字"},
            {key: "operateType", label: "操作类型", type: "select", values: operateTypeData},
            {key: "operateStatus", label: "操作状态", type: "select", values: operateStatusData}
        ],
        timeQuery: true,// 开启时间查询
        timeKey: 'operateTime',
        defaultTimeSelected: 'today',
        defaultDataSelected: 'operateStatus',
        doSearch: function () {
            tableReload();
        }
    });

    table.render({
        height: 'full-88',
        elem: '#' + tableId,
        url: ctx + '/operate-log/getLogList',
        page: true,
        where: {
            operateTimeStart: nowDate
        },
        orderName: 'operateTime',
        order: 'desc',
        cols: [[
            {type: 'numbers'},
            {field: 'operateModule', title: '操作模块', width: '10%', sort: true},
            {field: 'operateUserName', title: '操作人', width: '10%', sort: true},
            {field: 'operateTypeName', title: '操作类型', width: '6%'},
            {field: 'ipAddr', title: '操作地址', width: '10%'},
            {field: 'operateTime', title: '操作时间', width: '15%', sort: true},
            {field: 'requestUrl', title: '请求路径', width: '15%'},
            {field: 'description', title: '描述内容'},
            {
                field: 'operateStatus', title: '操作状态', width: '10%', templet: function (row) {
                    return row.operateStatus === 1 ? '<span class="layui-badge layui-bg-green">成功</span>' : '<span style="cursor:pointer;" class="layui-badge layui-bg-danger" lay-event="fail">失败</span>';
                }
            }
        ]]
    });

    sortEventListen(table, layFilter, tableId);

    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        switch (obj.event) {
            case 'fail':
                top.layer.open({
                    type: 1,
                    title: '异常信息',
                    content: '<div style="padding: 10px">' + data.expMsg + '</div>',
                    area: ['50%', '80%']
                });
                break;
        }
    });

    window.tableReload = function () {
        layuiTableReload(table, tableId, form.val('log-search'));
    }
});
