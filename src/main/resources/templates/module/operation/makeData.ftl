<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-label {
        width: 120px;
    }

    .layui-form-item .layui-input-inline {
        width: 400px;
    }

    #columnDetailDiv .layui-form-label {
        width: 120px;
    }

    #columnDetailDiv .layui-input-inline {
        width: 300px;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <blockquote class="layui-elem-quote">
        数据库配置
    </blockquote>
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="database-form">
        <div class="layui-form-item">
            <label class="layui-form-label required">数据库类型</label>
            <div class="layui-input-inline">
                <div id="dbName"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">主机</label>
            <div class="layui-input-inline">
                <input type="text" name="host" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">端口</label>
            <div class="layui-input-inline">
                <input type="number" name="port" lay-verify="required" class="layui-input layui-input-inline" style="width: 60px;">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">数据库</label>
            <div class="layui-input-inline">
                <input type="text" name="database" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">用户名</label>
            <div class="layui-input-inline">
                <input type="text" name="user" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">密码</label>
            <div class="layui-input-inline">
                <input type="password" autocomplete="new-password" style="display:none">
                <input type="password" name="password" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-primary layui-border-black" lay-submit lay-filter="connect-test-submit">测试连接</button>
            </div>
        </div>
    </form>
    <blockquote class="layui-elem-quote">
        表配置
    </blockquote>
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="table-form">
        <div class="layui-form-item">
            <label class="layui-form-label required">增加</label>
            <div class="layui-input-block">
                <input type="number" name="insertNumber" lay-verify="required" autocomplete="off" class="layui-input layui-input-inline" style="width: 100px;">
                <div class="layui-form-mid">条数据</div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">表名</label>
            <div class="layui-input-block">
                <input type="text" name="tableName" lay-verify="required" autocomplete="off" class="layui-input layui-input-inline">
                <button id="getColumnListBtn" class="layui-btn layui-btn-warm">获取字段信息</button>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">字段</label>
            <div class="layui-input-inline" style="width: calc(100% - 200px);">
                <script type="text/html" id="toolbar">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
                </div>
                </script>
                <script type="text/html" id="operator">
                <button class="layui-btn layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
                <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </script>
                <table class="layui-hide" id="columnDetail-list-tab" lay-filter="columnDetail"></table>
            </div>
        </div>
    </form>
</div>
<div class="detail-operator">
    <button id="executeBtn" class="layui-btn">执行</button>
    <button id="saveConfigToLocalBtn" class="layui-btn layui-btn-primary">保存配置到本地</button>
    <button id="importLocalConfigBtn" class="layui-btn layui-btn-primary">导入本地配置</button>
</div>
</div>
<div id="columnDetailDiv" style="display: none">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="column-detail-form">
        <div class="layui-form-item">
            <label class="layui-form-label required">字段名</label>
            <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">字段类型</label>
            <div class="layui-input-inline">
                <div id="type">

                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">值类型</label>
            <div class="layui-input-inline">
                <div id="valueType">

                </div>
            </div>
        </div>
        <div class="layui-form-item hide" id="valueDiv">
            <label class="layui-form-label required">字段值</label>
            <div class="layui-input-inline">
                <input type="text" name="value" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item hide" id="lengthDiv">
            <label class="layui-form-label required">字段长度</label>
            <div class="layui-input-inline">
                <input type="number" name="columnLength" lay-verify="required" autocomplete="off" class="layui-input" style="width: 60px;">
            </div>
        </div>
    </form>
