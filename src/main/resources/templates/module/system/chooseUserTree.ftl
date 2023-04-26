<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="layui-fluid">
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
        spreadSelected: false,// 伸缩时是否选中状态
        response: {
            title: "name", //节点名称
            childName: "childes" //子节点名称
        }
    });

    window.getCurrentNode = function () {
        return dtree.getNowParam(orgTree);
    }

});

</script>
</body>
</html>