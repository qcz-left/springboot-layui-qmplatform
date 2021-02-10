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
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="sms-form">
        <div class="layui-card">
            <div class="layui-card-header">短信配置</div>
            <div class="layui-card-body">

                <div class="layui-form-item">
                    <label class="layui-form-label">短信提供商</label>
                    <div class="layui-input-inline">
                        <div id="smsProvider"></div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">AppId</label>
                    <div class="layui-input-inline">
                        <input type="text" name="appId" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">SecretId</label>
                    <div class="layui-input-inline">
                        <input type="text" name="secretId" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">SecretKey</label>
                    <div class="layui-input-inline">
                        <input type="password" name="secretKey" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">短信签名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sign" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-card-header">短信模板列表</div>
            <div class="layui-card-body">
                <script type="text/html" id="operator">
                    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
                </script>
                <div class="layui-inline" id="templateType" style="width: 150px;"></div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-sm" id="btnAdd"><i class="layui-icon layui-icon-addition"></i>选择模板添加</button>
                </div>
                <table class="layui-hide" id="template-list-tab" lay-filter="template"></table>
            </div>
        </div>
        <div class="detail-operator">
            <button class="layui-btn" lay-submit lay-filter="sms-submit">保存</button>
            <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'xmSelect', 'table'], function () {
        let form = layui.form;
        let xmSelect = layui.xmSelect;
        let table = layui.table;
        // 表单数据校验
        form.verify({});

        let detail;
        CommonUtil.getSync(ctx + '/notify/getSmsConfig', {}, function (result) {
            form.val('sms-form', result.data);
            detail = result.data;
        });

        // 短信数据加载
        let smsProviderSelect = xmSelect.render({
            el: '#smsProvider',
            name: 'smsProvider',
            radio: true,
            clickClose: true,
            model: {icon: 'hidden'},
            data: []
        });
        CommonUtil.getAjax(ctx + '/operation/dict-attr/getDictAttrListByCode', {
            code: 'sms-provider'
        }, function (result) {
            smsProviderSelect.update({
                initValue: [detail.smsProvider],
                data: result.data
            })
        });

        // 短信模板参数
        let tableId = 'template-list-tab';
        let tableIns = table.render({
            elem: '#' + tableId,
            data: Object.values(detail.templateParams || {}),
            cols: [[
                {type: 'numbers'},
                {
                    field: 'templateType', title: '模板名称', width: '20%', templet: function (row) {
                        return CommonUtil.getTemplateTypeName(row.templateType);
                    }
                },
                {field: 'templateID', title: '模板ID', width: '20%', edit: true},
                {field: 'paramCnt', title: '参数个数', width: '20%', edit: true},
                {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
            ]]
        });

        // 短信模板类型
        let templateTypeSelect = xmSelect.render({
            el: '#templateType',
            name: 'templateType',
            radio: true,
            clickClose: true,
            model: {icon: 'hidden', label: {type: 'text'}}
        });
        reloadTemplateType();
        $('#btnAdd').click(function () {
            let selectType = templateTypeSelect.getValue('valueStr');
            if (!selectType) {
                top.layer.warning('请选择模板类型！');
                return;
            }
            let allData = table.selectAll(tableId);
            allData.push({
                templateType: selectType
            });
            tableIns.reload({
                data: allData
            });
            // 同步更新模板类型下拉数据
            reloadTemplateType();
        });

        // 操作监听事件
        table.on('edit(template)', function (obj) {
            if (obj.field === 'paramCnt') {
                obj.data.paramCnt = CommonUtil.removeNotNumStr(obj.data.paramCnt);
                obj.update(obj.data);
                tableIns.reload();
            }
        });

        table.on('tool(template)', function (obj) {
            switch (obj.event) {
                case 'delete':
                    top.layer.confirm('确定要删除吗？', {
                        title: "警告",
                        skin: "my-layer-danger"
                    }, function (index) {
                        obj.del();
                        // 同步更新模板类型下拉数据
                        reloadTemplateType();
                        top.layer.close(index);
                    });
                    break;
            }
        });

        form.on('submit(sms-submit)', function (data) {
            top.layer.load(2);
            let selectAll = table.selectAll(tableId);
            let templateParams = {};
            for (let i = 0; i < selectAll.length; i++) {
                let item = selectAll[i];
                templateParams[item.templateType] = item;
            }
            data.field.templateParams = templateParams;
            CommonUtil.postAjax(ctx + '/notify/saveSmsConfig', data.field, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE);
            });
            return false;
        });

        function getTemplateTypeData() {
            return [{
                name: TemplateType.VALIDATE_CODE/* 验证码 */,
                value: 1
            }, {
                name: TemplateType.ALARM/* 告警 */,
                value: 2
            }, {
                name: TemplateType.NOTIFY/* 广播通知 */,
                value: 3
            }];
        }

        /**
         * 更新模板类型下拉框数据
         */
        function reloadTemplateType() {
            let templateTypeData = getTemplateTypeData();
            CommonUtil.removeArrayItem(templateTypeData, 'value', CommonUtil.getAttrFromArray(table.selectAll(tableId), 'templateType'));
            templateTypeSelect.update({
                initValue: templateTypeData.length > 0 ? [templateTypeData[0].value] : [],
                data: templateTypeData
            });
        }

    });
</script>
</body>
</html>
