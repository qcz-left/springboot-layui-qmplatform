layui.use(['treetable', 'table', 'form', 'element', 'layer'], function () {
    let treetable = layui.treetable;
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let tableId = 'org-list-tab';
    let layFilter = 'org';
    window.tableReload = function () {
        treetable.render({
            elem: '#' + tableId,
            url: ctx + '/organization/getOrgList',
            height: 'full-30',
            treeColIndex: 0,	//树形图标显示在第几列
            treeSpid: '',		//最上级的父级id
            treeIdName: 'id',	//id字段的名称
            treePidName: 'parentId',	//父级节点字段
            treeDefaultClose: false,	//是否默认折叠
            treeLinkage: false,		//父级展开时是否自动展开所有子级
            page: false,
            toolbar: '#toolbar',
            cols: [[
                {field: 'name', title: '组织机构名称', width: '20%'},
                {field: 'code', title: '组织机构编码', width: '10%'},
                {fixed: 'right', title: '操作', align: 'center', templet: '#operator'}
            ]]
        });
    }
    tableReload();
    sortEventListen(table, layFilter, tableId);

    // 头工具栏监听事件
    table.on('toolbar(' + layFilter + ')', function (obj) {
        switch (obj.event) {
            case 'add':
                open();
                break;
        }
    });

    // 操作监听事件
    table.on('tool(' + layFilter + ')', function (obj) {
        let data = obj.data;
        switch (obj.event) {
            case 'edit':
                open(data.id)
                break;
            case 'delete':
                remove([data.id], [data.name]);
                break;
        }
    });

    function open(id) {
        id = id || '';
        top.layer.open({
            type: 2,
            title: id ? "编辑组织机构" : "添加组织机构",
            content: ctx + "/organization/organizationDetailPage?id=" + id,
            area: ['30%', '80%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除组织机构：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后该组织机构及其子组织机构将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/organization/deleteOrg", {
                orgIds: CommonUtil.joinMulti(ids)
            }, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                top.layer.close(index);
            })
        });
    }
});
