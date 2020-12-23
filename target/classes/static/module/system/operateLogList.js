layui.use(['table', 'form', 'element', 'layer', 'laydate', 'xmSelect'], function () {
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let laydate = layui.laydate;
    let xmSelect = layui.xmSelect;
    let tableId = 'log-list-tab';
    let layFilter = 'log';

    let date = new Date();
    let yestDate = new Date(date.getTime() - 24 * 3600 * 1000).format('yyyy-MM-dd hh:mm:ss');
    laydate.render({
        elem: '#operateTime_start',
        type: 'datetime',
        value: yestDate
    });
    laydate.render({
        elem: '#operateTime_end',
        type: 'datetime'
    });

    // 操作类型数据加载
    let operateTypeSelect = xmSelect.render({
        el: '#operateType',
        name: 'operateType',
        radio: true,
        clickClose: true,
        height: 'auto',
        model: {label: {type: 'text'}},
        data: []
    })
    CommonUtil.getAjax(ctx + '/operation/dict-attr/getDictAttrListByCode', {
        code: 'operate-type'
    }, function (result) {
        result.data.unshift({name: '请选择', value: ''});
        operateTypeSelect.update({
            initValue: [''],
            data: result.data
        })
    })

    // 操作状态数据加载
    let operateStatusSelect = xmSelect.render({
        el: '#operateStatus',
        name: 'operateStatus',
        radio: true,
        clickClose: true,
        height: 'auto',
        model: {label: {type: 'text'}},
        data: []
    })
    CommonUtil.getAjax(ctx + '/operation/dict-attr/getDictAttrListByCode', {
        code: 'operate-status'
    }, function (result) {
        result.data.unshift({name: '请选择', value: ''});
        operateStatusSelect.update({
            initValue: [''],
            data: result.data
        })
    })

    table.render({
        height: 'full-80',
        elem: '#' + tableId,
        url: ctx + '/operate-log/getLogList',
        page: true,
        where: {
            orderName: 'operateTime',
            order: 'desc',
            operateTime_start: yestDate
        },
        cols: [[
            {type: 'numbers'},
            {field: 'operateModule', title: '操作模块', width: '10%', sort: true},
            {field: 'operateUserName', title: '操作人', width: '10%', sort: true},
            {field: 'operateTypeName', title: '操作类型', width: '6%'},
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
