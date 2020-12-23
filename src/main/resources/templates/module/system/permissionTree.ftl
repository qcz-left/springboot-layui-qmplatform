<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid" style="overflow: auto">
    <form class="layui-form detail-form" action="javascript:void(0);">
        <div id="permissionTree"></div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="allot-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    let roleId = "${RequestParameters["roleId"]}";
    layui.use(['form', 'layer', 'dtree'], function () {
        let form = layui.form;
        let layer = layui.layer;
        let dtree = layui.dtree;

        let permissionIds = [];
        CommonUtil.getSync(ctx + '/role/getRolePermission/' + roleId, {}, function (result) {
            permissionIds = result.data;
        })

        let permissionData = [];
        CommonUtil.getSync(ctx + '/menu/getMenuTree', {}, function (result) {
            permissionData = result.data;
        })

        buildData(permissionData);

        function buildData(data) {
            // 重新构造数据结构
            for (let i = 0; i < data.length; i++) {
                let item = data[i];
                if (permissionIds.indexOf(item.id) === -1) {
                    item.checkArr = "0";
                } else {
                    item.checkArr = "1";
                }
                if (item.hasChild) {
                    buildData(item.childes);
                } else {
                    item.isLast = true;
                }
            }
        }

        let permissionTree = dtree.render({
            elem: '#permissionTree',
            data: permissionData,
            checkbar: true,
            checkbarType: "all",
            response: {
                title: "name",		//节点名称
                childName: "childes"	//子节点名称
            }
        });

        form.on('submit(allot-submit)', function () {
            let checked = permissionTree.getCheckbarNodesParam();
            layer.load(2);
            CommonUtil.postAjax(ctx + '/role/saveRolePermission', {
                roleId: roleId,
                permissionIds: CommonUtil.getAttrFromArray(checked, "nodeId")
            }, function (result) {
                top.layer.closeAll();
                LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                    reloadParentTable();
                })
            });
            return false;
        });

    });
</script>
</body>
</html>
