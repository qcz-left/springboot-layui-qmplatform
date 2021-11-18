package ${package.Service};

import ${superServiceImplClassPackage};
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> {

    public boolean saveOne(${entity} ${entity?uncap_first}) {
        return save(${entity?uncap_first});
    }

    public boolean updateOne(${entity} ${entity?uncap_first}) {
        return updateById(${entity?uncap_first});
    }
}
</#if>
