layui.use(['dtree'], function () {
    let dtree = layui.dtree;

    let userGroupData = [];
    CommonUtil.postSync(ctx + '/system/user-group/getUserGroupTree', {}, function (result) {
        userGroupData = result.data;
    });

    let userGroupTree = TreeUtil.render(dtree, {
        elem: "#userGroupTree",
        data: userGroupData,
        toolbar: true,
        toolbarWay: "fixed",
        toolbarShow: [],
        toolbarExt: [{
            toolbarId: "add", icon: "dtree-icon-roundadd", title: "添加节点", handler: function (node) {
                jumpToDetailPage('', '', node.nodeId, node.context);
            }
        }, {
            toolbarId: "delete", icon: "dtree-icon-delete1", title: "删除节点", handler: function (node) {
                top.layer.confirm("是否要删除用户组：<span class='text-success'>" + node.context + "</span>，删除后将不可恢复！", {
                    title: "警告",
                    skin: "my-layer-danger"
                }, function (index) {
                    CommonUtil.postAjax(ctx + "/system/user-group/delete", {
                        ids: node.nodeId
                    }, function (data) {
                        top.layer.close(index);
                        LayerUtil.respMsg(data, null, null, function () {
                            reloadFrame();
                        });
                    }, null, true, false)
                });
            }
        }],
        ficon: ["1"],
        done: function () {
            if (userGroupData.length > 0) {
                dtree.click(userGroupTree, userGroupData[0].id);
            }
        }
    });
    // 绑定节点的单击事件
    dtree.on("node('userGroupTree')", function (obj) {
        let param = obj.param;
        let nodeId = param.nodeId;
        let context = param.context;
        let parentNode = userGroupTree.getParentParam(nodeId);
        jumpToDetailPage(nodeId, context, parentNode.nodeId, parentNode.context);
    });

    $("#btnSearch").click(function () {
        let inputName = $("input[name=inputName]").val();
        userGroupTree.fuzzySearch(inputName);
    });

    CommonUtil.enterKeyEvent('[name="inputName"]', function () {
        $("#btnSearch").click();
    });

    window.reloadEvent = function () {
        userGroupTree.reload();
    }

    /**
     * 跳转到详情页面
     * @param nodeId 用户组ID
     * @param parentId 上级用户组ID
     * @param parentName 上级用户组名称
     */
    function jumpToDetailPage(nodeId, context, parentId, parentName) {
        parentId = parentId || '';
        parentName = parentName || '';
        $("#userGroupContent").attr("src", "{0}?nodeId={1}&context={2}&parentId={3}&parentName={4}".format(ctx + "/system/user-group/userGroupDetailPage", nodeId, context, parentId, parentName));
    }
});