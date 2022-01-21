<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-label {
        width: 100px;
    }

    .layui-form {
        margin: 0 auto;
        max-width: 560px;
        padding-top: 40px;
    }

    body {
        background: #f4f4f4;
    }

    .ret-header {
        height: 45px;
        margin: 0 auto;
        width: 60%;
        padding-top: 20px;
    }

    .ret-title {
        color: #2c82ff;
        font-size: 18px;
    }

    .btn-login {
        float: right;
    }

    #stepForm {
        margin: 0 auto;
        background: white!important;
        padding-top: 10px;
    }
</style>
<body>
<div style="background: white;">
    <div class="ret-header">
        <span class="ret-title">找回密码</span>
        <a href="${ctx}/loginPage" class="btn-login">去登录</a>
    </div>
</div>
<br>
<div class="layui-carousel" id="stepForm" lay-filter="stepForm">
    <div carousel-item>
        <div>
            <form class="layui-form">
                <div class="layui-form-item">
                    <label class="layui-form-label">登录名</label>
                    <div class="layui-input-inline">
                        <input id="loginName" name="loginName" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="confirm-loginName">确定</button>
                    </div>
                </div>
            </form>
        </div>
        <div>
            <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="password-form">
                <input type="hidden" name="validateType" value="2">
                <div class="layui-form-item">
                    <label class="layui-form-label">手机号码</label>
                    <div class="layui-input-inline">
                        <input id="phone" autocomplete="off" class="layui-input" disabled>
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
                        <input type="password" autocomplete="new-password" style="display:none">
                        <input type="password" id="newPassword" name="newPassword" lay-verify="required|newPassword" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">确认新密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="confirmNewPassword" lay-verify="required|confirmNewPassword" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button type="button" class="layui-btn layui-btn-primary pre">上一步</button>
                        <button class="layui-btn" lay-submit lay-filter="password-submit">确认修改</button>
                    </div>
                </div>
            </form>
        </div>
        <div>
            <form class="layui-form">
                修改密码成功，赶快 <a class="text-success" href="${ctx}/loginPage">登录</a> 验证吧！
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.use(['form', 'layer', 'step'], function () {
        let form = layui.form;
        let layer = layui.layer;
        let step = layui.step;

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

        step.render({
            elem: '#stepForm',
            filter: 'stepForm',
            width: '60%', //设置容器宽度
            stepWidth: '750px',
            height: '500px',
            stepItems: [{
                title: '填写账号信息'
            }, {
                title: '修改密码'
            }, {
                title: '修改结果'
            }]
        });

        /**
         * 填写账号，确定下一步
         */
        form.on('submit(confirm-loginName)', function (data) {
            CommonUtil.getAjax(ctx + '/user/noNeedLogin/queryPhoneByName', {
                name: data.field.loginName
            }, function (result) {
                if (result.ok) {
                    $("#phone").val(result.data);
                    step.next('#stepForm');
                } else {
                    // 未查询到用户信息
                    layer.error(result.msg || "不存在该账号信息，请重新输入！")
                }
            });
            return false;
        });

        /**
         * 修改密码提交结果
         */
        form.on('submit(password-submit)', function (data) {
            let index = top.layer.load(2);
            data.field.loginname = $("#loginName").val();
            data.field.newPassword = rsaEncrypt(data.field.newPassword);
            data.field.confirmNewPassword = rsaEncrypt(data.field.confirmNewPassword);
            CommonUtil.putAjax(ctx + '/user/noNeedLogin/changeUserPwd', data.field, function (result) {
                top.layer.close(index);
                LayerUtil.respMsg(result, '修改密码成功，请重新登录验证', '修改密码失败', () => {
                    step.next('#stepForm');
                })
            });
            return false;
        });

        $('.pre').click(function () {
            step.pre('#stepForm');
        });

        $('.next').click(function () {
            step.next('#stepForm');
        });

        $("#btnCode").click(function () {
            let $this = $(this);
            let loading = layer.load(2);
            CommonUtil.getAjax(ctx + '/user/noNeedLogin/getValidateCode', {
                phone: $("#phone").val()
            }, function (result) {
                layer.close(loading);
                let data = result.data;
                if (result.ok) {
                    layer.success('验证码已发送到手机：' + data.phone + '，请注意查收！');
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
                } else {
                    layer.alert('验证码发送失败！错误码：<p class="text-danger">' + data.code + '</p>', {
                        icon: 2,
                        title: null,
                        btn: [],
                        offset: '30px'
                    });
                }
            })
        });
    });
</script>
</body>
</html>
