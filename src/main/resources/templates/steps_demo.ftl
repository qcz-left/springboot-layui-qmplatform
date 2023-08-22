<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    .layui-form-item label {
        width: 250px;
    }

    .layui-form-item .layui-input-inline {
        width: 400px;
    }

    .layui-input-group .layui-input-prefix {
        width: 120px;
    }
</style>
<body class="detail-body">
<div class="layui-fluid" style="width: calc(100% - 20px); display: inline-flex">
    <div id="stepsDemo" style="height: 100%;">

    </div>
    <div class="steps-separator"></div>
    <div class="steps-form-content" for="#stepsDemo">
        <div class="first-step steps-form-item layui-anim layui-anim-scale">
            <#include "./steps_demo_baseinfo.ftl">
        </div>
        <div class="second-step steps-form-item hide layui-anim layui-anim-scale">
            <#include "./steps_demo_netVisiteObject.ftl">
        </div>
        <div class="third-step steps-form-item hide layui-anim layui-anim-scale">
            第3个表单
        </div>
        <div class="four-step steps-form-item hide layui-anim layui-anim-scale">
            第4个表单
        </div>
    </div>
    <div class="detail-operator">
        <button id="preStepBtn" type="button" class="layui-btn layui-btn-primary">上一步</button>
        <button id="nextStepBtn" type="button" class="layui-btn layui-btn-normal">下一步</button>
        <button id="cancelStepBtn" type="button" class="layui-btn layui-btn-primary">取消</button>
    </div>
</div>
<script type="text/javascript">
layui.use(['form'], function () {
    $("#stepsDemo").steps({
        items: [
            {
                label: "基本信息",
                bindForm: ".first-step",
                beforeSubmit: function () {

                }
            },
            {
                label: "网络访问对象",
                bindForm: ".second-step"
            },
            {
                label: "接入控制",
                bindForm: ".third-step"
            },
            {
                label: "接入控制点",
                bindForm: ".four-step"
            }
        ],
        bindPre: "#preStepBtn",
        bindNext: "#nextStepBtn"
    });
});
</script>
</body>
</html>