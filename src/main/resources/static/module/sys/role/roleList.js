/**
 * 角色管理
 */
layui.use([ 'layer', 'table' ], function() {
	var table = layui.table;
	var layer = layui.layer;
	var tableId = "data-table";
	table.render({
		elem : '#'+tableId,
		height : 'full-55',
		url : _ctx + 'role/roleList',
		where : {
			field : 'roleName',
			order : 'asc',
			param : getParam()
		},
		page : true, // 开启分页
		limit : 20,
		limits : [20, 50, 100],
		autoSort : false, // 关闭数据在前端排序，通过服务器返回的数据排序
		initSort: {
		    field: 'roleName',
		    type: 'asc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
		},
		cols : [ [ // 表头
		{
			type : 'numbers'
		}, {
			field : 'roleName',
			title : '角色名',
			sort : true
		}, {
			field : 'roleSign',
			title : '角色标记'
		}, {
			field : 'remark',
			title : '备注'
		}, {
			field : 'operator',
			title : '操作',
			templet : function(item) {
				var btnEdit = '<a href="javascript:;" class="layui-btn layui-btn-warm layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
				var btnDelete = '<a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>';
				return btnEdit + btnDelete;
			}
		} ] ]
	});
	sortEventListen(table, tableId);
	
	//监听工具条
    table.on('tool('+tableId+')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent == "edit") {
			openForm(layEvent, data.roleId, data.roleName);
		} else if (layEvent == "delete") {
			deleteItem(data.roleId, data.roleName);
		}
    });

    // 搜索
	$("#app-btn-search").click(function() {
		table.reload(tableId,{
			page : 1,
			where : {
				param : getParam()
			}
		});
	});
	
	$("#app-btn-add").click(function() {
		openForm("add");
	});
	
	function openForm(action, roleId, roleName) {
		roleId = roleId || "";
		roleName = roleName || "";
		var index = layer.open({
			type : 2,
			title : action == "add" ? "添加角色" : "编辑【"+roleName+"】",
			content : _ctx + "role/roleForm?action="+action+"&roleId="+roleId,
			area : ['90%','90%'],
			success : function(layero, index) {
				
			},
			end : function(layero, index) {
				table.reload(tableId);
			}
		});
	}
	
	function deleteItem(id, name) {
		layer.confirm("是否要删除角色：" + name + "，删除后将不可恢复！", {title:"警告", skin:"my-layer-danger"}, function() {
			commonUtils.deleteAjax(_ctx + "role/delete/" + id, {}, function(data) {
				if (data.isSuccess) {
					layer.success(data.msg);
					table.reload(tableId);
				} else {
					layer.error(data.msg);
				}
			})
		});
	}
});