<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-item label {
        width: 100px;
    }

    .layui-form-item .layui-input-block {
        margin-left: 130px;
    }
</style>
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="org-form">
        <input type="hidden" name="organizationId">
        <div class="layui-form-item">
            <label class="layui-form-label required">组织机构名称</label>
            <div class="layui-input-block">
                <input type="text" name="organizationName" lay-verify="required" placeholder="请输入组织机构名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">组织机构编码</label>
            <div class="layui-input-block">
                <input type="text" name="organizationCode" lay-verify="required" placeholder="请输入组织机构编码" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">所属上级</label>
            <div class="layui-input-block">
                <div id="parentId" name="parentId"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">排序</label>
            <div class="layui-input-block">
                <input type="number" name="iorder" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="remark" name="remark"></textarea>
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="org-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    let id = "${RequestParameters["id"]}";
    layui.use(['form', 'layer', 'xmSelect'], function () {
        let form = layui.form;
        let layer = layui.layer;
        let xmSelect = layui.xmSelect;
        // 表单数据校验
        form.verify({});

        let detail = {};
        if (id) {
            CommonUtil.getSync(ctx + '/organization/getOrgOne/' + id, {}, function (result) {
                form.val('org-form', result.data);
                detail = result.data;
            })
        }

        // 上级权限数据加载
        let parentIdSelect = xmSelect.render({
            el: '#parentId',
            name: 'parentId',
            radio: true,
            clickClose: true,//选中关闭
            tree: {
                strict: false,
                show: true,
                showLine: false,
                clickExpand: false
            },
            prop: {
                value: 'id',
                children: 'childes'
            },
            data: []
        })
        CommonUtil.getAjax(ctx + '/organization/getOrgTree', {
            organizationId: id
        }, function (result) {
            parentIdSelect.update({
                initValue: [detail.parentId],
                data: result.data
            })
        })

        form.on('submit(org-submit)', function (data) {
            layer.load(2);
            CommonUtil.postOrPut(id, ctx + '/organization/' + (id ? 'updateOrg' : 'addOrg'), data.field, function (result) {
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
