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
            <label class="layui-form-label"></label>
            <div class="layui-input-block layui-word-aux">
                文件大小最大限制：${maxFileSize!'1MB'}
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
    layui.use(['form', 'upload', 'element'], function () {
        let form = layui.form;
        let layer = layui.layer;
        let upload = layui.upload;
        let element = layui.element;
        let maxFileSize = "${maxFileSize!'1MB'}";
        maxFileSize = maxFileSize.substring(0, maxFileSize.length - 2);
        upload.render({
            elem: '#attachment',
            accept: 'file',
            url: ctx + '/archive/attachment/uploadAttachment',
            auto: false,
            bindAction: '#upload',
            size: maxFileSize * 1024,// KB
            before: function () {
                this.data = form.val('attachment-form');
                layer.open({
                    type: 1,
                    title: '上传进度',
                    area: ['80%', '30%'],
                    content:    '<div style="width: 90%; text-align: center" class="vh-center">' +
                                '   <div class="layui-progress layui-progress-big" lay-showpercent="yes" lay-filter="upload">' +
                                '       <div class="layui-progress-bar layui-bg-green" lay-percent="0%"></div>' +
                                '   </div>' +
                                '   <div><span class="upload-loaded"></span> / <span class="upload-total"></span></div>' +
                                '</div>'
                });
            },
            progress: function (n, elem, res) {
                let percent = n + '%'; //获取进度百分比
                element.progress('upload', percent); //可配合 layui 进度条元素使用
                $(".upload-loaded").text(CommonUtil.convertByte(res.loaded));
                $(".upload-total").text(CommonUtil.convertByte(res.total));
                // 重新渲染
                element.render(null, 'upload');
            },
            done: function (result) {
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
