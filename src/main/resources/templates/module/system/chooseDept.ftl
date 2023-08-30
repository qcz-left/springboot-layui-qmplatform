<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    #deptTree {
        width: 100% !important;
        height: calc(100% - 60px);
        overflow-y: auto;
    }
</style>
<body class="layui-fluid">
<form class="layui-form" action="javascript:void(0);">
    <div class="layui-form-item">
        <div class="layui-input-group">
            <input type="text" name="deptName" autocomplete="off" class="layui-input" placeholder="输入部门名称查询">
            <div id="btnSearch" class="layui-input-split layui-input-suffix" style="cursor: pointer;">
                <i class="layui-icon layui-icon-search"></i>
            </div>
        </div>
    </div>
</form>
<div id="deptTree"></div>
<script>
let checkbar = ${RequestParameters["checkbar"]};
layui.use(['dtree'], function () {
    let dtree = layui.dtree;

    let deptData = [];
    CommonUtil.getSync(ctx + '/organization/getOrgTree', {}, function (result) {
        deptData = result.data;
    });

    function buildData(data) {
        // 重新构造数据结构
        for (let i = 0; i < data.length; i++) {
            let item = data[i];
            item.checkArr = "0";
            item.iconClass = "dtree-icon-fenzhijigou";
            if (item.hasChild) {
                buildData(item.childes);
            }
        }
    }

    buildData(deptData);

    let deptTreeOption = {
        elem: "#deptTree",
        data: deptData
    };
    if (checkbar) {
        deptTreeOption.checkbar = checkbar;
        deptTreeOption.checkbarType = 'self';
    }
    let deptTree = TreeUtil.render(dtree, deptTreeOption);

    $("#btnSearch").click(function () {
        searchData();
    });

    CommonUtil.enterKeyEvent('[name="deptName"]', function () {
        searchData();
    });

    let $deptName = $("input[name=deptName]");

    function searchData() {
        let deptName = $deptName.val();
        deptTree.fuzzySearch(deptName);
    }

    window.getCurrentNode = function () {
        if (checkbar) {
            return deptTree.getCheckbarNodesParam();
        }
        return deptTree.getNowParam();
    }

});

</script>
</body>
</html>