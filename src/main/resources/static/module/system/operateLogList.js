layui.use(['table', 'form', 'element', 'layer', 'laydate'], function () {
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let laydate = layui.laydate;
    let tableId = 'log-list-tab';
    let layFilter = 'log';

    let date = new Date();
    let nowDate = date.format('yyyy-MM-dd hh:mm:ss');
    let yestDate = new Date(date.getTime() - 24 * 3600 * 1000).format('yyyy-MM-dd hh:mm:ss');
    laydate.render({
        elem: '#operateTime',
        type: 'datetime',
        range: true,
        value: yestDate + " - " + nowDate,
        done: function (value, date, endDate) {
            let start_end = value.split(" - ");
            $("#operateTime_start").val(start_end[0]);
            $("#operateTime_end").val(start_end[1]);
        }
    });

    table.render({
        height: 'full-80',
        elem: '#' + tableId,
        url: ctx + '/operate-log/getLogList',
        page: true,
        where: {
            orderName: 'operateTime',
            order: 'desc',
            operateTime_start: yestDate,
            operateTime_end: nowDate
        },
        toolbar: '#toolbar',
        cols: [[
            {type: 'numbers'},
            {field: 'operateModule', title: '操作模块', width: '10%', sort: true},
            {field: 'operateUserName', title: '操作人', width: '10%', sort: true},
            {
                field: 'operateType', title: '操作类型', width: '6%', templet: function (row) {
                    let operateType = row.operateType;
                    switch (operateType) {
                        case -1:
                            operateType = "退出系统";
                            break;
                        case 1:
                            operateType = "登录系统";
                            break;
                        case 2:
                            operateType = "查询";
                            break;
                        case 3:
                            operateType = "新增";
                            break;
                        case 4:
                            operateType = "修改";
                            break;
                        case 5:
                            operateType = "删除";
                            break;
                        default:
                            operateType = "无效数据";
                            break;
                    }
                    return operateType;
                }
            },
            {field: 'ipAddr', title: '操作地址', width: '10%'},
            {
                field: 'operateTime', title: '操作时间', width: '15%', sort: true, templet: function (row) {
                    return new Date(row.operateTime).format('yyyy-MM-dd hh:mm:ss.S');
                }
            },
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

    // 搜索
    $("#btnSearch").click(function () {
        tableReload();
    });

    window.tableReload = function () {
        table.reload(tableId, {
            page: {
                curr: 1
            },
            where: form.val('log-search')
        });
    }
})
