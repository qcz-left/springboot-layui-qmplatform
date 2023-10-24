package com.qcz.qmplatform.module.business.system.domain.vo;

import com.qcz.qmplatform.common.anno.ExcelField;
import com.qcz.qmplatform.module.business.system.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserVO extends User {

    private List<String> organizationIds;

    private List<String> roleIds;

    @ExcelField("性别")
    private String userSexName;

    @ExcelField("状态")
    private String lockedName;

    @ExcelField("所属部门")
    private String organizationName;

    public String getUserSexName() {
        switch (super.getUserSex()) {
            case "1":
                return "男";
            case "2":
                return "女";
            default:
                return "";
        }
    }

    public String getLockedName() {
        switch (super.getLocked()) {
            case 0:
                return "正常";
            case 1:
                return "锁定";
            default:
                return "";
        }
    }
}
