<#import "/spring.ftl" as spring/>
<#assign ctx = "${requestContext.contextPath}">
<script type="text/javascript">
let ctx = "${requestContext.contextPath}";
</script>
<#include './priv_code.ftl'>
<head>
    <script type="text/javascript" src="${ctx}/static/plugin/jquery/jquery-2.2.2.min.js"></script>
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/css/layui.css" />
    <script type="text/javascript" src="${ctx}/static/plugin/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/layui-extends.js"></script>
    <#--dtree-->
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/module/dtree/font/dtreefont.css" />
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/module/dtree/dtree.css" />
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/module/step-lay/step.css">
    <#if params??>
        <#list params as item>
            <#if item == "echarts">
                <script type="text/javascript" src="${ctx}/static/plugin/echarts/echarts.min.js"></script>
            <#elseif item == "error">
            <link rel="stylesheet" href="${ctx}/static/error/error.css" />
            <#elseif item == "sparklines">
                <script type="text/javascript" src="${ctx}/static/plugin/sparklines/jquery.sparkline.min.js"></script>
            <#elseif item == "tinymce">
                <script type="text/javascript" src="${ctx}/static/plugin/tinymce/tinymce.min.js"></script>
            <#elseif item == "jqgrid">
            <#--jqgrid-->
            <link rel="stylesheet" href="${ctx}/static/plugin/jqgrid/css/css/ui-lightness/jquery-ui-1.8.16.custom.css" />
            <link rel="stylesheet" href="${ctx}/static/plugin/jqgrid/css/ui.jqgrid.css" />
                <script type="text/javascript" src="${ctx}/static/plugin/jqgrid/js/i18n/grid.locale-cn.js"></script>
                <script type="text/javascript" src="${ctx}/static/plugin/jqgrid/js/jquery.jqGrid.src.js"></script>
            </#if>
        </#list>
    </#if>
    <!-- 自定义公共js库 -->
    <link rel="stylesheet" href="${ctx}/static/common/css/common.css" />
    <link rel="stylesheet" href="${ctx}/static/common/font/iconfont.css" />
    <script type="text/javascript" src="${ctx}/static/common/constant.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/app.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/layer-util.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugin/jsencrypt/jsencrypt.min.js"></script>
</head>
