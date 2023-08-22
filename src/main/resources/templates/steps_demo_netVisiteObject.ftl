<div class="layui-form detail-form">
    <blockquote class="layui-elem-quote">
        用户范围和接入设备类型
    </blockquote>
    <div class="layui-form-item">
        <label class="layui-form-label">用户范围</label>
        <div class="layui-input-inline">
            <div class="layui-form-mid layui-word-aux" style="width: max-content;">
                请选择场景用户应用范围，用户、部门及子部门和用户组至少选择一种
            </div>
            <div class="layui-form-item">
                <div class="layui-input-group">
                    <div class="layui-input-split layui-input-prefix">
                        用户
                    </div>
                    <input type="text" placeholder="请选择用户" class="layui-input">
                    <div class="layui-input-suffix">
                        <button class="layui-btn">选择</button>
                    </div>
                </div>
                <div class="layui-form-mid layui-word-aux" style="width: max-content;">
                    1、当需要配置多个用户时，建议使用用户组；<br>
                    2、外协账号在接入安全->网络准入控制策略设置->外来接入人员管理->驻场外协管理中创建
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-group">
                    <div class="layui-input-split layui-input-prefix">
                        部门及子部门
                    </div>
                    <input type="text" placeholder="请选择部门" class="layui-input">
                    <div class="layui-input-suffix">
                        <button class="layui-btn">选择</button>
                    </div>
                </div>
                <div class="layui-form-mid layui-word-aux" style="width: max-content;">
                    员工账号没有在组织架构中创建或同步，选择root部门
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-group">
                    <div class="layui-input-split layui-input-prefix">
                        用户组
                    </div>
                    <input type="text" placeholder="请选择用户组" class="layui-input">
                    <div class="layui-input-suffix">
                        <button class="layui-btn">选择</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备类型</label>
        <div class="layui-input-inline">
            <input type="radio" name="deviceType" value="1" title="所有设备类型" checked>
            <input type="radio" name="deviceType" value="2" title="指定设备类型">
        </div>
    </div>
</div>
