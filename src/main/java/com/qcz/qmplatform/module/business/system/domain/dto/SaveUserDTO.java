package com.qcz.qmplatform.module.business.system.domain.dto;

import com.qcz.qmplatform.module.business.system.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 保存用户参数实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SaveUserDTO extends User implements Serializable {

    private List<String> organizationIds;

    private List<String> roleIds;
}
