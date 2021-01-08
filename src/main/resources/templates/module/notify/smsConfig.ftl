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
    <div class="layui-card">
        <div class="layui-card-header">短信配置</div>
        <div class="layui-card-body">
            <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="sms-form">
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
                <div class="layui-form-item">
                    <label class="layui-form-label required">模板ID</label>
                    <div class="layui-input-inline">
                        <input type="text" name="templateID" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">模板参数个数</label>
                    <div class="layui-input-inline">
                        <input type="number" name="templateParamCnt" class="layui-input" value="0">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="sms-submit">保存</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.use(['form','xmSelect'], function () {
        let form = layui.form;
        let xmSelect = layui.xmSelect;
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
        })
        CommonUtil.getAjax(ctx + '/operation/dict-attr/getDictAttrListByCode', {
            code: 'sms-provider'
        }, function (result) {
            smsProviderSelect.update({
                initValue: [detail.smsProvider],
                data: result.data
            })
        })

        form.on('submit(sms-submit)', function (data) {
            top.layer.load(2);
            CommonUtil.postAjax(ctx + '/notify/saveSmsConfig', data.field, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE);
            });
            return false;
        });

    });
</script>
</body>
</html>
