layui.use(['treetable', 'table', 'form', 'element', 'layer'], function () {
    let treetable = layui.treetable;
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let tableId = 'menu-list-tab';
    let layFilter = 'menu';
    window.tableReload = function () {
        treetable.render({
            elem: '#' + tableId,
            url: ctx + '/menu/getMenuList',
            height: 'full-30',
            treeColIndex: 1,	//树形图标显示在第几列
            treeSpid: '',		//最上级的父级id
            treeIdName: 'id',	//id字段的名称
            treePidName: 'parentId',	//父级节点字段
            treeDefaultClose: false,	//是否默认折叠
            treeLinkage: false,		//父级展开时是否自动展开所有子级
            toolbar: '#toolbar',
            cols: [[
                {
                    field: 'permissionType', width: '5%', templet: function (row) {
                        return row.permissionType === 1 ? '<span class="layui-badge layui-bg-blue">菜单</span>' : '<span class="layui-badge layui-bg-orange">按钮</span>';
                    }
                },
                {field: 'name', title: '菜单/按钮名称', width: '20%'},
                {field: 'code', title: '权限码', width: '10%'},
                {field: 'icon', title: '图标', width: '10%'},
                {
                    field: 'display', title: '是否显示', width: '10%', templet: function (row) {
                        return row.permissionType === 1 ? (row.display === 1 ? '是' : '否') : '';
                    }
                },
                {field: 'linkUrl', title: 'URL', width: '20%'},
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
            title: id ? "编辑菜单/按钮" : "添加菜单/按钮",
            content: ctx + "/menu/menuDetailPage?id=" + id,
            area: ['30%', '80%']
        });
    }

    function remove(ids, names) {
        top.layer.confirm("是否要删除权限：<span class='text-success'>" + CommonUtil.joinMultiByLen(names, 3) + "</span>，删除后该权限及其子权限将不可恢复！", {
            title: "警告",
            skin: "my-layer-danger"
        }, function (index) {
            CommonUtil.deleteAjax(ctx + "/menu/deleteMenu", {
                permissionIds: CommonUtil.joinMulti(ids)
            }, function (data) {
                LayerUtil.respMsg(data, null, null, function () {
                    tableReload();
                });
                top.layer.close(index);
            })
        });
    }
});
