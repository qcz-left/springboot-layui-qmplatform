<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div>
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="import-form">
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <a id="downloadTemplate" class="layui-btn" href="javascript:void(0);">下载导入模板</a>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <div class="layui-upload-drag" id="attachment">
                    <i class="layui-icon"></i>
                    <p>点击上传导入文件，或将文件拖拽到此处</p>
                </div>
            </div>
        </div>
        <div class="detail-operator">
            <button id="import" class="layui-btn layui-btn-normal">导入</button>
            <button class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['upload', 'table'], function () {
        let upload = layui.upload;
        let table = layui.table;

        let indexLoader;
        upload.render({
            elem: '#attachment',
            accept: 'file',
            url: ctx + '${RequestParameters["act"]}',
            auto: false,
            bindAction: '#import',
            before: function () {
                indexLoader = top.layer.load(2);
            },
            done: function (result) {
                top.layer.closeAll();
                getFrameWindow().reloadEvent();

                let importResult = result.data;
                let failCount = importResult.failCount;
                if (failCount === 0) {
                    top.layer.success("导入成功");
                } else {
                    // 有导入失败的记录
                    top.layer.open({
                        type: 2,
                        title: "导入结果",
                        content: ctx + '/importResultPage',
                        area: ['40%', '50%'],
                        success: function (layero, index) {
                            let $body = top.layer.getChildFrame('body', index);
                            $body.find('#total').text(importResult.total);
                            $body.find('#successCount').text(importResult.successCount);
                            $body.find('#failCount').text(failCount);
                            table.render({
                                elem: $body.find('#importResult-list-tab'),
                                data: importResult.importFailReasonList,
                                cols: [[
                                    {field: 'rowIndex', title: '行号', width: '20%'},
                                    {field: 'name', title: importResult.title, width: '40%'},
                                    {field: 'reason', title: '失败原因'}
                                ]]
                            });
                        }
                    });
                }
            },
            error: function () {
                top.layer.error("导入失败")
            }
        });

        $('#downloadTemplate').click(function () {
            parent.downloadTemplate();
        });
    });
</script>
</body>
</html>
