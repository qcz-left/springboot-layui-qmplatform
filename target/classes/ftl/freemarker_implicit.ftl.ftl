${a!'[#ftl]'}
${a!'[#-- @implicitly included --]'}
${a!'[[#-- @ftlvariable name="shiro" type="com.jagregory.shiro.freemarker.ShiroTags" --]]'}
<#list menus as menu>
${a!'[#--${menu.name}--]'}
${a!'[#-- @ftlvariable name="PrivCode.MENU_CODE_${menu.code?upper_case?replace("-", "_")?replace(":", "_")?replace(" ", "")}" type="java.lang.String" --]'}
</#list>
<#list buttons as button>
${a!'[#--${button.name}--]'}
${a!'[#-- @ftlvariable name="PrivCode.BTN_CODE_${button.code?upper_case?replace("-", "_")?replace(":", "_")?replace(" ", "")}" type="java.lang.String" --]'}
</#list>
