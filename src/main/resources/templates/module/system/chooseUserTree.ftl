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
        <div class="layui-input-group">
            <input type="text" name="username" autocomplete="off" class="layui-input" placeholder="输入用户名查询">
            <div id="btnSearch" class="layui-input-split layui-input-suffix" style="cursor: pointer;">
                <i class="layui-icon layui-icon-search"></i>
            </div>
        </div>
    </div>
</form>
<div id="orgTree"></div>
<script>
let checkbar = ${RequestParameters["checkbar"]};
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

    let userTreeOption = {
        elem: "#orgTree",
        data: orgData
    };
    if (checkbar) {
        userTreeOption.checkbar = checkbar;
        userTreeOption.checkbarType = 'self';
    }
    let orgTree = TreeUtil.render(dtree, userTreeOption);

    $("#btnSearch").click(function () {
        searchData();
    });

    CommonUtil.enterKeyEvent('[name="username"]', function () {
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