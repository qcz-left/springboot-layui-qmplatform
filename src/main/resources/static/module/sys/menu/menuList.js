/**
 * 菜单模块
 */
// 引入外部第三方插件
layui.config({
	base : _ctx + 'static/plugin/layui/module/',
})
layui.use(['layer', 'table', 'treetable'], function() {
	var table = layui.table;
    var layer = layui.layer;
    var treetable = layui.treetable;

    // 渲染表格
    var renderTable = function () {
        layer.load(2);
        treetable.render({
            treeColIndex: 1,
            treeSpid: 0,// 顶级菜单ID
            treeIdName: 'id',
            treePidName: 'pid',
            treeDefaultClose: false,
            treeLinkage: false,
            elem: '#menu-tree-table',
            url: _ctx + "menu/menuList",
            page: false,
            cols: [[
            	{ type: 'numbers'},
            	{ field : 'name', title : '菜单名称'},
				{ field : 'icon', title : '图标', templet: function(item) {
					return '<i class="'+(item.icon ? item.icon : 'layui-icon layui-icon-template-1')+'"></i>';
				}}, 
				{ field : 'url', title : '访问路径'}, 
				{ field : 'operator', title : '操作', templet: function(item) {
					var btnAdd = '<a href="javascript:;" class="layui-btn layui-btn-xs" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>添加子级</a>&nbsp;';
					var btnEdit = '<a href="javascript:;" class="layui-btn layui-btn-warm layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>'
					var btnDelete = '<a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>'
					return btnAdd + btnEdit + btnDelete;
				}}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });
    };

    renderTable();
    
    //监听工具条
    table.on('tool(menu-tree-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent == "add") {
        	openForm(layEvent, null, data.id, data.name);
		} else if (layEvent == "edit") {
			openForm(layEvent, data.id, data.pid, data.pname);
		} else if (layEvent == "delete") {
			deleteItem(data.id, data.name);
		}
    });
	
    $("#app-btn-add").click(function() {
		openForm("add");
	});
	function openForm(action, menuId, parentId, parentName) {
		menuId = menuId || "";
		parentId = parentId || "";
		parentName = parentName || "";
		var index = layer.open({
			type : 2,
			title : action == "add" ? "添加菜单" : "编辑菜单",
			content : _ctx + "menu/menuForm?menuId="+menuId+"&parentId="+parentId+"&action="+action+"&parentName="+parentName,
			area : ['90%','90%'],
			success : function(layero, index) {
				
			},
			end : function(layero, index) {
				renderTable();
			}
		});
	}
	
	function deleteItem(id, name) {
		layer.confirm("是否要删除菜单：" + name + "，删除后将不可恢复！", function() {
			commonUtils.deleteAjax(_ctx + "menu/delete/" + id, {}, function(data) {
				if (data.isSuccess) {
					layer.success(data.msg);
					renderTable();
				} else {
					layer.error(data.msg);
				}
			})
		});
	}

})