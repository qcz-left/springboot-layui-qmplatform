<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-label {
        width: 120px;
    }
    .layui-form-item .layui-input-inline {
        width: 300px;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">安全设置</div>
        <div class="layui-card-body">
            <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="user-form">
                <input type="hidden" name="id">
                <input type="hidden" name="validateType" value="1">
                <div class="layui-form-item">
                    <label class="layui-form-label required">当前密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="password" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">新密码</label>
                    <div class="layui-input-inline">
                        <input type="password" id="newPassword" name="newPassword" lay-verify="required|newPassword" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid layui-word-aux">密码必须5到12位，且不能出现空格</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">确认新密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="confirmNewPassword" lay-verify="required|confirmNewPassword" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="password-submit">确认修改</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.use(['form'], function () {
        let form = layui.form;
        // 表单数据校验
        form.verify({
            newPassword: [
                /^[\S]{5,12}$/,
                '密码必须5到12位，且不能出现空格'
            ],
            confirmNewPassword: function (value) {
                if (value != $("#newPassword").val()) {
                    return '两次密码填写不一致，请重新填写！';
                }
            }
        });

        form.on('submit(password-submit)', function (data) {
            top.layer.load(2);
            data.field.password = rsaEncrypt(data.field.password);
            CommonUtil.putAjax(ctx + '/user/changeCurrentUserPwd', data.field, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, '修改密码成功，请重新登录验证', '修改密码失败', () => {

                })
            });
            return false;
        });

    });
</script>
</body>
</html>
