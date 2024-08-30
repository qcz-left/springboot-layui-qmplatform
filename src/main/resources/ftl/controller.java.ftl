package ${package.Controller};

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Entity}.${entity};
import ${package.Service}.${entity}Service;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
@Module("${table.comment!}")
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${entity}Service ${entity?uncap_first}Service;

    @GetMapping("/listPage")
    public String listPage() {
        return "/module/${config.injectionConfig.customMap.modulePrefix}${package.ModuleName}/${entity?uncap_first}List";
    }

    @GetMapping("/detailPage")
    public String detailPage() {
        return "/module/${config.injectionConfig.customMap.modulePrefix}${package.ModuleName}/${entity?uncap_first}Detail";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseResult<PageResult<${entity}>> list(PageRequest pageRequest) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(${entity?uncap_first}Service.list()));
    }

    @PostMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<${entity}> get(@PathVariable String id) {
        return ResponseResult.ok(${entity?uncap_first}Service.getById(id));
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增${table.comment!}")
    public ResponseResult<Void> insert(@RequestBody ${entity} ${entity?uncap_first}) {
        return ResponseResult.newInstance(${entity?uncap_first}Service.save(${entity?uncap_first}));
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改${table.comment!}")
    public ResponseResult<Void> update(@RequestBody ${entity} ${entity?uncap_first}) {
        return ResponseResult.newInstance(${entity?uncap_first}Service.updateById(${entity?uncap_first}));
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除${table.comment!}")
    public ResponseResult<Void> delete(@RequestBody List<String> ids) {
        return ResponseResult.newInstance(${entity?uncap_first}Service.removeByIds(ids));
    }
}
</#if>
