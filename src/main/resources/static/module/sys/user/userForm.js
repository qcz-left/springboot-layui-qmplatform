/**
 * 用户表单
 */
var vmData = {
	data : {
		userId : userId,
		loginName : '',
		userSex : 'M',
		locked : '1'
	},
	// 后台错误验证信息
	error : {
		loginName : ""
	},
	formStatus : true,// 表单状态，true表示可以提交，不允许提交时设置为false即可
	old : {
		loginName : ''
	},
	firstVisit : true
};
var tableId = "data-table";
var layer = null;
var form = null;
var table = null;
var element = null;
var vm = new Vue({
	el : "#vue-container",
	data : vmData,
	methods : {
		
	},
	created :  function() {
		if (action == "view" || action == "edit") {
			commonUtils.getAjax(_ctx + "user/data/" + userId, function(data) {
				// 时间在后台逻辑设置
				delete data['createTime'];
				delete data['updateTime'];
				vmData.old.loginName = data.loginName;
				vmData.data = data;
			});
		}
	},
	watch : {
		'data.loginName' : function(data, oldData) {
			if (vmData.firstVisit || data == vmData.old.loginName) {
				vmData.firstVisit = false;
				return;
			}
			commonUtils.getAjax(_ctx + "user/validateOnlyLoginName?loginName="+data, function(data) {
				if (data.isSuccess) {
					vmData.formStatus = true;
					vmData.error.loginName = "";
				} else {
					vmData.formStatus = false;
					vmData.error.loginName = data.msg;
				}
			});
		}
	},
	mounted : function() {
		layui.use(['element', 'form', 'layer', 'table'], function() {
			element = layui.element;
			form = layui.form;
			layer = layui.layer;
			table = layui.table;
			form.render();
			// 下拉框选择事件监听
			form.on('select(locked)', function(data) {
				vmData.data.locked = data.value;
			});
			form.on('select(userSex)', function(data) {
				vmData.data.userSex = data.value;
			});
			// 表单提交
			form.on('submit(*)', function(data){
				if (!vmData.formStatus) return false;
				layer.load();
				var roleIds = [];
				var checkedData = table.checkStatus(tableId).data;
				for ( var i in checkedData) {
					var item = checkedData[i];
					roleIds.push(item.roleId);
					Vue.set(vmData.data, "roleIds", roleIds.toString());
				}
				commonUtils.postAjax(_ctx + "user/saveUser", vmData.data, function(data) {
					layer.closeAll('loading');
					if (data.isSuccess) {
						vmData.data.userId = data.data;
						layer.success(data.msg);
					} else {
						layer.error(data.msg);
					}
				});
			});
			table.render({
				elem : '#'+tableId,
				url : _ctx + 'role/roleListWithStatus',
				where : {
					param : JSON.stringify({userId:userId})
				},
				cols : [ [ // 表头
					{ type : 'checkbox' },
					{ field : 'roleId', hide : true},
					{ field : 'roleName'} 
				] ],
				done: function(res, page, count){
					var data = res.data;
					for ( var i in data) {
						var item = data[i];
						if (item.checked == 1) {
							item["LAY_CHECKED"]='true';
							//下面三句是通过更改css来实现选中的效果
							var index= item['LAY_TABLE_INDEX'];
							$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
							$('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
						}
					}
			    }
			});
		})
	}
});