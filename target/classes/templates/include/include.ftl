<#assign ctx = "${requestContext.contextPath}">
<script type="text/javascript">
    let ctx = "${requestContext.contextPath}";
</script>
<#include './priv_code.ftl'>
<head>
    <script type="text/javascript" src="${ctx}/static/plugin/jquery/jquery-2.2.2.min.js"></script>
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/css/layui.css"/>
    <script type="text/javascript" src="${ctx}/static/plugin/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/layui-extends.js"></script>
    <#--dtree-->
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/module/dtree/font/iconfont.css"/>
    <link rel="stylesheet" href="${ctx}/static/plugin/layui/module/dtree/dtree.css"/>
    <#if params??>
        <#list params as item>
            <#if item == "echarts">
                <script type="text/javascript" src="${ctx}/static/plugin/echarts3.3.2/echarts.min.js"></script>
            <#elseif item == "error">
            <link rel="stylesheet" href="${ctx}/static/error/error.css"/>
            </#if>
        </#list>
    </#if>
    <!-- 自定义公共js库 -->
    <link rel="stylesheet" href="${ctx}/static/common/css/common.css"/>
    <script type="text/javascript" src="${ctx}/static/common/constant.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/app.js"></script>
    <script type="text/javascript" src="${ctx}/static/common/layer-util.js"></script>
</head>
