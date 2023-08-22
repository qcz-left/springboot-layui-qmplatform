<div class="layui-form detail-form">
    <div class="layui-form-item">
        <label class="layui-form-label required">接入场景名称</label>
        <div class="layui-input-inline">
            <input type="text" name="sceneName" lay-verify="required" placeholder="请输入接入场景名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">场景描述</label>
        <div class="layui-input-inline">
            <textarea class="layui-textarea" id="remark" name="remark" placeholder="请输入场景描述"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">选择网络访问对象类型</label>
        <div class="layui-input-inline">
            <input type="radio" name="netVisitorType" value="1" title="员工" checked>
            <input type="radio" name="netVisitorType" value="2" title="访客">
            <input type="radio" name="netVisitorType" value="3" title="IOT">
            <div class="layui-form-mid layui-word-aux" style="width: max-content">
                1、当公司员工要接入企业内网或Internet，可以选择员工对象类型，外协人员也属于员工；<br>
                2、当有来访客户需要临时接入企业内网或Internet，可以选择访客对象类型；<br>
                3、当哑终端（如IP电话、摄像头等）或非哑终端无需账号密码认证要接入企业内网或Internet，可以选择IOT。
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">网络设备与Radius间共用的安全字</label>
        <div class="layui-input-inline">
            <input type="password" autocomplete="new-password" style="display:none">
            <input type="password" name="safeWord" lay-verify="required" lay-affix="eye" placeholder="请输入安全字" autocomplete="off" class="layui-input">
            <div class="layui-form-mid layui-word-aux" style="width: max-content">
                填写的安全字要去网络设备上安全字保持一致，否则会导致认证失败。
            </div>
        </div>
    </div>
</div>