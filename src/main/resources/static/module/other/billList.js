layui.use(['table', 'laydate', 'xmSelect', 'form'], function () {
    let table = layui.table;
    let laydate = layui.laydate;
    let xmSelect = layui.xmSelect;
    let form = layui.form;

    let tableId = 'bill-list-tab';
    let layFilter = 'bill';
    let baseUrl = ctx + '/other/bill';

    laydate.render({
        elem: '#consumeTime',
        type: 'date',
        range: ['#consumeTimeStart', '#consumeTimeEnd']
    });

    let typeSelect = SelectUtil.render(xmSelect, {
        el: '#typeId',
        name: 'typeId',
        radio: true,
        tree: true,
        filterable: true,
        data: []
    });
    CommonUtil.getAjax(ctx + '/other/bill-type/getBillTypeTree', {}, function (result) {
        typeSelect.update({
            data: result.data
        })
    });

    table.render({
        elem: '#' + tableId,
        url: baseUrl + '/getBillList',
        height: 'full-131',
        page: true,
        toolbar: '#toolbar',
        orderName: 'consumeTime',
        order: 'desc',
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

    loadStatisticsView()

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
                        type: 'date',
                        value: new Date().format('yyyy-MM-dd')
                    });

                    let iframeTypeSelect = SelectUtil.render(iframeXmSelect, {
                        el: '#typeId',
                        name: 'typeId',
                        radio: true,
                        tree: true,
                        filterable: true,
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
        layuiTableReload(table, tableId, form.val('bill-search'));
        loadStatisticsView();
    }

    /**
     * 数据统计
     */
    function loadStatisticsView() {
        billTypeView();
        billDateView();
    }

    /**
     * 账单分类统计
     */
    function billTypeView() {
        let chartDom = document.getElementById('billTypeView');
        let myChart = echarts.init(chartDom);

        let data;
        let sum = 0;
        CommonUtil.postSync(baseUrl + '/getAmountGroupByType', form.val('bill-search'), function (result) {
            data = result.data;
            for (let i = 0; i < data.length; i++) {
                sum += data[i].value;
            }
        });

        let option = {
            title: {
                text: '账单分类统计',
                left: 'center'
            },
            graphic: {
                type: "text",
                left: "center",
                top: "center",
                style: {
                    text: '总计：' + '\n' + sum.toFixed(2)
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    type: 'pie',
                    radius: ["35%", "65%"],
                    data: data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        myChart.setOption(option);
    }

    /**
     * 账单日消费折线图
     */
    function billDateView() {
        let chartDom = document.getElementById('billDateView');
        let myChart = echarts.init(chartDom);

        let data;
        CommonUtil.postSync(baseUrl + '/getAmountGroupByDate', form.val('bill-search'), function (result) {
            data = result.data;
        });

        let xAxisData = [];
        let yAxisData = [];
        for (let i = 0; i < data.length; i++) {
            xAxisData[i] = data[i].name;
            yAxisData[i] = data[i].value;
        }

        let option = {
            title: {
                text: '日消费趋势',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            xAxis: {
                type: 'category',
                data: xAxisData
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: yAxisData,
                    type: 'line'
                }
            ]
        };

        myChart.setOption(option);
    }

});
