package com.qcz.qmplatform.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.system.domain.Menu;
import com.qcz.qmplatform.module.system.pojo.MenuTree;
import com.qcz.qmplatform.module.system.pojo.Permission;
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

    List<MenuTree> selectMenuTree(Permission permission);

    List<Permission> getPermissionByIds(@Param("ids") List<String> ids);

    void deleteMenuById(@Param("menuIds") List<String> menuIds);

}
