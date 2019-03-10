package com.qcz.qmplatform.common.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.redis.RedisCache;
import com.qcz.qmplatform.common.utils.HttpServletUtils;

/**
 * 防止多人同时登陆
 * @author changzhongq
 * @time 2019年3月9日 下午9:56:33
 */
@SuppressWarnings("unchecked")
public class KickoutSessionControlFilter extends AccessControlFilter {

	private String kickoutUrl; // 踢出后到的地址
	private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private int maxSession = 1; // 同一个帐号最大会话数 默认1

	private SessionManager sessionManager;

	@Autowired
	private RedisCache<String, Object> redisCache;

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}

		Session session = subject.getSession();
		String username = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();

		// TODO 同步控制
		Deque<Serializable> deque = (Deque<Serializable>) redisCache.get(username);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
		}

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
			deque.push(sessionId);
		}
		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if (kickoutAfter) { // 如果踢出后者
				kickoutSessionId = deque.removeFirst();
			} else { // 否则踢出前者
				kickoutSessionId = deque.removeLast();
			}
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e) {// ignore exception
			}
		}
		redisCache.put(username, deque);
		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if (session.getAttribute("kickout") != null) {
			// 会话被踢出了
			try {
				subject.logout();
			} catch (Exception e) { // ignore
			}
			saveRequest(request);
			if (HttpServletUtils.isAjax(request)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				ResponseResult resultData = new ResponseResult();
				resultData.setData(0);
				resultData.setIsSuccess(false);
				resultData.setMsg("您已经在其他地方登录，请重新登录！");
				response.getWriter().write(new Gson().toJson(resultData));
			} else {
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
			return false;
		}

		return true;
	}
}