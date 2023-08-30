package com.qcz.qmplatform.intercept;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.ClassUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mybatis SQL 数据权限 拦截器
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
@Slf4j
public class MybatisInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisInterceptor.class);

    private static final Integer MAPPED_STATEMENT_INDEX = 0;
    private static final Integer PARAM_OBJ_INDEX = 1;
    private static final Integer ROW_BOUNDS_INDEX = 2;
    private static final Integer RESULT_HANDLER_INDEX = 3;
    private static final Integer CACHE_KEY_INDEX = 4;
    private static final Integer BOUND_SQL_INDEX = 5;

    private Field additionalParametersField;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        String id = ms.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        final Class<?> clazz = Class.forName(className);

        Object parameter = args[PARAM_OBJ_INDEX];
        BoundSql boundSql = ms.getBoundSql(parameter);
        CacheKey cacheKey = null;
        Executor executor = (Executor) invocation.getTarget();
        RowBounds rowBounds = null;
        ResultHandler<?> resultHandler = null;
        if (args.length == 4) {
            rowBounds = (RowBounds) args[ROW_BOUNDS_INDEX];
            resultHandler = (ResultHandler<?>) args[RESULT_HANDLER_INDEX];
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else if (args.length == 6) {
            boundSql = (BoundSql) args[BOUND_SQL_INDEX];
            cacheKey = (CacheKey) args[CACHE_KEY_INDEX];
        }

        boundSql = buildNewBoundSql(ms, boundSql);
        String sql = boundSql.getSql();

        Method method;
        AuthQuery authQuery;
        if (args.length > 2
                && (method = ReflectUtil.getMethodByName(clazz, methodName)) != null
                && (authQuery = method.getAnnotation(AuthQuery.class)) != null
                && !SecurityUtils.getSubject().hasRole(Constant.SYSTEM_ADMIN)) {
            sql = StringUtils.format("select * from ({}) as tmp where {} = '{}'", sql, authQuery.userColumn(), SubjectUtils.getUserId());
        }
        MappedStatement newMappedStatement = setCurrentSql(ms, boundSql, sql);

        if (args.length == 2) {
            return executor.update(newMappedStatement, parameter);
        }
        return executor.query(newMappedStatement, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    private MappedStatement setCurrentSql(MappedStatement mappedStatement, BoundSql boundSql, String sql) {
        BoundSqlSource boundSqlSource = new BoundSqlSource(boundSql);
        MappedStatement newMappedStatement = copyFromMappedStatement(mappedStatement, boundSqlSource);
        MetaObject metaObject = MetaObject.forObject(newMappedStatement,
                new DefaultObjectFactory(), new DefaultObjectWrapperFactory(),
                new DefaultReflectorFactory());
        metaObject.setValue("sqlSource.boundSql.sql", sql);
        return newMappedStatement;
    }

    private static class BoundSqlSource implements SqlSource {

        private final BoundSql boundSql;

        private BoundSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        try {
            //反射获取 BoundSql 中的 additionalParameters 属性
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private BoundSql buildNewBoundSql(MappedStatement ms, BoundSql boundSql) throws IllegalAccessException {
        String sql = boundSql.getSql();
        Configuration configuration = ms.getConfiguration();
        Object parameterObject = boundSql.getParameterObject();
        Map<String, Object> parameterMap;
        if (parameterObject instanceof Map) {
            parameterMap = (Map<String, Object>) parameterObject;
        } else if (ClassUtils.isCommonDataType(parameterObject.getClass())) {
            // 单个参数的情况
            parameterMap = new HashMap<>();

            Matcher matcher = Pattern.compile("#[\\s\\S]*?#|\\$[\\s\\S]*?\\$").matcher(sql);
            while (matcher.find()) {
                String group = matcher.group();
                String name = group.substring(1, group.length() - 1);
                parameterMap.put(name, parameterObject);
            }
        } else {
            parameterMap = BeanUtil.beanToMap(parameterObject);
        }
        List<ParameterMapping> newParameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }

        Map<String, Object> additionalParameter = new HashMap<>();

        String DYNAMIC_SQL_REG = "\\[\\[[\\s\\S]*?\\]\\]";
        Pattern ENC_PATTERN = Pattern.compile(DYNAMIC_SQL_REG);
        Matcher matcher = ENC_PATTERN.matcher(sql);
        while (matcher.find()) {
            String group = matcher.group();

            int indexOf = group.indexOf("#");
            boolean isPlaceHolder = indexOf > -1;
            indexOf = isPlaceHolder ? indexOf : group.indexOf("$");
            int lastIndexOf = group.lastIndexOf("#");
            lastIndexOf = isPlaceHolder ? lastIndexOf : group.lastIndexOf("$");

            String name = group.substring(indexOf + 1, lastIndexOf);
            Object value = parameterMap.get(name);
            if (ObjectUtil.isNotEmpty(value)) {
                sql = sql.replaceFirst(DYNAMIC_SQL_REG, Matcher.quoteReplacement(group.substring(2, group.length() - 2)));
                sql = setParameter(sql, name, value, newParameterMappings, configuration, additionalParameter, isPlaceHolder);
            } else {
                sql = sql.replaceFirst(DYNAMIC_SQL_REG, "");
            }
        }

        for (String key : parameterMap.keySet()) {
            boolean isPlaceHolder = Pattern.compile("#" + key + "#").matcher(sql).find();
            boolean isReplace = Pattern.compile(Matcher.quoteReplacement("$" + key + "$")).matcher(sql).find();
            if (isPlaceHolder || isReplace) {
                Object value = parameterMap.get(key);
                sql = setParameter(sql, key, value, newParameterMappings, configuration, additionalParameter, isPlaceHolder);
            }
        }

        BoundSql newBoundSql = new BoundSql(configuration, sql, newParameterMappings, parameterObject);
        if (additionalParametersField != null) {
            Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
            additionalParameters.putAll(additionalParameter);
            for (String key : additionalParameters.keySet()) {
                newBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
            }
        }

        return newBoundSql;
    }

    private String setParameter(String sql, String key, Object value, List<ParameterMapping> newParameterMappings, Configuration configuration, Map<String, Object> additionalParameter, boolean isPlaceHolder) {
        if (isPlaceHolder) {
            return setParameterAndMapping(sql, key, value, newParameterMappings, configuration, additionalParameter);
        }

        return setNormalParameter(sql, key, value);
    }

    /**
     * 直接替换的方式设置普通参数
     *
     * @return sql
     */
    private String setNormalParameter(String sql, String key, Object value) {
        StringBuilder replacement = new StringBuilder();
        String replacementStr;
        if (value instanceof List) {
            List<?> valList = (List<?>) value;
            for (Object o : valList) {
                replacement.append(",'").append(o).append("'");
            }
            replacementStr = replacement.deleteCharAt(0).toString();
        } else if (value instanceof Object[]) {
            Object[] valArray = (Object[]) value;
            for (Object o : valArray) {
                replacement.append(",'").append(o).append("'");
            }
            replacementStr = replacement.deleteCharAt(0).toString();
        } else if (value instanceof String) {
            replacementStr = "'" + value + "'";
        } else if (value instanceof Integer) {
            replacementStr = "" + value + "";
        } else {
            log.warn("the parameter type[{}] is not supported!", value.getClass());
            return sql;
        }
        return sql.replaceFirst(Matcher.quoteReplacement("$" + key + "$"), replacementStr);
    }

    /**
     * 设置带有占位符的参数
     *
     * @return sql
     */
    private String setParameterAndMapping(String sql, String key, Object value, List<ParameterMapping> newParameterMappings, Configuration configuration, Map<String, Object> additionalParameter) {
        StringBuilder replacement = new StringBuilder();
        String replacementStr;
        if (value instanceof List) {
            List<?> valList = (List<?>) value;
            for (int i = 0; i < valList.size(); i++) {
                Object o = valList.get(i);
                replacement.append(",?");
                ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, "__frch_" + key + "_" + i, o.getClass()).build();
                newParameterMappings.add(parameterMapping);
                additionalParameter.put("__frch_" + key + "_" + i, o);
            }
            replacementStr = replacement.deleteCharAt(0).toString();
        } else if (value instanceof Object[]) {
            Object[] valArray = (Object[]) value;
            for (int i = 0; i < valArray.length; i++) {
                Object o = valArray[i];
                replacement.append(",?");
                ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, "__frch_" + key + "_" + i, o.getClass()).build();
                newParameterMappings.add(parameterMapping);
                additionalParameter.put("__frch_" + key + "_" + i, o);
            }
            replacementStr = replacement.deleteCharAt(0).toString();
        } else {
            replacementStr = "?";
            ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, key, value.getClass()).build();
            newParameterMappings.add(parameterMapping);
            additionalParameter.put(key, value);
        }
        return sql.replaceFirst("#" + key + "#", replacementStr);
    }

}
