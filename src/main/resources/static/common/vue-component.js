/**
 * 定义vue组件
 */
// 本来想实现无限级菜单，还没想好，待完成...
/*var menuTree = Vue.extend({
	props : ['message'],
	template : '<ol><template v-for="menu in message">'+
					'<li class="layui-nav-item layui-nav-itemed" v-if="menu.hasChild">'+
						'<a :class="menu.icon" href="javascript:;" v-text="menu.name"></a>'+
						'<dl class="layui-nav-child">'+
							'<template v-for="menuChildren in menu.childrens">'+
								'<template v-if="menuChildren.hasChild">'+
									'<menu-tree :message="menu.childrens"></menu-tree>'+
								'</template>'+
								'<template v-else>'+
									'<dd><a :class="menuChildren.icon" :href="'+_ctx+'+menuChildren.url" v-text="menuChildren.name"></a></dd>'+
								'</template>'+
							'</template>'+
						'</dl>'+
					'</li>'+
					'<li class="layui-nav-item" v-else><a :href="'+_ctx+'+menu.url" v-text="menu.name"></a></li>'+
				'</template></ol>'
});

Vue.component('menu-tree', menuTree);*/