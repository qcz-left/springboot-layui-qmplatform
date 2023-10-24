package com.qcz.qmplatform.module.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.business.system.domain.Menu;
import com.qcz.qmplatform.module.business.system.domain.pojo.MenuTree;
import com.qcz.qmplatform.module.business.system.domain.pojo.Permission;
import com.qcz.qmplatform.module.business.system.domain.qo.PermissionQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuTree> selectMenuTree(PermissionQO permission);

    List<Permission> getPermissionByIds(@Param("ids") List<String> ids);

    int validatePermissionCode(@Param("permissionId") String permissionId, @Param("code") String code);
}
