package com.qcz.qmplatform.module.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;

    private String filePath;

}

