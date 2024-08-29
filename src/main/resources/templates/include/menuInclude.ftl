<#macro menuTreeMacro menus>
    <#list menus as menu>
        <dd>
            <#if menu.hasChild>
                <li class="layui-nav-item">
                    <a style="padding-left: ${20 + menu.level * 25}px;" class="parent-menu" href="javascript:void(0);"><i class="layui-icon <#if menu.icon?has_content>${menu.icon}<#else>layui-icon-list</#if>"></i>&nbsp;<span class="menu-name">&nbsp;${menu.name}</span></a>
                    <dl class="layui-nav-child">
                        <@menuTreeMacro menus = menu.childes />
                    </dl>
                </li>
            <#else>
                <a style="padding-left: ${20 + menu.level * 25}px;" href="javascript:void(0);" lay-id="${menu.id}" lay-href="${ctx + menu.linkUrl}"><i class="layui-icon <#if menu.icon?has_content>${menu.icon}<#else>layui-icon-list</#if>"></i>&nbsp;<span class="menu-name">&nbsp;${menu.name}</span></a>
            </#if>
        </dd>
    </#list>
</#macro>
<#list menuTree as menu>
    <#if menu.hasChild>
        <li class="layui-nav-item">
            <a class="parent-menu" href="javascript:void(0);"><i class="layui-icon <#if menu.icon?has_content>${menu.icon}<#else>layui-icon-list</#if>"></i>&nbsp;<span class="menu-name">&nbsp;${menu.name}</span></a>
            <dl class="layui-nav-child">
                <@menuTreeMacro menus = menu.childes />
            </dl>
        </li>
    <#else>
        <li class="layui-nav-item"><a href="javascript:void(0);" lay-id="${menu.id}" lay-href="${ctx + menu.linkUrl}"><i class="layui-icon <#if menu.icon?has_content>${menu.icon}<#else>layui-icon-list</#if>"></i>&nbsp;<span class="menu-name">&nbsp;${menu.name}</span></a></li>
    </#if>
</#list>

