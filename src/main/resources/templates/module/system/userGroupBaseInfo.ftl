<form class="layui-form detail-form" action="javascript:void(0);" lay-filter="userGroup-form">
    <input type="hidden" name="id">
    <div id="parentDiv" class="layui-form-item hide">
        <label class="layui-form-label">所属上级</label>
        <div class="layui-input-block">
            <input type="hidden" name="parentId" value="${RequestParameters["parentId"]!}">
            <input type="text" name="parentName" disabled class="layui-input" value="${RequestParameters["parentName"]!}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">用户组名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" placeholder="请输入用户组名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea" id="remark" name="remark"></textarea>
        </div>
    </div>
    <div class="detail-operator">
        <button type="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="userGroup-submit">保存</button>
    </div>
</form>