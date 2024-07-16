<!DOCTYPE html>
<html lang="en">
<#include "./include/include.ftl">
<title>${systemTitle}</title>
<style>
    #main-tab-menu table {
        margin: unset;
        background: #e5e5e5;
    }

    .hide {
        display: none !important;
    }
</style>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">${systemTitle}</div>
        <ul class="layui-nav layui-layout-right" lay-filter="personal">
            <img id="userImg" src="${ctx}${currentUser.photoPath!'/static/common/image/charon.png'}" class="layui-nav-img" layadmin-event>
            <li class="layui-nav-item">
                <a href="javascript:void(0);">
                    <span id="currentUsername">${currentUser.username}</span>
                </a>
                <dl class="layui-nav-child">
                    <dd>
                        <a href="javascript:void(0);" lay-id="personal-basic-info" lay-href="${ctx}/user/personalBasicInfoPage">基本资料</a>
                    </dd>
                    <dd>
                        <a href="javascript:void(0);" lay-id="safe-setting" lay-href="${ctx}/user/safeSettingPage">安全设置</a>
                    </dd>
                    <dd>
                        <ul class="layui-menu" id="skinMenu" style="margin: 0;">
                            <li class="layui-menu-item-parent">
                                <div class="layui-menu-body-title">
                                    一键换肤
                                    <i class="layui-icon layui-icon-right"></i>
                                </div>
                                <div class="layui-panel layui-menu-body-panel">
                                    <ul>
                                        <li lay-options="{type: 'black'}">
                                            <div class="layui-menu-body-title">商务黑</div>
                                        </li>
                                        <li lay-options="{type: 'blue'}">
                                            <div class="layui-menu-body-title">科技蓝</div>
                                        </li>
                                        <li lay-options="{type: 'red'}">
                                            <div class="layui-menu-body-title">烈焰红</div>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:void(0);" class="layui-icon layui-icon-notice" lay-id="message" lay-href="${ctx}/system/message/messageListPage" lay-title="系统通知">
                    <span id="messageCount" style="left: 30px;<#if messageCount.all == 0> display: none;</#if>" class="layui-badge">${messageCount.all}</span>
                </a>
            </li>
            <li class="layui-nav-item"><a href="${ctx}/logout">退出</a></li>
            <li class="layui-nav-item">
                <div id="choose-color"></div>
            </li>
        </ul>
    </div>
    <div class="layui-side">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="menu-tree">
                <#include '/include/menuInclude.ftl'>
            </ul>
        </div>
    </div>
    <!-- 页面标签 -->
    <div class="layui-body">
        <div class="layui-tab layui-tab-brief" lay-filter="main-tab" lay-stope="tabmore" lay-allowclose="true">
            <div id="main-tab-header">
                <div class="collapse-span layui-inline">
                    <div class="vh-center">
                        <i id="collapseIcon" title="折叠菜单" class="layui-icon layui-icon-shrink-right"></i>
                        <i id="expandIcon" title="展开菜单" class="layui-icon layui-icon-spread-left" style="display: none"></i>
                    </div>
                </div>
                <span class="currentLocationContent">
                    <span>当前位置：</span>
                    <span id="currentLocation" class="layui-font-gray"></span>
                </span>
                <div class="refresh-btn-content layui-inline">
                    <i class="layui-icon layui-icon-refresh-3" title="刷新当前菜单页面" onclick="reloadFrame()" style="font-size: 30px;"></i>
                </div>
            </div>
            <ul id="main-tab-title" class="layui-tab-title layui-inline">
            </ul>
            <div class="layui-tab-content">
            </div>
        </div>
        <div id="main-tab-menu" style="display: none;">
            <table class="layui-table">
                <tr>
                    <td class="context-menu-item layui-btn layui-btn-primary" id="closeCurrent">关闭当前</td>
                </tr>
                <tr>
                    <td class="context-menu-item layui-btn layui-btn-primary" id="closeOther">关闭其它</td>
                </tr>
                <tr>
                    <td class="context-menu-item layui-btn layui-btn-primary" id="closeLeft">关闭左侧</td>
                </tr>
                <tr>
                    <td class="context-menu-item layui-btn layui-btn-primary" id="closeRight">关闭右侧</td>
                </tr>
                <tr>
                    <td class="context-menu-item layui-btn layui-btn-primary" id="closeAll">关闭所有</td>
                </tr>
            </table>
        </div>
    </div>

