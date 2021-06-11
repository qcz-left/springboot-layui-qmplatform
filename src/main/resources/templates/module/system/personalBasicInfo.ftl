<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-item .layui-input-inline {
        width: 300px;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">设置我的资料</div>
        <div class="layui-card-body">
            <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="user-form">
                <input type="hidden" name="id">
                <div class="layui-form-item">
                    <label class="layui-form-label">我的角色</label>
                    <div class="layui-input-inline">
                        <div id="roleName" disabled class="layui-input"></div>
                    </div>
                    <div class="layui-form-mid layui-word-aux">修改角色请到系统管理-用户管理页面</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">登录名</label>
                    <div class="layui-input-inline">
                        <div id="loginname" disabled class="layui-input"></div>
                    </div>
                    <div class="layui-form-mid layui-word-aux">不可修改</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="username" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="userSex" value="1" title="男" checked>
                        <input type="radio" name="userSex" value="2" title="女">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">手机号码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" lay-verify="required|phone" autocomplete="off" placeholder="请输入手机号" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">邮箱</label>
                    <div class="layui-input-inline">
                        <input type="text" name="emailAddr" lay-verify="required|email" autocomplete="off" placeholder="请输入邮箱" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea class="layui-textarea" id="remark" name="remark"></textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="user-submit">确认修改</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
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
        form.verify({});

        CommonUtil.getSync(ctx + '/user/getCurrentUserInfo', {}, function (result) {
            form.val('user-form', result.data);
            $("#roleName").text(result.data.roleName);
            $("#loginname").text(result.data.loginname);
        });

        form.on('submit(user-submit)', function (data) {
            top.layer.load(2);
            CommonUtil.putAjax(ctx + '/user/saveCurrentUserInfo', data.field, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                    top.$("#currentUsername").text(form.val('user-form').username)
                })
            });
            return false;
        });

    });
</script>
</body>
</html>
