package com.qcz.qmplatform.common.aop.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.support.json.JSONUtils;
import com.qcz.qmplatform.common.aop.annotation.CustomOperateLog;
import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.NoRecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.utils.CommonUtils;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.ReflectUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.common.utils.TimeUtils;
import com.qcz.qmplatform.module.common.CommonService;
import com.qcz.qmplatform.module.sys.dao.SysOperateLogDao;
import com.qcz.qmplatform.module.sys.entity.SysOperateLog;
import com.qcz.qmplatform.module.sys.entity.User;

/**
 * 操作日志 拦截
 * @author changzhongq
 * @time 2018年11月23日 下午3:59:27
 */
@Aspect
@Component
@SuppressWarnings("unchecked")
public class OperateLogAspect {

	@Autowired
	private SysOperateLogDao sysOperateLogDao;

	/**
	 * 线程池服务
	 */
	private ExecutorService executorService;

	public OperateLogAspect() {
		executorService = Executors.newFixedThreadPool(10);
	}

	/**
	 * 切点-登录登出
	 */
	@Pointcut("@annotation(com.qcz.qmplatform.common.aop.annotation.CustomOperateLog)")
	public void customPointcut() {

	}

	/**
	 * 切点-查询
	 */
	@Pointcut("execution(* com.qcz.qmplatform.module..*.service.*.find*(..))")
	public void findPointcut() {

	}

	/**
	 * 切点-修改
	 */
	@Pointcut("execution(* com.qcz.qmplatform.common.base.BaseService.updateWithOperateLog(..))")
	public void updatePointcut() {

	}

	/**
	 * 切点-保存
	 */
	@Pointcut("execution(* com.qcz.qmplatform.common.base.BaseService.save(..))")
	public void savePointcut() {

	}

	/**
	 * 切点-删除
	 */
	@Pointcut("execution(* com.qcz.qmplatform.common.base.BaseService.delete(..))")
	public void deletePointcut() {

	}

