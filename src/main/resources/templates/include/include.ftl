<#assign ctx = "${requestContext.contextPath}">
<script type="text/javascript">
    let ctx = "${requestContext.contextPath}";
</script>
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
            <#elseif item == "xmSelect">
                <script type="text/javascript" src="${ctx}/static/plugin/layui/module/xm-select/xm-select.js"></script>
            <#elseif item == "treetable">
            <link rel="stylesheet" href="${ctx}/static/plugin/layui/module/treetable-lay/treetable.css"/>
                <script type="text/javascript" src="${ctx}/static/plugin/layui/module/treetable-lay/treetable.js"></script>
            <#elseif item == "laydate">
            <link rel="stylesheet" href="${ctx}/static/plugin/laydate/theme/default/laydate.css"/>
                <script type="text/javascript" src="${ctx}/static/plugin/laydate/laydate.js"></script>
            <#elseif item == "jqgrid">
            <link rel="stylesheet" href="${ctx}/static/plugin/jqgrid/css/ui.jqgrid.css"/>
                <script type="text/javascript" src="${ctx}/static/plugin/jqgrid/js/i18n/grid.locale-cn.js"></script>
                <script type="text/javascript" src="${ctx}/static/plugin/jqgrid/js/jquery.jqGrid.min.js?v=1.0"></script>
                <script type="text/javascript" src="${ctx}/static/plugin/jqgrid/js/grid.treegrid.js"></script>
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
