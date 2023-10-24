<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<body class="detail-body">
<div class="layui-fluid">
    <form class="layui-form detail-form" action="javascript:void(0);" lay-filter="user-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label required">用户名</label>
            <div class="layui-input-block">
                <input type="text" name="username" lay-verify="required" autocomplete="off" placeholder="请输入用户名" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">登录名</label>
            <div class="layui-input-block">
                <input type="text" id="loginname" name="loginname" lay-verify="required|loginname" autocomplete="off" placeholder="请输入登录名" class="layui-input" disabled>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <div class="layui-form-mid layui-word-aux">用户创建成功后，登录名将不可修改</div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">密码</label>
            <div class="layui-input-block">
                <input type="password" autocomplete="new-password" style="display:none">
                <input type="password" name="password" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <div id="userSex"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <div id="locked"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">部门</label>
            <#if RequestParameters["parentId"]??>
                <div class="layui-form-mid layui-word-aux">${RequestParameters["parentName"]!}</div>
            <#else>
                <div class="layui-input-block">
                    <div id="organizationIds"></div>
                </div>
            </#if>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-block">
                <div id="roleIds"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">手机号码</label>
            <div class="layui-input-block">
                <input type="text" name="phone" lay-verify="required|phone" autocomplete="off" placeholder="请输入手机号" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label required">邮箱</label>
            <div class="layui-input-block">
                <input type="text" name="emailAddr" lay-verify="email" autocomplete="off" placeholder="请输入邮箱" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" id="remark" name="remark"></textarea>
            </div>
        </div>
        <div class="detail-operator">
            <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="user-submit">保存</button>
            <button type="submit" class="layui-btn layui-btn-primary" onclick="closeCurrentIframe()">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript">
let id = "${RequestParameters["id"]!}";
let parentId = "${RequestParameters["parentId"]!}";
layui.use(['form', 'layer', 'xmSelect'], function () {
    let form = layui.form;
    let layer = layui.layer;
    let xmSelect = layui.xmSelect;
    // 表单数据校验
    form.verify({
        loginname: function (value) {
            let valid;
            CommonUtil.getSync(ctx + '/user/validateLoginName', {
                loginname: value,
                userId: id
            }, function (result) {
                valid = result.ok;
            });
            if (!valid) {
                return '登录名已存在，请重新输入！';
            }
        },
        password: [
            /^[\S]{5,12}$/,
            '密码必须5到12位，且不能出现空格'
        ]
    });

    let detail = {
        organizationIds: []
    };
    if (id) {
        CommonUtil.getSync(ctx + '/user/getUser/' + id, {}, function (result) {
            result.oldPassword = result.password;
            result.password = Password.UN_CHANGED_PASSWORD;
            form.val('user-form', result);
            detail = result;
        })
    } else {
        $("#loginname").removeAttr("disabled");
    }

    let organizationIdsSelect;
    if (!parentId) {
        // 部门数据加载
        organizationIdsSelect = SelectUtil.render(xmSelect, {
            el: '#organizationIds',
            name: 'organizationIds',
            tree: true,
            treeExpandedKeys: detail.organizationIds,
            data: []
        });
        CommonUtil.getAjax(ctx + '/organization/getOrgTree', {}, function (result) {
            organizationIdsSelect.update({
                initValue: detail.organizationIds,
                data: result.data
            })
        })
    }

    // 角色数据加载
    let roleIdsSelect = SelectUtil.render(xmSelect, {
        el: '#roleIds',
        name: 'roleIds',
        prop: {
            value: 'roleId',
            name: 'roleName'
        },
        data: []
    });
    CommonUtil.postAjax(ctx + '/role/getRoleList', {
        limit: 9999
    }, function (result) {
        roleIdsSelect.update({
            initValue: detail.roleIds,
            data: result.data.list
        })
    });

    // 性别数据加载
    SelectUtil.render(xmSelect, {
        el: '#userSex',
        name: 'userSex',
        radio: true,
        model: {label: {type: 'text'}},
        data: [{
            name: "男",
            value: "1"
        }, {
            name: "女",
            value: "2"
        }],
        initValue: [detail.userSex]
    });

    // 账号状态数据加载
    SelectUtil.render(xmSelect, {
        el: '#locked',
        name: 'locked',
        radio: true,
        model: {label: {type: 'text'}},
        data: [{
            name: "正常",
            value: "0"
        }, {
            name: "锁定",
            value: "1"
        }],
        initValue: [detail.locked]
    });

    form.on('submit(user-submit)', function (data) {
        layer.load(2);
        data.field.roleIds = roleIdsSelect.getValue('value');
        data.field.organizationIds = [parentId] || organizationIdsSelect.getValue('value');
        data.field.password = rsaEncrypt(data.field.password);
        CommonUtil.postOrPut(id, ctx + (id ? '/user/updateUser' : '/user/addUser'), data.field, function (result) {
            top.layer.closeAll();
            LayerUtil.respMsg(result, Msg.SAVE_SUCCESS, Msg.SAVE_FAILURE, () => {
                if (parentId) {
                    reloadFrame();
                } else {
                    reloadParentTable();
                }
            })
        });
        return false;
    });

});
</script>
</body>
</html>
