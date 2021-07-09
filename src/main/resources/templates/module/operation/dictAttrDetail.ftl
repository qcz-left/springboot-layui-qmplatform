<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="dictAttr-form">
        <input type="hidden" name="attrId">
        <input type="hidden" name="dictId" value="${RequestParameters["dictId"]}">
        <div class="layui-form-item">
            <label class="layui-form-label required">属性名称</label>
            <div class="layui-input-block">
                <input type="text" name="attrName" lay-verify="required" placeholder="请输入属性名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">属性值</label>
            <div class="layui-input-block">
                <input type="number" name="attrValue" lay-verify="required|attrValue" placeholder="请输入属性值" class="layui-input">
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="dictAttr-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    let attrId = "${RequestParameters["attrId"]}";
    let dictId = "${RequestParameters["dictId"]}";
    layui.use(['form', 'layer'], function () {
        let form = layui.form;
        let layer = layui.layer;
        // 表单数据校验
        form.verify({
            attrValue: function (value) {
                let valid;
                CommonUtil.getSync(ctx + '/operation/dict-attr/validateAttrValue', {
                    attrValue: value,
                    attrId: attrId,
                    dictId: dictId
                }, function (result) {
                    valid = result.ok;
                });
                if (!valid) {
                    return '属性值已存在，请重新填写！';
                }
            }
        });

        if (attrId) {
            CommonUtil.getSync(ctx + '/operation/dict-attr/getDictAttrOne/' + attrId, {}, function (result) {
                form.val('dictAttr-form', result.data);
            })
        }

        form.on('submit(dictAttr-submit)', function (data) {
            let index = layer.load(2);
            CommonUtil.postOrPut(attrId, ctx + '/operation/dict-attr/' + (attrId ? 'updateDictAttr' : 'addDictAttr'), data.field, function (result) {
                layer.close(index);
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                    reloadTable("dictIframe");
                    closeCurrentIframe();
                })
            });
            return false;
        });

    });
</script>
</body>
</html>
