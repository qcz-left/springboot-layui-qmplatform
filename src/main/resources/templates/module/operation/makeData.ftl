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
</style>
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="make-data-form">
        <div class="layui-card">
            <div class="layui-card-header">数据库配置</div>
            <div class="layui-card-body">
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
                        <button id="testConnect" class="layui-btn layui-btn-primary layui-border-black">测试连接</button>
                    </div>
                </div>
            </div>
            <div class="layui-card-header">表配置</div>
            <div class="layui-card-body">
                <div class="layui-form-item">
                    <label class="layui-form-label required">表名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="tableName" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">字段</label>
                    <div class="layui-input-inline">
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
            </div>
        </div>
        <div class="detail-operator">
            <button class="layui-btn" lay-submit lay-filter="make-data-submit">执行</button>
        </div>
    </form>
</div>
<script lang="text/javascript">
layui.use(['form', 'xmSelect', 'table'], function () {
    let form = layui.form;
    let xmSelect = layui.xmSelect;
    let table = layui.table;
    // 表单数据校验
    form.verify({});

    xmSelect.render({
        el: '#dbName',
        name: 'dbName',
        radio: true,
        clickClose: true,
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
        cols: [[
            {type: 'numbers'},
            {field: 'name', title: '字段名', width: '20%'},
            {field: 'type', title: '字段类型', width: '20%'},
            {field: 'valueType', title: '值类型', width: '20%'},
            {field: 'value', title: '字段值', width: '20%'},
            {field: 'length', title: '字段长度', width: '10%'},
            {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
        ]]
    });

    table.on('toolbar(columnDetail)', function (obj) {
        switch (obj.event) {
            case 'add':
                openColumnDetail()
                break;
            case 'edit':
                openColumnDetail()
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

    function openColumnDetail() {

    }

    $("#testConnect").click(function () {
        let index = layer.loadingWithText('正在连接数据库...');
        let makeDataForm = form.val('make-data-form');
        makeDataForm.password = rsaEncrypt(makeDataForm.password);
        CommonUtil.postAjax(ctx + '/operation/make-data/testConnect', makeDataForm, function (result) {
            LayerUtil.respMsg(result, "连接成功", "连接失败");
            layer.close(index);
        });
    })
})
</script>
</body>
</html>