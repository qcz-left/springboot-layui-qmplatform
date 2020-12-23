<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="attachment-form">
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <div class="layui-upload-drag" id="attachment">
                    <i class="layui-icon"></i>
                    <p>点击上传，或将图片拖拽到此处</p>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">文件说明</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="description" name="description"></textarea>
            </div>
        </div>
        <div class="detail-operator">
            <button id="upload" class="layui-btn layui-btn-normal">上传</button>
            <button class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'layer', 'upload'], function () {
        let form = layui.form;
        let layer = layui.layer;
        let upload = layui.upload;

        upload.render({
            elem: '#attachment',
            accept: 'file',
            url: ctx + '/archive/attachment/uploadAttachment',
            auto: false,
            bindAction: '#upload',
            before: function (obj) {
                this.data = form.val('attachment-form');
            },
            done: function (result) {
                layer.load(2);
                LayerUtil.respMsg(result, '上传成功', '上传失败', function () {
                    top.layer.closeAll();
                    reloadParentTable();
                })
            },
            error: function () {
                layer.error("上传失败！")
            }
        });

    });
</script>
</body>
</html>
