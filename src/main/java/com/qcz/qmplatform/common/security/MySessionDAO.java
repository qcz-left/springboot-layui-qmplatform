package com.qcz.qmplatform.common.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcz.qmplatform.common.redis.RedisCache;

/**
 * 
 * @author changzhongq
 * @time 2019年3月10日 上午1:08:38
 */
@SuppressWarnings("unchecked")
public class MySessionDAO extends CachingSessionDAO {

	@Autowired
	private RedisCache<String, Object> redisCache;

	private static final String SESSION_CACHE = "session-cache";

	private static Map<Serializable, Session> sessionMap;

	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		getSessionMap().put(sessionId, session);
		setSessionMap();
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		return getSessionMap().get(sessionId);
	}

	protected void doUpdate(Session session) {
		getSessionMap().put(session.getId(), session);
		setSessionMap();
	}

	protected void doDelete(Session session) {
		getSessionMap().remove(session.getId(), session);
		setSessionMap();
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return getSessionMap().values();
	}

	public Map<Serializable, Session> getSessionMap() {
		sessionMap = (ConcurrentHashMap<Serializable, Session>) redisCache.get(SESSION_CACHE);
		if (sessionMap == null) {
			sessionMap = new ConcurrentHashMap<Serializable, Session>();
			redisCache.put(SESSION_CACHE, sessionMap);
		}
		return sessionMap;
	}

	public void setSessionMap() {
		redisCache.put(SESSION_CACHE, sessionMap);
	}

}
