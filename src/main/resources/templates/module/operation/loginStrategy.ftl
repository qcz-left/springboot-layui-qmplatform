<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="loginStrategy-form">
        <div class="layui-form-item">
            <label class="layui-form-label">策略开关</label>
            <div class="layui-input-block">
                <input type="checkbox" name="enable" value="1" lay-skin="switch" lay-filter="bak-switch" lay-text="开|关">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <div class="layui-form-mid">登录失败超过</div>
                <input type="number" name="codeAtErrorTimes" value="${loginStrategy.codeAtErrorTimes!''}" class="layui-input layui-input-inline" style="width: 60px;">
                <div class="layui-form-mid">次时需要填写验证码</div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <div class="layui-form-mid">密码认证失败超过</div>
                <input type="number" name="lockAtErrorTimes" value="${loginStrategy.lockAtErrorTimes!''}" class="layui-input layui-input-inline" style="width: 60px;">
                <div class="layui-form-mid">次时锁定账号</div>
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="loginStrategy-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form'], function () {
        let form = layui.form;

        form.on('submit(loginStrategy-submit)', function (data) {
            let index = layer.load(2);
            CommonUtil.postAjax(ctx + '/operation/loginRecord/saveLoginStrategy', data.field, function (result) {
                layer.close(index);
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                    closeCurrentIframe();
                })
            });
            return false;
        });

    });
</script>
</body>
</html>