</div>
<link rel="stylesheet" href="${ctx}/static/css/index.css" />
<script>
let menuTree = JSON.parse('${Json.toJsonStr(menuTree)}');
top.rsaPublicKey = "${rsaPublicKey!}";
layui.use(['element', 'dropdown'], function () {
    let element = layui.element;
    let dropdown = layui.dropdown;

    let tabLayFilter = 'main-tab';
    let classLayuiThis = 'layui-this';
    // 监听菜单导航点击
    element.on('nav(menu-tree)', function (elem) {
        let layHref = elem.attr("lay-href");
        if ($("#expandIcon").is(":visible") && !layHref) {
            $("#expandIcon").click();
        }
        addTab(elem);
    });
    // 监听右上角个人导航点击
    element.on('nav(personal)', function (elem) {
        addTab(elem);
    });

    dropdown.on('click(skinMenu)', function (options) {
        let type = options.type;
        localStorage.setItem("skinType", type);
        CommonUtil.changeSkin($, type);
    });

    let boxDOM = document.querySelector('[lay-filter="main-tab"]');// 外层容器DOM
    let contentDOM = document.querySelector('#main-tab-title'); // 内容容器DOM
    let tabTitleScroll = {
        offsetX: 0,// 偏移距离
        setPosition: function () {
            // 处理边界情况
            if (this.offsetX < 0) {
                this.offsetX = 0;
            }
            let maxX = contentDOM.offsetWidth - boxDOM.offsetWidth;
            if (this.offsetX > maxX) {
                this.offsetX = maxX;
            }
            // 设置偏移距离
            contentDOM.style.transform = 'translateX(' + (-this.offsetX) + 'px)'; // css3模拟滚动
        },
        /**
         * 增加偏移量
         * @param offsetX 偏移量
         */
        increaseOffsetX: function (offsetX) {
            this.offsetX += offsetX;
            tabTitleScroll.setPosition();
        }
    }
    // 监听wheel事件
    boxDOM.addEventListener('wheel', function (e) {
        e.preventDefault();
        e.stopPropagation();
        let delta = e.deltaX || e.deltaY || e.wheelDelta; // 滚动距离
        tabTitleScroll.offsetX = tabTitleScroll.offsetX + delta; // 偏移距离
        tabTitleScroll.setPosition();
    })

    // tab标题容器的边距+边框
    let $mainTabTitle = $('#main-tab-title');
    let tabTitlePaddingWidth = ($mainTabTitle.outerWidth(true) - $mainTabTitle.width()) / 2;
    // 监听选项卡切换
    element.on('tab(' + tabLayFilter + ')', function (data) {
        let layId = $(this).attr("lay-id");
        let currentLocation = getFullMenuName(layId, " / ");
        $("#currentLocation").text(currentLocation || $(this).text());
        $(".layui-side ." + classLayuiThis).removeClass(classLayuiThis);
        $(".layui-side a[lay-id=" + layId + "]").parent().addClass(classLayuiThis);

        // 整个选项卡最右侧相对偏移量
        let tabBoxOffsetLeft = data.elem.offset().left + data.elem.outerWidth(true);
        // 当前tab最右侧相对偏移量
        let tabOffsetLeft = $(this).offset().left + $(this).outerWidth(true);
        if (tabOffsetLeft > tabBoxOffsetLeft) {
            tabTitleScroll.increaseOffsetX(tabOffsetLeft - tabBoxOffsetLeft + tabTitlePaddingWidth);
        }

    });
    // 监听选项卡删除
    element.on('tabDelete(' + tabLayFilter + ')', function () {
        let layId = $(this).parent().attr("lay-id");
        $(".layui-side a[lay-id=" + layId + "]").parent().removeClass(classLayuiThis);
    });

    // 默认选中侧边栏第一个菜单，并自动展开父菜单
    $(".layui-side a[lay-id]:first").click().parents("li.layui-nav-item").addClass("layui-nav-itemed");

    // 上传头像
    $("#userImg").click(function () {
        top.layer.open({
            type: 2,
            title: '上传头像',
            content: ctx + "/user/uploadUserImgPage",
            area: ['40%', '40%']
        });
    });

    let collapseWidth = 200;
    let $layuiSide = $(".layui-side");
    let $layuiBody = $(".layui-body");
    // 收缩菜单
    $("#collapseIcon").click(function () {
        $layuiSide.width($layuiSide.width() - collapseWidth);
        $layuiSide.find(".layui-nav-item").width($layuiSide.width() - collapseWidth);
        $layuiBody.offset({
            left: $layuiBody.offset().left - collapseWidth
        });
        $layuiSide.find(".layui-nav-child").addClass("hide");

        $layuiSide.find(".layui-nav-tree > li").each(function () {
            LayerUtil.tips({
                content: $(this).find(".menu-name").html(),
                follow: $(this)
            });
        });
        $("#collapseIcon,.layui-side .layui-nav-more,.menu-name").hide();
        $("#expandIcon").show();
    });

    // 展开菜单
    $("#expandIcon").click(function () {
        $("#collapseIcon,.layui-side .layui-nav-more,.menu-name").show();
        $layuiSide.find(".layui-nav-item").width($layuiSide.width() + collapseWidth);
        $layuiSide.width($layuiSide.width() + collapseWidth);
        $layuiBody.offset({
            left: $layuiBody.offset().left + collapseWidth
        });
        $layuiSide.find(".layui-nav-child").removeClass("hide");

        $layuiSide.find(".layui-nav-tree > li").each(function () {
            $(this).mouseout().off('mouseenter mouseleave')
        });

        $("#expandIcon").hide();
    });

    tabRightClickEvent();

    /**
     * 右键菜单事件
     */
    function tabRightClickEvent() {
        let tabElement = document.getElementById("main-tab-title");
        let tabRightClickMenu = document.getElementById("main-tab-menu");
        //去掉默认的contextmenu事件，否则会和右键事件同时出现。
        tabElement.oncontextmenu = function (e) {
            e.preventDefault();
        };
        tabRightClickMenu.oncontextmenu = function (e) {
            e.preventDefault();
        };
        tabElement.onmousedown = function (e) {
            if (e.button === 2) {//右键
                let x = e.clientX;//获取鼠标单击点的X坐标
                let y = e.clientY;//获取鼠标单击点的Y坐标
                //设置菜单的位置
                tabRightClickMenu.style.position = "fixed";
                tabRightClickMenu.style.left = (x - 5) + "px";
                tabRightClickMenu.style.top = (y - 5) + "px";
                tabRightClickMenu.style.display = "block";
                $("#main-tab-menu-header").click();
            } else if (e.button === 0) { //左键
                tabRightClickMenu.style.display = "none";
            } else if (e.button === 1) { //按下滚轮
                tabRightClickMenu.style.display = "none";
            }
        }
        tabRightClickMenu.onmouseleave = function (e) {
            tabRightClickMenu.style.display = "none";
        }

        $("#closeCurrent").click(function () {
            let layId = getCurrentTabId();
            element.tabDelete(tabLayFilter, layId);
            tabRightClickMenu.style.display = "none";
        });

        $("#closeLeft").click(function () {
            let layId = getCurrentTabId();
            $("#main-tab-title li[lay-id='" + layId + "']").prevAll("li").each(function () {
                element.tabDelete(tabLayFilter, $(this).attr("lay-id"));
            });
            tabRightClickMenu.style.display = "none";
        });

        $("#closeRight").click(function () {
            let layId = getCurrentTabId();
            $("#main-tab-title li[lay-id='" + layId + "']").nextAll("li").each(function () {
                element.tabDelete(tabLayFilter, $(this).attr("lay-id"));
            });
            tabRightClickMenu.style.display = "none";
        });

        $("#closeOther").click(function () {
            let layId = getCurrentTabId();
            $("#main-tab-title li[lay-id!='" + layId + "']").each(function () {
                element.tabDelete(tabLayFilter, $(this).attr("lay-id"));
            });
            tabRightClickMenu.style.display = "none";
        });

        $("#closeAll").click(function () {
            $("#main-tab-title li").each(function () {
                element.tabDelete(tabLayFilter, $(this).attr("lay-id"));
            });
            tabRightClickMenu.style.display = "none";
        });
    }

    function getCurrentTabId() {
        return $(".layui-side ." + classLayuiThis + " a").attr("lay-id");
    }

    function addTab(elem) {
        let layId = elem.attr("lay-id");
        if (!layId) {
            return;
        }
        // 增加一个tab
        let layHref = elem.attr("lay-href");
        if ($(".layui-tab li[lay-id=" + layId + "]").length === 0) {
            element.tabAdd(tabLayFilter, {
                title: elem.attr("lay-title") || elem.text(),
                content: '<iframe src="' + layHref + '" frameborder="0" id="iframe-body-' + layId + '" style="width: 100%;height: 100%;"></iframe>',
                id: layId
            });
            // 最多展示多少个tab页
            let $tabLis = $("[lay-filter='main-tab'] ul > li");
            if ($tabLis.length > ${maxTabs!10}) {
                element.tabDelete(tabLayFilter, $tabLis.first().attr("lay-id"));
            }
        } else {
            document.getElementById("iframe-body-" + layId).contentWindow.location.reload();
        }
        element.tabChange(tabLayFilter, layId);
    }

    let socketUrl = CommonUtil.getWsProtocol() + "://" + window.location.host + ctx + "/socket/validateSession";
    let socket = new WebSocket(socketUrl);
    //打开事件
    socket.onopen = function () {

    };
    //获得消息事件
    socket.onmessage = function (msg) {
        let result = JSON.parse(msg.data);
        let code = result.code;
        if (code === ResponseCode.AUTHORIZED_EXPIRE || code === ResponseCode.DATA_BAK_RECOVER) {
            gotoLoginPage(code);
        }
    };
    //关闭事件
    socket.onclose = function () {

    };
    //发生了错误事件
    socket.onerror = function () {
        console.log("websocket发生了错误");
    };

    /*
     * 系统消息
     */
    let socketMessageUrl = CommonUtil.getWsProtocol() + "://" + window.location.host + ctx + "/socket/notify";
    let socketMessage = new WebSocket(socketMessageUrl);
    //获得消息事件
    socketMessage.onmessage = function (msg) {
        let result = JSON.parse(msg.data);
        let all = result.all;
        if (all > 0) {
            $("#messageCount").text(all).show();
        } else {
            $("#messageCount").hide();
        }
    };
});

/**
 * 获取完整的菜单名称
 * @param menuId 菜单ID
 * @param split 分隔符
 */
function getFullMenuName(menuId, split) {
    return getMenuNameRecursive("", menuTree, menuId, split);
}

/**
 * 递归的获取菜单名称
 */
function getMenuNameRecursive(pre, menuList, menuId, split) {
    pre = (pre ? pre + split : pre);
    for (let i = 0; i < menuList.length; i++) {
        let menu = menuList[i];
        if (menu.id === menuId) {
            return pre + menu.name;
        }
        if (menu.hasChild) {
            let menuName = getMenuNameRecursive(pre + menu.name, menu.childes, menuId, split);
            if (menuName) {
                return menuName;
            }
        }
    }
}
</script>
</body>
</html>
