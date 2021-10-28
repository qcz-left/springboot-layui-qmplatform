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
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="mail-form">
        <div class="layui-card">
            <div class="layui-card-header">邮箱配置</div>
            <div class="layui-card-body">
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱服务商</label>
                    <div class="layui-input-inline">
                        <div id="host"></div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">服务端口</label>
                    <div class="layui-input-inline">
                        <input type="number" name="port" lay-verify="required" class="layui-input layui-input-inline" style="width: 60px;">
                        <input type="checkbox" name="enableSSL" lay-filter="enableSSL" value="true" class="layui-input-inline" title="加密连接">
                        <div class="layui-form-mid layui-word-aux">邮箱默认端口一般非加密填25，加密填465，根据实际情况填写</div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">发送邮箱地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="senderHost" lay-verify="required|email" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">发送邮箱密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="senderPwd" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <button id="testMail" class="layui-btn layui-btn-primary layui-border-black">测试</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="detail-operator">
            <button class="layui-btn" lay-submit lay-filter="sms-submit">保存</button>
            <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'xmSelect'], function () {
        let form = layui.form;
        let xmSelect = layui.xmSelect;
        // 表单数据校验
        form.verify({});

        let detail = {};
        // 是否未初始化配置
        let uninitialized = false;
        CommonUtil.getSync(ctx + '/notify/getMailConfig', {}, function (result) {
            detail = result.data;
            uninitialized = !detail.host;
            if (uninitialized) {
                detail.host = '';
                detail.port = 25;
                detail.senderPwd = '';
            }
            form.val('mail-form', detail);
        });

        // 邮箱配置数据加载
        xmSelect.render({
            el: '#host',
            name: 'host',
            radio: true,
            clickClose: true,
            model: {icon: 'hidden'},
            data: [{
                name: '网易163邮箱',
                value: 'smtp.163.com'
            }, {
                name: 'QQ邮箱',
                value: 'smtp.qq.com'
            }],
            initValue: [detail.host]
        });

        form.on('checkbox(enableSSL)', function(data){
            if (uninitialized) {
                form.val('mail-form', {
                    'port': data.elem.checked ? 465 : 25
                });
            }
        });

        form.on('submit(sms-submit)', function (data) {
            top.layer.load(2);
            data.field.senderPwd = rsaEncrypt(data.field.senderPwd);
            CommonUtil.postAjax(ctx + '/notify/saveMailConfig', data.field, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE);
            });
            return false;
        });

        $("#testMail").click(function () {
            layer.prompt({
                formType: 0,
                title: '请输入接收邮件地址'
            }, function(val, index){
                layer.close(index);
                let backIndex = layer.loadingWithText('正在发送邮件...');
                let mailConfig = form.val('mail-form');
                mailConfig.senderPwd = rsaEncrypt(mailConfig.senderPwd);
                CommonUtil.postAjax(ctx + '/notify/testSendMail', {
                    mailConfig: mailConfig,
                    mailParam: {
                        to: val,
                        subject: '测试',
                        content: '测试发送邮件'
                    }
                }, function (result) {
                    LayerUtil.respMsg(result, "发送成功", "发送失败");
                    layer.close(backIndex);
                });
            });
        });

    });
</script>
</body>
</html>
