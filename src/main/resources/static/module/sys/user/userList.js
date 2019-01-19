/**
 * 用户管理
 */
layui.use([ 'layer', 'table', 'form' ], function() {
	var table = layui.table;
	var layer = layui.layer;
	var form = layui.form;
	var tableId = "data-table";
	table.render({
		elem : '#'+tableId,
		height : 'full-55',
		url : _ctx + 'user/userList',
		where : {
			field : 'userName',
			order : 'asc',
			param : getParam()
		},
		page : true, // 开启分页
		limit : _limit,
		limits : _limits,
		autoSort : false, // 关闭数据在前端排序，通过服务器返回的数据排序
		initSort: {
		    field: 'userName',
		    type: 'asc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
		},
		cols : [ [ // 表头
		{
			type : 'numbers'
		}, {
			field : 'userName',
			title : '用户名',
			sort : true
		}, {
			field : 'loginName',
			title : '登录名'
		}, {
			field : 'userSex',
			title : '性别',
			templet : function(item) {
				var userSex = item.userSex;
				switch (userSex) {
				case "M":
					userSex = "男";
					break;
				case "F":
					userSex = "女";
					break;
				default:
					userSex = "数据有误";
					break;
				}
				return userSex;
			}
		}, {
			field : 'locked',
			title : '状态',
			templet : function(item) {
				var locked = item.locked;
				var lockedHtml = '<input type="checkbox" data-id="'+item.userId+'" data-name="'+item.userName+'" lay-skin="switch" lay-filter="locked" lay-text="正常|锁定"';
				switch (locked) {
				case "0":
					lockedHtml += '>';
					break;
				case "1":
					lockedHtml += ' checked>';
					break;
				default:
					return "数据有误";
				}
				return lockedHtml;
			}
		}, {
			field : 'operator',
			title : '操作',
			templet : function(item) {
				var btnEdit = '<a href="javascript:;" class="layui-btn layui-btn-warm layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>'
				var btnDelete = '<a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>'
				var resetPwd = '<a href="javascript:;" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="reset"><i class="layui-icon layui-icon-refresh"></i>重置密码</a>'
				return btnEdit + btnDelete + resetPwd;
			}
		} ] ]
	});
	sortEventListen(table, tableId);
	
	//监听工具条
    table.on('tool('+tableId+')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent == "edit") {
			openForm(layEvent, data.userId, data.userName);
		} else if (layEvent == "delete") {
			deleteItem(data.userId, data.userName);
		} else if (layEvent == "reset") {
			layer.confirm("确定要重置【"+data.userName+"】的密码吗？", {skin:"my-layer-warm"}, function() {
				var param = {
						userId : data.userId
				};
				commonUtils.postAjax(_ctx + "user/resetLoginPassword", param, function(data) {
					if (data.isSuccess) {
						layer.success(data.msg);
					} else {
						layer.error(data.msg);
					}
				})
			});
		}
    });
    
    // 监听启用，锁定开关点击事件
    form.on('switch(locked)', function(data){
    	var elem = data.elem;
		var userId = elem.dataset["id"];
		var userName = elem.dataset["name"];
		var checked = elem.checked;
		layer.confirm("确定要"+(checked ? '开启 ' : '锁定 ')+userName+" 吗？", {
				skin : "my-layer-warm",
				end : function() {
					table.reload(tableId);
				}
		}, function() {
			var param = {
					userId : userId,
					locked : checked ? "1" : "0"
			};
			commonUtils.postAjax(_ctx + "user/changeLockedStatus", param, function(result) {
				if (result.isSuccess) {
					layer.success(result.msg);
				} else {
					layer.error(result.msg);
				}
			})
		});
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
	
	// 添加
	$("#app-btn-add").click(function() {
		openForm("add");
	});
	
	function openForm(action, userId, userName) {
		userId = userId || "";
		userName = userName || "";
		var index = layer.open({
			type : 2,
			title : action == "add" ? "添加用户" : "编辑【"+userName+"】",
			content : _ctx + "user/userForm?action="+action+"&userId="+userId,
			area : ['90%','90%'],
			success : function(layero, index) {
				
			},
			end : function(layero, index) {
				table.reload(tableId);
			}
		});
	}
	
	function deleteItem(id, name) {
		layer.confirm("是否要删除用户：" + name + "，删除后将不可恢复！", {title:"警告", skin:"my-layer-danger"}, function() {
			commonUtils.deleteAjax(_ctx + "user/delete/" + id, {}, function(data) {
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