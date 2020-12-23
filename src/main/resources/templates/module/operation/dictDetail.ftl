<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="dict-form">
        <input type="hidden" name="dictId">
        <div class="layui-form-item">
            <label class="layui-form-label required">字典名称</label>
            <div class="layui-input-block">
                <input type="text" name="dictName" lay-verify="required" placeholder="请输入字典名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">字典编码</label>
            <div class="layui-input-block">
                <input type="text" name="dictCode" lay-verify="required|dictCode" placeholder="请输入字典编码" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="remark" name="remark"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">排序</label>
            <div class="layui-input-block">
                <input type="number" name="sort" maxlength="5" class="layui-input">
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="dict-submit">保存</button>
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
        form.verify({
            dictCode: function (value) {
                let valid;
                CommonUtil.getSync(ctx + '/operation/dict/validateDictCode', {
                    dictCode: value,
                    dictId: id
                }, function (result) {
                    valid = result.ok;
                })
                if (!valid) {
                    return '字典编码已存在，请重新填写！';
                }
            }
        });

        if (id) {
            CommonUtil.getSync(ctx + '/operation/dict/getDictOne/' + id, {}, function (result) {
                form.val('dict-form', result.data);
            })
        }

        form.on('submit(dict-submit)', function (data) {
            layer.load(2);
            CommonUtil.postOrPut(id, ctx + '/operation/dict/' + (id ? 'updateDict' : 'addDict'), data.field, function (result) {
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
