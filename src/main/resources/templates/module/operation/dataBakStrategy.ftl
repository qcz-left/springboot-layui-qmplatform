<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="dataBakStrategy-form">
        <div class="layui-form-item">
            <label class="layui-form-label">备份开关</label>
            <div class="layui-input-block">
                <input type="checkbox" name="enable" value="1" lay-skin="switch" lay-filter="bak-switch" lay-text="开|关">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备份周期</label>
            <div class="layui-input-block">
                <input type="checkbox" name="week1" value="1" title="星期一">
                <input type="checkbox" name="week2" value="2" title="星期二">
                <input type="checkbox" name="week3" value="4" title="星期三">
                <input type="checkbox" name="week4" value="8" title="星期四">
                <input type="checkbox" name="week5" value="16" title="星期五">
                <input type="checkbox" name="week6" value="32" title="星期六">
                <input type="checkbox" name="week7" value="64" title="星期日">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block text-danger">
                <div class="layui-form-mid">磁盘剩余</div>
                <input type="number" name="limitDiskSpace" class="layui-input layui-input-inline" style="width: 60px;">
                <div class="layui-form-mid">G时不允许备份</div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block text-danger">
                <div class="layui-form-mid">删除</div>
                <input type="number" name="saveDays" class="layui-input layui-input-inline" style="width: 60px;">
                <div class="layui-form-mid">天前的备份</div>
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="dataBakStrategy-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        let form = layui.form;
        let layer = layui.layer;

        CommonUtil.getSync(ctx + '/operation/data-bak/getDataBakStrategy', {}, function (result) {
            form.val('dataBakStrategy-form', result.data);
        })

        form.on('submit(dataBakStrategy-submit)', function (data) {
            let index = layer.load(2);
            CommonUtil.postAjax(ctx + '/operation/data-bak/saveDataBakStrategy', data.field, function (result) {
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
