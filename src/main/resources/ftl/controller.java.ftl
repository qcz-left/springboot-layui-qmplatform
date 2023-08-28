package ${package.Controller};

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
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

import javax.annotation.Resource;
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
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
    ${entity}Service ${entity?uncap_first}Service;

    @GetMapping("/${entity?uncap_first}ListPage")
    public String ${entity?uncap_first}ListPage() {
        return "/module/${package.ModuleName}/${entity?uncap_first}List";
    }

    @GetMapping("/${entity?uncap_first}DetailPage")
    public String ${entity?uncap_first}DetailPage() {
        return "/module/${package.ModuleName}/${entity?uncap_first}Detail";
    }

    @PostMapping("/get${entity}List")
    @ResponseBody
    public ResponseResult<PageResult> get${entity}List(PageRequest pageRequest) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(${entity?uncap_first}Service.list()));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<${entity}> get(@PathVariable String id) {
        return ResponseResult.ok(${entity?uncap_first}Service.getById(id));
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增${table.comment!}")
    public ResponseResult<Void> insert(@RequestBody ${entity} ${entity?uncap_first}) {
        return ResponseResult.newInstance(${entity?uncap_first}Service.saveOne(${entity?uncap_first}));
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改${table.comment!}")
    public ResponseResult<Void> update(@RequestBody ${entity} ${entity?uncap_first}) {
        return ResponseResult.newInstance(${entity?uncap_first}Service.updateOne(${entity?uncap_first}));
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除${table.comment!}")
    public ResponseResult<Void> delete(String ids) {
        return ResponseResult.newInstance(${entity?uncap_first}Service.removeByIds(StringUtils.split(ids, ',')));
    }
}
</#if>
