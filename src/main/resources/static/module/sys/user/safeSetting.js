/**
 * 安全设置
 */
var vmData = {
	data : {
		oldLoginPassword : "",
		loginPassword : "",
		againLoginPassword : ""
	},
	error : {
		againLoginPassword : ""
	}
};
var tableId = "data-table";
var vm = new Vue({
	el : "#vue-container",
	data : vmData,
	methods : {
		// 加载基础数据
		loadBaseData : function() {
			
		}
	},
	mounted : function() {
		layui.use(['element', 'form', 'layer'], function() {
			var element = layui.element;
			var form = layui.form;
			var layer = layui.layer;
			// 表单提交
			form.on('submit(*)', function(data){
				if (vmData.data.loginPassword != vmData.data.againLoginPassword) {
					vmData.error.againLoginPassword = "重复输入密码不一致，请重新输入！";
					return false;
				}
				vmData.error.againLoginPassword = "";
				layer.load();
				commonUtils.postAjax(_ctx + "user/safeSetting", vmData.data, function(data) {
					layer.closeAll('loading');
					if (data.isSuccess) {
						layer.success(data.msg);
						top.layer.loadingWithText("修改密码后需要重新登录以验证，3秒后转入登录页...");
						setTimeout('top.location.href = _ctx + "main/logout"', 3000);
					} else {
						layer.error(data.msg);
					}
				});
			});
		})
	}
});

vm.$on('loadBaseData', function() {
	this.loadBaseData();
});

vm.$emit('loadBaseData');