package com.qcz.qmplatform.common.bean;

/**
 * 此文件为 Freemarker 模板引擎自动生成，请勿修改
 */
public class PrivCode {

<#list menus as menu>
    /**
     * ${menu.name}
     */
    public static final String MENU_CODE_${menu.code?upper_case?replace("-", "_")?replace(":", "_")?replace(" ", "")} = "${menu.code}";

</#list>
<#list buttons as button>
    /**
     * ${button.name}
     */
    public static final String BTN_CODE_${button.code?upper_case?replace("-", "_")?replace(":", "_")?replace(" ", "")} = "${button.code}";

</#list>
}
