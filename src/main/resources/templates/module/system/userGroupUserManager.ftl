<div id="searchDiv">

</div>
<div class="layui-col-xs12">
    <script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-addition"></i>添加</button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</button>
    </div>
    </script>
    <table class="layui-hide" id="user-list-tab" lay-filter="user">
    </table>
</div>
<script>
layui.use(['form', 'table', 'dtree'], function () {
    let form = layui.form;
    let table = layui.table;
    let dtree = layui.dtree;

    let tableId = 'user-list-tab';
    let layFilter = 'user';

    $("#searchDiv").laySearch({
        layFilter: 'user-search',
        data: [
            {key: "username", label: "用户名", type: "text"},
            {key: "deptName", label: "部门", type: "text"}
        ],
        doSearch: function () {
            userTableReload();
        }
    });

    table.render({
        elem: '#' + tableId,
        url: ctx + '/user/getUserGroupUserList?userGroupId=' + userGroupId,
        orderName: 'username',
        height: 'full-160',
        page: true,
        toolbar: '#toolbar',
        defaultToolbar: [],
        cols: [[
            {type: 'checkbox', excel: false},
            {field: 'username', title: '用户名', sort: true},
            {field: 'organizationName', title: '所属部门'}
        ]]
    });
    sortEventListen(table, layFilter, tableId);

    // 头工具栏监听事件
    table.on('toolbar(' + layFilter + ')', function (obj) {
        let event = obj.event;
        if (event === 'add') {
            CommonUtil.chooseUserTree({
                checkbar: true,
                notExistsUserGroupId: userGroupId,
                success: function (param) {
                    let nodeIds = CommonUtil.getAttrFromArray(param, "nodeId");
                    let loadIndex = layer.load(2);
                    CommonUtil.postAjax(ctx + '/system/user-user-group/batchInsert', {
                        userGroupId: userGroupId,
                        userIds: nodeIds
                    }, function (result) {
                        layer.close(loadIndex);
                        LayerUtil.respMsg(result, Msg.OPERATE_SUCCESS, Msg.OPERATE_FAILURE, function () {
                            userTableReload();
                        })
                    })
                }
            });
        } else if (event === 'delete') {
            let checked = table.checkStatus(tableId).data;
            if (checked.length === 0) {
                top.layer.warning(Msg.AT_LEAST_CHOOSE_ONE);
                return;
            }

            let userIds = CommonUtil.getAttrFromArray(checked, "userId");
            let names = CommonUtil.getAttrFromArray(checked, "username");
            layer.confirm("是否要从<span class='text-danger'>" + userGroupName + "</span>中删除用户：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后将不可恢复！", {
                title: "警告",
                skin: "my-layer-danger"
            }, function (index) {
                let loadIndex = layer.load(2);
                CommonUtil.postAjax(ctx + '/system/user-user-group/batchDelete', {
                    userGroupId: userGroupId,
                    userIds: userIds
                }, function (result) {
                    layer.close(loadIndex);
                    layer.close(index);
                    LayerUtil.respMsg(result, Msg.DELETE_SUCCESS, Msg.DELETE_FAILURE, function () {
                        userTableReload();
                    })
                })
            });
        }
    });

    function userTableReload() {
        layuiTableReload(table, tableId, form.val('user-search'));
    }
})
</script>