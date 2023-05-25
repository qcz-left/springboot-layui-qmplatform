<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<style>
    #orgTree {
        width: 100% !important;
        height: calc(100% - 60px);
        overflow-y: auto;
    }
</style>
<body class="layui-fluid">
<form class="layui-form" action="javascript:void(0);">
    <div class="layui-form-item">
        <input type="text" name="username" autocomplete="off" class="layui-input layui-input-inline" placeholder="输入用户名查询">
        <button id="btnSearch" class="layui-btn layui-btn-primary layui-inline"><i class="layui-icon layui-icon-search"></i></button>
    </div>
</form>
<div id="orgTree"></div>
<script>
layui.use(['dtree'], function () {
    let dtree = layui.dtree;

    let orgData = [];
    CommonUtil.getSync(ctx + '/organization/getOrgUserTree', {}, function (result) {
        orgData = result.data;
    });

    function buildData(data) {
        // 重新构造数据结构
        for (let i = data.length - 1; i >= 0; i--) {
            let item = data[i];
            if (item.itype === 1) {
                item.disabled = true;
                item['iconClass'] = "dtree-icon-fenzhijigou";
            } else {
                item['iconClass'] = "dtree-icon-yonghu";
            }
            if (item.hasChild) {
                buildData(item.childes);
            }
        }
    }

    buildData(orgData);

    let orgTree = dtree.render({
        elem: "#orgTree",
        data: orgData,
        dataStyle: "layuiStyle",
        skin: "laySimple",
        record: true,
        ficon: "8",
        spreadSelected: false,// 伸缩时是否选中状态
        response: {
            title: "name", //节点名称
            childName: "childes" //子节点名称
        }
    });

    $("#btnSearch").click(function () {
        searchData();
    });

    let $username = $("input[name=username]");
    function searchData() {
        let username = $username.val();
        orgTree.fuzzySearch(username);
    }

    window.getCurrentNode = function () {
        return dtree.getNowParam(orgTree);
    }

});

</script>
</body>
</html>