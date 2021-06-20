layui.use(['dtree', 'table', 'form', 'element'], function () {
    let dtree = layui.dtree;
    let table = layui.table;
    let form = layui.form;
    let element = layui.element;

    let orgData = [];
    CommonUtil.getSync(ctx + '/organization/getOrgUserTree', {}, function (result) {
        orgData = result.data;
    })
    // 树代码示例
    let orgTree = dtree.render({
        elem: "#orgTree",
        data: orgData,
        method: "get",
        skin: "laySimple",
        iframeLoad: "all",
        useIframe: true,  //启用iframe
        iframeElem: "#orgContent",  // iframe的ID
        iframeUrl: ctx + "/organization/orgDetailPage", // iframe路由到的地址
        response: {
            title: "name",		//节点名称
            childName: "childes"	//子节点名称
        },
        done: function () {
            dtree.click(orgTree, orgData[0].id);
        }
    });
    /** 获取本页面url中的参数值 */
    let getUrlParam = function (name) {
        let url = location.search;
        url = url.substring(url.indexOf("?"));
        if (url.indexOf("?") != -1) {
            let str = url.substr(1);
            let strs = str.split("&");
            for (let i = 0; i < strs.length; i++) {
                if (name == strs[i].split("=")[0]) {
                    return unescape(strs[i].split("=")[1]);
                }
            }
        }
        return "";
    }
    let nodeId = getUrlParam("nodeId"),
        parentId = getUrlParam("parentId"),
        context = decodeURI(getUrlParam("context")),  // 注意，此处对context做了一次转码
        leaf = getUrlParam("leaf"),
        level = getUrlParam("level"),
        spread = getUrlParam("spread")
    // 赋值
    form.val("show_form", {
        "nodeId": nodeId,
        "parentId": parentId,
        "context": context,
        "leaf": leaf,
        "level": level,
        "spread": spread
    });
    form.render(); //刷新表单
});
