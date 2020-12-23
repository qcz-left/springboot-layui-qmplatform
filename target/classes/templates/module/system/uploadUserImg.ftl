<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);">
        <div class="layui-form-item">
            <div class="layui-input-inline layui-upload-drag" id="userImg">
                <i class="layui-icon"></i>
                <p>点击上传，或将图片拖拽到此处</p>
            </div>
            <div class="layui-input-inline layui-hide" id="userImgView">
                <img src="" style="max-width: 154px; max-height: 154px;">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="detail-operator">
                <button id="btnUpload" class="layui-btn layui-btn-normal">上传</button>
                <button class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['layer', 'upload'], function () {
        let layer = layui.layer;
        let upload = layui.upload;

        upload.render({
            elem: '#userImg',
            url: ctx + '/user/uploadUserImg',
            auto: false,
            bindAction: '#btnUpload',
            choose: function (obj) {
                //将每次选择的文件追加到文件队列
                obj.pushFile();
                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function (index, file, result) {
                    $('#userImgView').removeClass("layui-hide").find("img").attr('src', result);
                });
            },
            done: function (result) {
                layer.load(2);
                LayerUtil.respMsg(result, '上传成功', '上传失败', function (data) {
                    top.layer.closeAll();
                    // 替换右上角的头像
                    top.$("#userImg").attr("src", ctx + data.filePath);
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
