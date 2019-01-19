/**
 * Qu管理平台-首页 逻辑控制
 */
var vmData = {
	data : {
		iframeSrc : '',
		tabHtml : ''
	},
	list : {
		tabMenu : []
	}
};
var element = null;
var firstVisitedMenu_url = null;
var vm = new Vue({
	el : "#vue-container",
	data : vmData,
	methods : {
		visitMenu : function(menu) {
			// 选项卡样式切换
			$("#lay_app_tabsheader li.layui-this").removeClass("layui-this");
			var url = menu.url;
			if (!checkUrl(url)) {
				vmData.list.tabMenu.push(menu);
				$("#lay_app_tabsheader").append('<li lay-id="'+url+'">'+(menu.name=='主页'?'<i class="'+menu.icon+'"></i>':menu.name)+'</li>');
			}
			$("#lay_app_tabsheader li[lay-id='"+url+"']").addClass("layui-this");
			
			// 左侧导航样式切换
			$(".layui-nav-tree .layui-this").removeClass("layui-this");
			$(".layui-nav-tree a[lay-href='"+url+"']").parent().addClass("layui-this");
			vmData.data.iframeSrc = url;
		}
	},
	created : function() {
		// 加载基础数据
		commonUtils.getAjax(_ctx + "menu/menuTreeData", function(data) {
			var menuTree = data.data;
			Vue.set(vmData.data, 'menuTree', menuTree);
			var firstVisitedMenu = getFirstVisitedMenu(menuTree);
			firstVisitedMenu_url = firstVisitedMenu.url;
			var firstVisitedMenu_name = firstVisitedMenu.name;
			$("#lay_app_tabsheader").append('<li lay-id="'+firstVisitedMenu_url+'" class="layui-this">'+(firstVisitedMenu_name=='主页'?'<i class="'+firstVisitedMenu.icon+'"></i>':firstVisitedMenu_name)+'</li>');
			vmData.list.tabMenu.push(firstVisitedMenu);
			vmData.data.iframeSrc = firstVisitedMenu_url;
		});
	},
	updated : function() {
		element.render();
	},
	mounted : function() {
		//这段代码直接写在最外层，会造成layui和vue冲突，样式不起作用
		layui.use(["element", "colorpicker"], function() {
			element = layui.element;
			var colorpicker = layui.colorpicker;
			// 选择主题
			colorpicker.render({
				elem : '#choose-color',
				color : '#0869a6', // 设置默认色
				done : function(color) {
					$("#index-left").removeClass("layui-bg-black").css("background-color", color);
					$("#index-left .layui-nav").css("background-color", color);
					$(".layui-layout-admin .layui-header").css("background-color", color);
					$(".layui-header .layui-logo").css("color", "white");
					$(".layui-header li>a").css("color", "white");
				}
			});
			// 选项卡删除监听
			element.on('tabDelete(layadmin-layout-tabs)', function(data){
				navStyleShowback();
			});
			// 选项卡切换监听事件
			element.on('tab(layadmin-layout-tabs)', function(data){
				vmData.data.iframeSrc = $(this).attr("lay-id");
				navStyleShowback();
			});
			
			// 右侧选项卡控制导航
			$(".layadmin-tabs-control ul li dl dd a").click(function() {
				var ddEvent = $(this).parent("dd").attr("layadmin-event");
				if (ddEvent == "closeThisTabs") {
					element.tabDelete('layadmin-layout-tabs', $("#lay_app_tabsheader li[class='layui-this'][lay-id!='"+firstVisitedMenu_url+"']").attr("lay-id"));
				} else if (ddEvent == "closeOtherTabs") {
					$("#lay_app_tabsheader li[class!='layui-this'][lay-id!='"+firstVisitedMenu_url+"']").each(function() {
						element.tabDelete('layadmin-layout-tabs', $(this).attr("lay-id"));
					});
				} else if (ddEvent == "closeAllTabs") {
					$("#lay_app_tabsheader li[lay-id!='"+firstVisitedMenu_url+"']").each(function() {
						element.tabDelete('layadmin-layout-tabs', $(this).attr("lay-id"));
					});
				} 
				$(".layadmin-tabs-control ul li dl").removeClass("layui-show");
			});
			
			// 安全设置
			$(".safe-setting").click(function() {
				layer.open({
					type : 2,
					title : "安全设置",
					content : _ctx + "user/safeSettingPage",
					area : [ "50%", "80%" ],
					skin : 'layui-layer-molv',
					shade : 0,
					maxmin : true,
					resize : true,
					moveOut : true,
					zIndex : layer.zIndex, //多窗口模式，层叠打开
					end : function() {
						
					}
				});
			});
		});
	}
});

/*
 * 获取第一个被访问到的菜单
 */
function getFirstVisitedMenu(menuTree) {
	for ( var i in menuTree) {
		var menu = menuTree[i];
		if (!menu.hasChild) {
			return menu;
		} else {
			return getFirstVisitedMenu(menu.childrens);
		}
	}
}

/*
 * 检查选项卡中是否有这一url了
 */
function checkUrl(url) {
	var flag = false;
	$("#lay_app_tabsheader li").each(function() {
		if (url == $(this).attr("lay-id")) {
			flag = true;
			return false;// 此处意思是跳出循环
		}
	});
	return flag;
}

/*
 * 导航样式回显
 */
function navStyleShowback() {
	$(".layui-nav-tree .layui-this").removeClass("layui-this");
	$(".layui-nav-tree a[lay-href='"+$("#lay_app_tabsheader li.layui-this").attr("lay-id")+"']").parent().addClass("layui-this");
}