</div>
<script lang="text/javascript">
layui.use(['form', 'xmSelect', 'table', 'upload'], function () {
    let form = layui.form;
    let xmSelect = layui.xmSelect;
    let table = layui.table;
    let upload = layui.upload;
    // 表单数据校验
    form.verify({});

    let dbNameSelect = SelectUtil.render(xmSelect, {
        el: '#dbName',
        name: 'dbName',
        radio: true,
        model: {icon: 'hidden'},
        data: [{
            name: 'Postgre数据库',
            value: 'postgresql'
        }, {
            name: 'MySql数据库',
            value: 'mysql'
        }, {
            name: 'Oracle数据库',
            value: 'oracle'
        }, {
            name: 'SQLServer数据库',
            value: 'sqlserver'
        }],
        initValue: ['postgresql']
    });

    let columnDetailTableId = 'columnDetail-list-tab';
    let columnDetailTableIns = table.render({
        elem: '#' + columnDetailTableId,
        toolbar: '#toolbar',
        page: false,
        data: [],
        cols: [[
            {type: 'numbers'},
            {field: 'name', title: '字段名', width: '20%'},
            {
                field: 'type', title: '字段类型', width: '15%', templet: function (row) {
                    switch (row.type) {
                        case 'string':
                            return '字符串';
                        case 'number':
                            return '数字';
                        case 'date':
                            return '时间';
                        case 'ipv4':
                            return 'ipv4';
                        case 'ipv6':
                            return 'ipv6';
                        case 'mac':
                            return 'mac';
                    }
                }
            },
            {
                field: 'valueType', title: '值类型', width: '15%', templet(row) {
                    switch (row.valueType + "") {
                        case "1":
                            return '固定值';
                        case "2":
                            return '随机值';
                    }
                }
            },
            {field: 'value', title: '字段值', width: '20%'},
            {field: 'columnLength', title: '字段长度', width: '10%'},
            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
        ]]
    });

    table.on('toolbar(columnDetail)', function (obj) {
        switch (obj.event) {
            case 'add':
                openColumnDetail(obj)
                break;
        }
    });

    table.on('tool(columnDetail)', function (obj) {
        switch (obj.event) {
            case 'edit':
                openColumnDetail(obj)
                break;
            case 'delete':
                top.layer.confirm('确定要删除吗？', {
                    title: "警告",
                    skin: "my-layer-danger"
                }, function (index) {
                    obj.del();
                    top.layer.close(index);
                });
                break;
        }
    });

    let typeSelect = SelectUtil.render(xmSelect, {
        el: '#type',
        name: 'type',
        radio: true,
        model: {icon: 'hidden'},
        data: [{
            name: '字符串',
            value: 'string'
        }, {
            name: '数字',
            value: 'number'
        }, {
            name: '时间',
            value: 'date'
        }, {
            name: 'ipv4',
            value: 'ipv4'
        }, {
            name: 'ipv6',
            value: 'ipv6'
        }, {
            name: 'mac',
            value: 'mac'
        }]
    });

    let valueTypeSelect = SelectUtil.render(xmSelect, {
        el: '#valueType',
        name: 'valueType',
        radio: true,
        model: {icon: 'hidden'},
        data: [{
            name: '固定值',
            value: 1
        }, {
            name: '随机值',
            value: 2
        }],
        on: function (data) {
            let value = data.arr[0].value;
            let valueSelector = '[name="value"]';
            let lengthSelector = '[name="columnLength"]';
            clearFormValid(valueSelector);
            clearFormValid(lengthSelector);
            if (value === 1) {
                addFormValid(valueSelector)
                $("#valueDiv").show();
                $("#lengthDiv").hide();
            } else if (value === 2) {
                addFormValid(lengthSelector)
                $("#valueDiv").hide();
                $("#lengthDiv").show();
            }
        }
    });

    function openColumnDetail(obj) {
        let action = obj.event;
        let data = obj.data;
        layer.open({
            type: 1,
            area: ['40%', '60%'],
            title: "字段详情",
            content: $('#columnDetailDiv'),
            btn: ['确定', '取消'],
            success: function () {
                if (action === 'add') {
                    form.val('column-detail-form', {
                        name: '',
                        value: '',
                        columnLength: ''
                    });
                    typeSelect.setValue(['string']);
                    valueTypeSelect.setValue([1], null, true);
                }
                if (action === 'edit') {
                    form.val('column-detail-form', data);
                    typeSelect.setValue([data.type]);
                    valueTypeSelect.setValue([data.valueType], null, true);
                }
            },
            yes: function (index) {
                if (!form.doVerify($("[lay-filter=column-detail-form]"))) {
                    return;
                }
                if (action === "add") {
                    let allData = table.selectAll(columnDetailTableId);
                    allData.push(form.val('column-detail-form', data));
                    columnDetailTableIns.reload({
                        data: allData
                    });
                } else if (action === 'edit') {
                    obj.update(form.val('column-detail-form'));
                }
                layer.close(index);
            }
        })
    }

    form.on('submit(connect-test-submit)', function (data) {
        let index = layer.loadingWithText('正在连接数据库...');
        data.field.password = rsaEncrypt(data.field.password);
        CommonUtil.postAjax(ctx + '/operation/make-data/testConnect', data.field, function (result) {
            LayerUtil.respMsg(result, "连接成功", "连接失败");
            layer.close(index);
        });
    })

    $("#getColumnListBtn").click(function () {
        if (!form.doVerify($("[lay-filter=database-form]"))) {
            return;
        }
        let tableName = $("input[name=tableName]").val();
        if (!tableName) {
            top.layer.warning("请填写表名");
            return;
        }

        CommonUtil.postAjax(ctx + '/operation/make-data/getColumnList', {
            dbDetail: form.val("database-form"),
            dataDetail: {
                tableName: tableName
            }
        }, function (result) {
            columnDetailTableIns.reload({
                data: result.data || []
            });
        })
    });

    $("#executeBtn").click(function () {
        if (!form.doVerify($("[lay-filter=database-form]"))) {
            return;
        }
        if (!form.doVerify($("[lay-filter=table-form]"))) {
            return;
        }
        top.layer.load(2);
        let selectAll = table.selectAll(columnDetailTableId);
        let tableName = form.val("table-form").tableName;
        let insertNumber = form.val("table-form").insertNumber;
        CommonUtil.postAjax(ctx + '/operation/make-data/start', {
            dbDetail: form.val("database-form"),
            dataDetail: {
                tableName: tableName,
                columnDetails: selectAll
            },
            insertNumber: insertNumber
        }, function (result) {
            top.layer.closeAll();
            LayerUtil.respMsg(result, "任务执行成功", "任务执行失败");
        });
    })

    $("#saveConfigToLocalBtn").click(function () {
        top.layer.load(2);
        let selectAll = table.selectAll(columnDetailTableId);
        let tableName = form.val("table-form").tableName;
        let insertNumber = form.val("table-form").insertNumber;
        CommonUtil.postAjax(ctx + '/operation/make-data/saveConfigToLocal', {
            dbDetail: form.val("database-form"),
            dataDetail: {
                tableName: tableName,
                columnDetails: selectAll
            },
            insertNumber: insertNumber
        }, function (result) {
            top.layer.closeAll();
            LayerUtil.respMsg(result, null, null, function () {
                window.location = ctx + '/downloadExcelFile?filePath=' + encodeURIComponent(result.data) + "&fileName=制作数据配置.dat";
            });
        });
    })

    upload.render({
        elem: '#importLocalConfigBtn',
        accept: 'file',
        url: ctx + '/operation/make-data/importLocalConfig',
        done: function (result) {
            let data = result.data;
            form.val("database-form", data.dbDetail)
            dbNameSelect.setValue([data.dbDetail.dbName])
            form.val("table-form", {
                tableName: data.dataDetail.tableName,
                insertNumber: data.insertNumber
            })
            columnDetailTableIns.reload({
                data: data.dataDetail.columnDetails
            });
            LayerUtil.respMsg(result, "导入成功", "导入失败");
        }
    });
})
</script>
</body>
</html>