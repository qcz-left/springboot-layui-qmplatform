package com.qcz.qmplatform.intercept;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
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
)})
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
        BoundSql boundSql;
        CacheKey cacheKey;
        Executor executor = (Executor) invocation.getTarget();
        RowBounds rowBounds = (RowBounds) args[ROW_BOUNDS_INDEX];
        ResultHandler<?> resultHandler = (ResultHandler<?>) args[RESULT_HANDLER_INDEX];
        if (args.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            boundSql = (BoundSql) args[BOUND_SQL_INDEX];
            cacheKey = (CacheKey) args[CACHE_KEY_INDEX];
        }

        boundSql = buildNewBoundSql(ms, boundSql);
        String sql = boundSql.getSql();

        Method method = ReflectUtil.getMethodByName(clazz, methodName);
        AuthQuery authQuery;
        if (method != null
                && (authQuery = method.getAnnotation(AuthQuery.class)) != null
                && !SecurityUtils.getSubject().hasRole(Constant.SYSTEM_ADMIN)) {
            sql = StringUtils.format("select * from ({}) as tmp where {} = '{}'", sql, authQuery.userColumn(), SubjectUtils.getUserId());
        }
        MappedStatement newMappedStatement = setCurrentSql(ms, boundSql, sql);

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
        } else {
            parameterMap = BeanUtil.beanToMap(parameterObject);
        }
        List<ParameterMapping> newParameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        if (CollectionUtil.isEmpty(parameterMap)) {
            return boundSql;
        }

        String DYNAMIC_SQL_REG = "\\[\\[.*?\\]\\]";
        Pattern ENC_PATTERN = Pattern.compile(DYNAMIC_SQL_REG);
        Matcher matcher = ENC_PATTERN.matcher(sql);
        while (matcher.find()) {
            String group = matcher.group();
            String name = group.substring(group.indexOf("#") + 1, group.lastIndexOf("#"));
            Object value = parameterMap.get(name);
            if (ObjectUtil.isNotEmpty(value)) {
                sql = sql.replaceFirst(DYNAMIC_SQL_REG, group.substring(2, group.length() - 2));
                sql = sql.replaceFirst("#" + name + "#", "?");
                ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, name, value.getClass()).build();
                newParameterMappings.add(parameterMapping);
            } else {
                sql = sql.replaceFirst(DYNAMIC_SQL_REG, "");
            }
        }

        Map<String, Object> additionalParameter = new HashMap<>();

        for (String key : parameterMap.keySet()) {
            if (Pattern.compile("#" + key + "#").matcher(sql).find()) {
                Object value = parameterMap.get(key);

                StringBuilder replacement = new StringBuilder();
                if (value instanceof List) {
                    List<?> valList = (List<?>) value;
                    for (int i = 0; i < valList.size(); i++) {
                        Object o = valList.get(i);
                        replacement.append(",?");
                        ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, "__frch_" + key + "_" + i, o.getClass()).build();
                        newParameterMappings.add(parameterMapping);
                        additionalParameter.put("__frch_" + key + "_" + i, o);
                    }
                    sql = sql.replaceFirst("#" + key + "#", replacement.deleteCharAt(0).toString());
                } else if (value instanceof Object[]) {
                    Object[] valArray = (Object[]) value;
                    for (int i = 0; i < valArray.length; i++) {
                        Object o = valArray[i];
                        replacement.append(",?");
                        ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, "__frch_" + key + "_" + i, o.getClass()).build();
                        newParameterMappings.add(parameterMapping);
                        additionalParameter.put("__frch_" + key + "_" + i, o);
                    }
                    sql = sql.replaceFirst("#" + key + "#", replacement.deleteCharAt(0).toString());
                } else {
                    // String
                    sql = sql.replaceFirst("#" + key + "#", "?");
                    ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, key, value.getClass()).build();
                    newParameterMappings.add(parameterMapping);
                    additionalParameter.put(key, value);
                }
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

}
