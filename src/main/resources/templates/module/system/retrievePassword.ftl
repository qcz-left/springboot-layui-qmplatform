<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-label {
        width: 100px;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="password-form">
        <input type="hidden" name="validateType" value="2">
        <div class="layui-form-item">
            <label class="layui-form-label">手机号码</label>
            <div class="layui-input-inline">
                <input autocomplete="off" class="layui-input" disabled value="${currentUser.phone!''}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">验证码</label>
            <div class="layui-input-inline">
                <input lay-verify="required" name="validateCode" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-inline">
                <button id="btnCode" class="layui-btn layui-btn-primary">获取验证码</button>
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
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="password-submit">确认修改</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        let form = layui.form;
        let layer = layui.layer;
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
            let index = top.layer.load(2);
            CommonUtil.putAjax(ctx + '/user/changeCurrentUserPwd', data.field, function (result) {
                top.layer.close(index);
                LayerUtil.respMsg(result, '修改密码成功，请重新登录验证', '修改密码失败', () => {
                    closeCurrentIframe();
                })
            });
            return false;
        });

        $("#btnCode").click(function () {
            let $this = $(this);
            CommonUtil.getAjax(ctx + '/user/getValidateCode', {}, function (result) {
                if (result.ok) {
                    layer.success(result.msg);
                    $this.addClass('layui-btn-disabled').attr('disabled', true);
                    let time = 60;
                    let interval = window.setInterval(function () {
                        $this.html('（' + time-- + '秒）后重新发送');
                        if (time < 0) {
                            $this.html('获取验证码');
                            $this.removeClass('layui-btn-disabled').removeAttr('disabled');
                            window.clearInterval(interval);
                        }
                    }, 1000);
                }
            })
        });
    });
</script>
</body>
</html>
