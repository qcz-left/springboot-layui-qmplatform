<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-item label {
        width: 240px;
    }

    .layui-form-item .layui-input-inline {
        width: 400px;
    }

    .layui-input-group .layui-input-prefix {
        width: 120px;
    }

    .jq-steps {
        height: 100%;
        width: calc(100% - 20px);
        display: inline-flex;
    }

</style>
<body class="detail-body">
<div class="layui-fluid">
    <div id="stepsDemo" class="jq-steps">
        <div class="steps-nav">

        </div>
        <div class="steps-form-content" for="#stepsDemo">
            <div class="step-1 steps-form-item">
                <#include "./steps_demo_baseinfo.ftl">
            </div>
            <div class="step-2 steps-form-item hide">
                <#include "./steps_demo_netVisiteObject.ftl">
            </div>
            <div class="step-3 steps-form-item hide">
                第3个表单
            </div>
            <div class="step-4 steps-form-item hide">
                第4个表单
            </div>
            <div class="step-5 steps-form-item hide">
                第5个表单
            </div>
            <div class="step-6 steps-form-item hide">
                第6个表单
            </div>
            <div class="step-7 steps-form-item hide">
                第7个表单
            </div>
        </div>
        <div class="detail-operator">
            <button id="preStepBtn" type="button" class="layui-btn layui-btn-primary" style="display: none;">上一步</button>
            <button id="nextStepBtn" type="button" class="layui-btn layui-btn-normal" style="display: none;">下一步</button>
            <button id="saveBtn" type="button" class="layui-btn layui-btn-normal" style="display: none;">保存</button>
        </div>
    </div>
</div>
<script type="text/javascript">
layui.use(['form'], function () {
    $("#stepsDemo").steps({
        // direction: "vertical",
        items: [
            {
                label: "基本信息",
                bindForm: ".step-1",
                beforeSubmit: function () {

                }
            },
            {
                label: "网络访问对象",
                bindForm: ".step-2"
            },
            {
                label: "接入控制",
                bindForm: ".step-3"
            },
            {
                label: "接入控制点",
                bindForm: ".step-4"
            },
            {
                label: "接入控制点5",
                bindForm: ".step-5"
            },
            {
                label: "接入控制点6",
                bindForm: ".step-6"
            },
            {
                label: "接入控制点7",
                bindForm: ".step-7",
                beforeSubmit: function () {
                    console.log("最后一步了。。。")
                }
            }
        ],
        bindPre: "#preStepBtn",
        bindNext: "#nextStepBtn",
        bindSave: "#saveBtn"
    });
});
</script>
</body>
</html>