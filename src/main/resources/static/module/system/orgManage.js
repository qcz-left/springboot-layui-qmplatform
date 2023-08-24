layui.use(['dtree', 'table', 'form', 'element'], function () {
    let dtree = layui.dtree;

    let orgData = [];
    CommonUtil.getSync(ctx + '/organization/getOrgUserTree', {}, function (result) {
        orgData = result.data;
    });

    function buildData(data) {
        // 重新构造数据结构
        for (let i = 0; i < data.length; i++) {
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
    // 树代码示例
    let orgTree = TreeUtil.render(dtree, {
        elem: "#orgTree",
        data: orgData,
        done: function () {
            dtree.click(orgTree, orgData[0].id);
        }
    });
    // 绑定节点的单击事件
    dtree.on("node('orgTree')", function (obj) {
        let param = obj.param;
        $("#orgContent").attr("src", "{0}?nodeId={1}&isDept={2}&context={3}".format(ctx + "/organization/orgDetailPage", param.nodeId, param.recordData.itype === 1, param.context));
    });

    $("#btnSearch").click(function () {
        let inputName = $("input[name=inputName]").val();
        orgTree.fuzzySearch(inputName);
    });

    CommonUtil.enterKeyEvent('[name="inputName"]', function () {
        $("#btnSearch").click();
    });

    window.reloadEvent = function () {
        orgTree.reload();
    }

});
