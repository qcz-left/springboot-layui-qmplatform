<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="role-form">
        <input type="hidden" name="roleId">
        <div class="layui-form-item">
            <label class="layui-form-label required">角色名称</label>
            <div class="layui-input-block">
                <input type="text" name="roleName" lay-verify="required" placeholder="请输入角色名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">标识</label>
            <div class="layui-input-block">
                <input type="text" name="roleSign" lay-verify="required" placeholder="请输入标识" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="remark" name="remark"></textarea>
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="role-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    let id = "${RequestParameters["id"]}";
    layui.use(['form', 'layer'], function () {
        let form = layui.form;
        let layer = layui.layer;
        // 表单数据校验
        form.verify({});

        if (id) {
            CommonUtil.getSync(ctx + '/role/getRoleOne/' + id, {}, function (result) {
                form.val('role-form', result.data);
            })
        }

        form.on('submit(role-submit)', function (data) {
            layer.load(2);
            CommonUtil.postOrPut(id, ctx + '/role/' + (id ? 'updateRole' : 'addRole'), data.field, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                    reloadParentTable();
                })
            });
            return false;
        });

    });
</script>
</body>
</html>