	/**
	 * 通知：登录登出记录
	 * @param joinPoint 连接点
	 * @throws Throwable
	 */
	@Around("customPointcut()")
	public Object customAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		// 找不到方法或如果类上有不记录日志的注解，则操作记录将不会插入到数据库
		final HttpServletRequest request = getHttpServletRequest();
		if (request == null || targetClass.getAnnotation(NoRecordLog.class) != null) {
			return joinPoint.proceed();
		}
		/*
		 * 操作状态，默认为成功1，如果原逻辑操作出错，需要记录日志操作状态为失败，状态码为0
		 * 无论原逻辑操作是否成功都需要记录到日志里面，所以记录日志行为在finally里面执行
		 */
		int operateStatus = 1;
		Object proceed = null;
		User currentUser = SubjectUtils.getUser();
		try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {// 原逻辑程序有异常，这里抛回，并修改操作状态
			operateStatus = 0;
			throw new Exception(e);
		} finally {
			String requestUrl = request.getServletPath();
			String ipAddress = HttpServletUtils.getIpAddress(request);
			// 在另一线程使用外部可变变量需要做一层过渡
			int operateStatusTemp = operateStatus;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
					Method method = methodSignature.getMethod();
					CustomOperateLog customOperateLog = method.getAnnotation(CustomOperateLog.class);
					String moduleName = null;
					OperateType type = null;
					if (customOperateLog != null) {
						type = customOperateLog.type();
						switch (type) {
						case LOGIN:
							moduleName = "登录系统";
							break;
						case LOGOUT:
							moduleName = "退出系统";
							break;
						default:
							moduleName = "未知";
							break;
						}
					}
					insertOperateLog(operateStatusTemp, type, "", moduleName, requestUrl, ipAddress, null, currentUser);
				}
			});
		}
		return proceed;
	}

	/**
	 * 通知：查询日志记录
	 * @param joinPoint 连接点
	 * @throws Throwable
	 */
	@Around("findPointcut()")
	public Object findAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		// 找不到方法或如果类上有不记录日志的注解，则操作记录将不会插入到数据库
		final HttpServletRequest request = getHttpServletRequest();
		if (request == null || targetClass.getAnnotation(NoRecordLog.class) != null) {
			return joinPoint.proceed();
		}
		/*
		 * 操作状态，默认为成功1，如果原逻辑操作出错，需要记录日志操作状态为失败，状态码为0
		 * 无论原逻辑操作是否成功都需要记录到日志里面，所以记录日志行为在finally里面执行
		 */
		int operateStatus = 1;
		Object proceed = null;
		User currentUser = SubjectUtils.getUser();
		try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {// 原逻辑程序有异常，这里抛回，并修改操作状态
			operateStatus = 0;
			throw new Exception(e);
		} finally {
			String requestUrl = request.getServletPath();
			String ipAddress = HttpServletUtils.getIpAddress(request);
			// 在另一线程使用外部可变变量需要做一层过渡
			int operateStatusTemp = operateStatus;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
					// 方法参数名
					String[] parameterNames = methodSignature.getParameterNames();
					// 方法参数值
					Object[] parameterValues = joinPoint.getArgs();
					// 解析方法参数
					Map<String, Object> updateParams = new HashMap<>();
					if (parameterValues != null && parameterValues.length > 0) {
						for (int i = 0; i < parameterValues.length; i++) {
							Object object = parameterValues[i];
							if (object instanceof Map) {
								Map<String, Object> paramMap = (Map<String, Object>) object;
								updateParams.put(parameterNames[i], paramMap);
							} else if (object instanceof String) {
								updateParams.put(parameterNames[i], object.toString());
							}
						}
					}
					Module module = targetClass.getAnnotation(Module.class);
					String moduleName = null;
					if (module != null) {
						moduleName = module.value();
					}
					insertOperateLog(operateStatusTemp, OperateType.FIND, JSONUtils.toJSONString(updateParams), moduleName, requestUrl, ipAddress,
							ReflectUtils.getTable(ReflectUtils.getEntityClass(targetClass)), currentUser);
				}
			});
		}
		return proceed;
	}

	/**
	 * 通知：保存（新增或修改）日志记录
	 * @param joinPoint 连接点
	 * @throws Throwable
	 */
	@Around("savePointcut()")
	public Object saveAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		final HttpServletRequest request = getHttpServletRequest();
		// 找不到方法或如果类上有不记录日志的注解，则操作记录将不会插入到数据库
		if (request == null || targetClass.getAnnotation(NoRecordLog.class) != null) {
			return joinPoint.proceed();
		}
		Map<String, Object> updateParams = new HashMap<>();
		OperateType operateType = null;
		Class<?> entityClass = ReflectUtils.getEntityClass(targetClass);
		try {
			// 方法参数值
			Object data = joinPoint.getArgs()[0];
			Map<String, Object> nowData = CommonUtils.convertMap(data);
			Object id = ReflectUtils.getId(data);
			Map<String, Object> preUpdateData = CommonService.findByIdWithLowerCaseKey(entityClass, id);
			if (preUpdateData == null) {
				// 新增
				operateType = OperateType.INSERT;
				updateParams = nowData;
			} else {
				// 修改
				operateType = OperateType.UPDATE;
				updateParams.put("updateBefore", preUpdateData);
				updateParams.put("updateAfter", nowData);
			}
		} catch (Exception e) {
			return joinPoint.proceed();
		}
		/*
		 * 操作状态，默认为成功1，如果原逻辑操作出错，需要记录日志操作状态为失败，状态码为0
		 * 无论原逻辑操作是否成功都需要记录到日志里面，所以记录日志行为在finally里面执行
		 */
		int operateStatus = 1;
		Object proceed = null;
		User currentUser = SubjectUtils.getUser();
		try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {// 原逻辑程序有异常，这里抛回，并修改操作状态
			operateStatus = 0;
			throw new Exception(e);
		} finally {
			String requestUrl = request.getServletPath();
			String ipAddress = HttpServletUtils.getIpAddress(request);
			// 在另一线程使用外部可变变量需要做一层过渡
			int operateStatusTemp = operateStatus;
			Map<String, Object> updateParamsTemp = updateParams;
			OperateType operateTypeTemp = operateType;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Module module = targetClass.getAnnotation(Module.class);
					String moduleName = null;
					if (module != null) {
						moduleName = module.value();
					}
					insertOperateLog(operateStatusTemp, operateTypeTemp, JSONUtils.toJSONString(updateParamsTemp), moduleName, requestUrl, ipAddress, ReflectUtils.getTable(entityClass), currentUser);
				}
			});
		}
		return proceed;
	}

	/**
	 * 通知：删除日志记录
	 * @param joinPoint 连接点
	 * @throws Throwable
	 */
	@Around("deletePointcut()")
	public Object deleteAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		final HttpServletRequest request = getHttpServletRequest();
		// 找不到方法或如果类上有不记录日志的注解，则操作记录将不会插入到数据库
		if (request == null || targetClass.getAnnotation(NoRecordLog.class) != null) {
			return joinPoint.proceed();
		}
		Map<String, Object> oldData = null;
		Class<?> entityClass = ReflectUtils.getEntityClass(targetClass);
		try {
			// 方法参数值
			Object id = joinPoint.getArgs()[0];
			// 待删除的数据
			oldData = CommonService.findByIdWithLowerCaseKey(entityClass, id);
		} catch (Exception e) {
			return joinPoint.proceed();
		}
		/*
		 * 操作状态，默认为成功1，如果原逻辑操作出错，需要记录日志操作状态为失败，状态码为0
		 * 无论原逻辑操作是否成功都需要记录到日志里面，所以记录日志行为在finally里面执行
		 */
		int operateStatus = 1;
		Object proceed = null;
		User currentUser = SubjectUtils.getUser();
		try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {// 原逻辑程序有异常，这里抛回，并修改操作状态
			operateStatus = 0;
			throw new Exception(e);
		} finally {
			String requestUrl = request.getServletPath();
			String ipAddress = HttpServletUtils.getIpAddress(request);
			// 在另一线程使用外部可变变量需要做一层过渡
			int operateStatusTemp = operateStatus;
			Map<String, Object> oldDataTemp = oldData;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Module module = targetClass.getAnnotation(Module.class);
					String moduleName = null;
					if (module != null) {
						moduleName = module.value();
					}
					insertOperateLog(operateStatusTemp, OperateType.DELETE, JSONUtils.toJSONString(oldDataTemp), moduleName, requestUrl, ipAddress, ReflectUtils.getTable(entityClass), currentUser);
				}
			});
		}
		return proceed;
	}

	/**
	 * 通知：更新日志记录
	 * @param joinPoint 连接点
	 * @throws Throwable
	 */
	@Around("updatePointcut()")
	public Object updateAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		final HttpServletRequest request = getHttpServletRequest();
		// 找不到方法或如果类上有不记录日志的注解，则操作记录将不会插入到数据库
		if (request == null || targetClass.getAnnotation(NoRecordLog.class) != null) {
			return joinPoint.proceed();
		}
		Class<?> entityClass = ReflectUtils.getEntityClass(targetClass);
		int operateStatus = 1;
		Object proceed = null;
		User currentUser = SubjectUtils.getUser();
		try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {// 原逻辑程序有异常，这里抛回，并修改操作状态
			operateStatus = 0;
			throw new Exception(e);
		} finally {
			String requestUrl = request.getServletPath();
			String ipAddress = HttpServletUtils.getIpAddress(request);
			// 在另一线程使用外部可变变量需要做一层过渡
			int operateStatusTemp = operateStatus;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Map<String, Object> updateParams = new HashMap<>();
					updateParams.put("updateBefore", CommonUtils.convertMap(joinPoint.getArgs()[0]));
					updateParams.put("updateAfter", CommonUtils.convertMap(joinPoint.getArgs()[1]));
					Module module = targetClass.getAnnotation(Module.class);
					String moduleName = null;
					if (module != null) {
						moduleName = module.value();
					}
					insertOperateLog(operateStatusTemp, OperateType.UPDATE, JSONUtils.toJSONString(updateParams), moduleName, requestUrl, ipAddress, ReflectUtils.getTable(entityClass), currentUser);
				}
			});
		}
		return proceed;
	}

	/**
	 * 插入操作日志
	 * @param operateStatus 操作状态
	 * @param operateType 操作类型
	 * @param updateParams 更新参数
	 * @param module 模块名，在service类上使用注解@Module定义
	 * @param requestUrl 请求路径（MVC C）
	 * @param ipAddress ip地址
	 * @param tableName 表名
	 * @param currentUser 当前操作人
	 */
	public void insertOperateLog(int operateStatus, OperateType operateType, String updateParams, String module, String requestUrl, String ipAddress, String tableName, User currentUser) {
		SysOperateLog log = new SysOperateLog();
		log.setOperateModule(module);
		log.setUpdateParams(updateParams);
		log.setLogId(StringUtils.getUUID());
		log.setOperateUserId(currentUser.getUserId());
		log.setOperateUserName(currentUser.getUserName());
		log.setOperateTime(TimeUtils.getTimestamp());
		log.setOperateType(String.valueOf(operateType.getType()));
		log.setRequestUrl(requestUrl);
		log.setIp(ipAddress);
		log.setTableName(tableName);
		log.setOperateStatus(operateStatus);

		sysOperateLogDao.insert(log);
	}

	/**
	 * 获取request
	 */
	public HttpServletRequest getHttpServletRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> clazz = Class.forName("com.qcz.qmplatform.module.sys.service.MenuService");
		ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
		System.err.println(type.getActualTypeArguments()[0]);
	}
}
