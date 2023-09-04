package com.qcz.qmplatform.module.business.system.domain.assist;

public enum PermissionType {

    MENU(1),

    BUTTON(2);

    private final int type;

    PermissionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
