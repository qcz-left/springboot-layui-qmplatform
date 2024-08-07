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

</style>
<body class="detail-body">
<div class="layui-fluid">
    <div id="stepsDemo" class="jq-steps">
        <div class="steps-nav">

        </div>
        <div class="steps-form-content">
            <div class="step-1 steps-form-item">
                <#include "./steps_demo_baseinfo.ftl">
            </div>
            <div class="step-2 steps-form-item hide">
                <#include "./steps_demo_netVisiteObject.ftl">
            </div>
            <div class="step-3 steps-form-item hide">
                第3个表单
            </div>
            <div class="step-7 steps-form-item hide">
                第7个表单
            </div>
        </div>
        <div class="detail-operator">
            <button id="preStepBtn" type="button" class="layui-btn layui-btn-primary">上一步</button>
            <button id="nextStepBtn" type="button" class="layui-btn layui-btn-normal">下一步</button>
            <button id="saveBtn" type="button" class="layui-btn layui-btn-normal hide">保存</button>
        </div>
    </div>
</div>
<script type="text/javascript">
layui.use(['form'], function () {
    $("#stepsDemo").steps({
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
                label: "接入控制点7",
                bindForm: ".step-7",
                beforeSubmit: function () {
                    console.log("最后一步了。。。");
                    return true;
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