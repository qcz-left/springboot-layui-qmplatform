<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<script type="text/javascript">
top.rsaPublicKey = "${ConfigLoader.getRsaPublicKey()!}";
</script>
<style>
    .layui-form-label {
        width: 100px;
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
        width: 100%;
        height: calc(100% - 85px);
    }

    .hide {
        display: none !important;
    }

</style>
<body class="detail-body">
<div class="layui-fluid">
    <div style="background: white;">
        <div class="ret-header">
            <span class="ret-title">找回密码</span>
            <a href="${ctx}/loginPage" class="btn-login">去登录</a>
        </div>
    </div>
    <br>
    <div id="stepForm" class="jq-steps">
        <div class="steps-nav">

        </div>
        <div class="steps-form-content">
            <div class="step-1 steps-form-item vh-center hide">
                <div class="layui-form" lay-filter="account-info">
                    <div class="layui-form-item">
                        <label class="layui-form-label">登录名</label>
                        <div class="layui-input-inline">
                            <input id="loginName" name="loginName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>
            <div class="step-2 steps-form-item vh-center hide">
                <div class="layui-form detail-form" action="javascript:void(0);" lay-filter="password-form">
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
                </div>
            </div>
            <div class="step-3 steps-form-item vh-center hide">
                修改密码成功，赶快 <a class="text-success" href="${ctx}/loginPage">登录</a> 验证吧！
            </div>
        </div>
        <div class="detail-operator">
            <button id="preStepBtn" type="button" class="layui-btn layui-btn-primary">上一步</button>
            <button id="nextStepBtn" type="button" class="layui-btn layui-btn-normal">下一步</button>
        </div>
    </div>
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

        $("#stepForm").steps({
            direction: 'vertical',
            items: [
                {
                    label: "填写账号信息",
                    bindForm: ".step-1",
                    beforeSubmit: function () {
                        if (!form.doVerify($('[lay-filter="account-info"]'))) {
                            return false;
                        }
                        let success;
                        CommonUtil.getSync(ctx + '/user/nnl/queryPhoneByName', {
                            name: form.val('account-info').loginName
                        }, function (result) {
                            success = result.ok;
                            if (success) {
                                $("#phone").val(result.data);
                            } else {
                                // 未查询到用户信息
                                layer.error(result.msg || "不存在该账号信息，请重新输入！");
                            }
                        });
                        return success;
                    }
                },
                {
                    label: "修改密码",
                    bindForm: ".step-2",
                    beforeSubmit: function () {
                        if (!form.doVerify($('[lay-filter="password-form"]'))) {
                            return false;
                        }
                        let index = top.layer.load(2);
                        let data = form.val('password-form');
                        data.loginname = form.val('account-info').loginName;
                        data.newPassword = rsaEncrypt(data.newPassword);
                        data.confirmNewPassword = rsaEncrypt(data.confirmNewPassword);
                        let success;
                        CommonUtil.postSync(ctx + '/user/nnl/changeUserPwd', data, function (result) {
                            success = result.ok;
                            top.layer.close(index);
                            LayerUtil.respMsg(result, '修改密码成功，请重新登录验证', '修改密码失败');
                        });
                        return success;
                    }
                },
                {
                    label: "修改结果",
                    bindForm: ".step-3"
                }
            ],
            bindPre: "#preStepBtn",
            bindNext: "#nextStepBtn"
        });

        $("#btnCode").click(function () {
            let $this = $(this);
            let loading = layer.load(2);
            CommonUtil.getAjax(ctx + '/user/nnl/getValidateCode', {
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
