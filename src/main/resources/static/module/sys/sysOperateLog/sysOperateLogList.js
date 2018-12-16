/**
 * 系统操作日志
 */
layui.use([ 'layer', 'table' ], function() {
	var table = layui.table;
	var layer = layui.layer;
	var tableId = "data-table";
	table.render({
		elem : '#' + tableId,
		height : 'full-75',
		url : _ctx + 'sysOperateLog/sysOperateLogList',
		where : {
			field : 'operateTime',
			order : 'desc',
			param : getParam()
		},
		page : true, // 开启分页
		limit : _limit,
		limits : _limits,
		autoSort : false, // 关闭数据在前端排序，通过服务器返回的数据排序
		initSort: {
		    field: 'operateTime',
		    type: 'desc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
		},
		cols : [ [ // 表头
		{
			type : 'numbers'
		}, {
			field : 'operateModule',
			title : '操作模块',
			sort : true
		}, {
			field : 'operateUserName',
			title : '操作人'
		}, {
			field : 'operateType',
			title : '操作类型',
			templet : function(item) {
				var operateType = item.operateType;
				switch (operateType) {
				case "-1":
					operateType = "退出系统";
					break;
				case "1":
					operateType = "登录系统";
					break;
				case "2":
					operateType = "查询";
					break;
				case "3":
					operateType = "新增";
					break;
				case "4":
					operateType = "修改";
					break;
				case "5":
					operateType = "删除";
					break;
				default:
					operateType = "无效数据";
					break;
				}
				return operateType;
			}
		}, {
			field : 'ip',
			title : '操作地址'
		}, {
			field : 'operateTime',
			title : '操作时间',
			sort : true,
			templet : function(item) {
				return new Date(item.operateTime).Format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			field : 'operateStatus',
			title : '操作状态',
			sort : true,
			templet : function(item) {
				return item.operateStatus ? "成功" : "失败";
			}
		}, {
			field : 'operator',
			title : '操作',
			templet : function(item) {
				var btnEdit = '<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs"><i class="layui-icon layui-icon-read"></i>查看详情</a>';
				return btnEdit;
			}
		} ] ]
	});
	sortEventListen(table, tableId);

	// 监听工具条
	table.on('tool(' + tableId + ')', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		if (layEvent == "edit") {
			
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
});