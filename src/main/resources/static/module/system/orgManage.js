layui.use(['dtree', 'table', 'form', 'element', 'util', 'xmSelect'], function () {
    let dtree = layui.dtree;
    let util = layui.util;
    let xmSelect = layui.xmSelect;

    // 自定义固定条
    util.fixbar({
        bars: [{
            type: 'setting',
            icon: 'layui-icon-set'
        }],
        css: {
            top: 70
        },
        click: function (type) {
            if (type === 'setting') {
                layer.open({
                    type: 1,
                    title: '组织架构通用设置',
                    offset: 'r',
                    anim: 'slideLeft', // 从右往左
                    area: ['380px', '100%'],
                    shade: 0.1,
                    shadeClose: true,
                    id: 'org-setting',
                    content: $("#orgCommonSetting")
                });
                let deptSelectValue;
                CommonUtil.postSync(ctx + '/organization/getOrgCommonConfig', {}, function (result) {
                    let data = result.data;
                    deptSelectValue = data.unknownDept || '';
                });
                let deptSelectData;
                CommonUtil.getSync(ctx + '/organization/getOrgTree', {}, function (result) {
                    deptSelectData = result.data;
                });
                // 侧边设置，部门数据
                SelectUtil.render(xmSelect, {
                    el: '#commonSettingDept',
                    name: 'commonSettingDept',
                    radio: true,
                    tree: true,
                    model: {label: {type: 'text'}},
                    data: deptSelectData,
                    initValue: [deptSelectValue],
                    on: function (data) {
                        if (data.arr.length === 0) {
                            return;
                        }
                        let value = data.arr[0].id;
                        CommonUtil.postAjax(ctx + '/organization/updateUnknownDept/' + value, {}, function (result) {
                            LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE);
                        });
                    }
                });
            }
        }
    });

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
