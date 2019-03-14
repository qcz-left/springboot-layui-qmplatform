package com.qcz.qmplatform.common.security;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcz.qmplatform.module.sys.entity.SysSession;
import com.qcz.qmplatform.module.sys.service.SysSessionService;

/**
 * 
 * @author changzhongq
 * @time 2019年3月10日 上午1:08:38
 */
public class MySessionDAO extends CachingSessionDAO {

	@Autowired
	private SysSessionService sysSessionService;

	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);

		SysSession sysSession = new SysSession();
		sysSession.setSessionId(sessionId.toString());
		sysSession.setSession(SerializationUtils.serialize(session));
		sysSessionService.save(sysSession);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		return (Session) SerializationUtils.deserialize(sysSessionService.find(sessionId).getSession());
	}

	protected void doUpdate(Session session) {
		SysSession sysSession = sysSessionService.find(session.getId());
		if (sysSession != null) {
			sysSession.setSession(SerializationUtils.serialize(session));
			sysSessionService.save(sysSession);
		}
	}

	protected void doDelete(Session session) {
		sysSessionService.delete(session.getId());
	}

}
