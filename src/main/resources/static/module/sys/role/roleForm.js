/**
 * 角色表单
 */
layui.config({
	base : _ctx + 'static/plugin/dtree/',
})
var vmData = {
	data : {
		roleId : roleId
	},
	// 后台错误验证信息
	error : {
		
	},
	formStatus : true,// 表单状态，true表示可以提交，不允许提交时设置为false即可
};
var layer = null;
var form = null;
layui.use(['element', 'form', 'layer', 'dtree'], function() {
	var element = layui.element;
	form = layui.form;
	layer = layui.layer;
	var dtree = layui.dtree;
	// 表单提交
	form.on('submit(*)', function(data){
		if (!vmData.formStatus) return false;
		layer.load();
		var menuIds = [];
		var checked = dtree.getCheckbarNodesParam("menuTree");
		for ( var i in checked) {
			var item = checked[i];
			menuIds.push(item.nodeId);
		}
		Vue.set(vmData.data, "menuIds", menuIds.toString());
		commonUtils.postAjax(_ctx + "role/saveRole", vmData.data, function(data) {
			layer.closeAll('loading');
			if (data.isSuccess) {
				vmData.data.roleId = data.data;
				layer.success(data.msg);
			} else {
				layer.error(data.msg);
			}
		});
	});
	var menuTree = dtree.render({
		elem: "#menuTree",
		url: _ctx + "menu/menuTreeWithStatus?roleId="+roleId,
		response:{message:"msg",statusCode:0},
		dataStyle: 'layuiStyle',
//		checkbarType: 'no-all',// 半选
		dot: false,
		checkbar:true //开启复选框
	});
	dtree.on("node('menuTree')" ,function(param){
		
	});
})

var vm = new Vue({
	el : "#vue-container",
	data : vmData,
	methods : {
		// 加载基础数据
		loadBaseData : function() {
			if (action == "view" || action == "edit") {
				commonUtils.getAjax(_ctx + "role/data/" + roleId, function(data) {
					// 时间在后台逻辑设置
					delete data['createTime'];
					delete data['updateTime'];
					vmData.data = data;
				});
			}
		}
	},
	mounted : function() {
		
	}
});

vm.$on('loadBaseData', function() {
	this.loadBaseData();
});

vm.$emit('loadBaseData');