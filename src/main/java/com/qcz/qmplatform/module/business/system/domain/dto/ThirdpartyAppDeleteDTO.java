package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ThirdpartyAppDeleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

}
