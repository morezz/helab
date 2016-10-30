package helab.security;

import helab.common.CommonConfigBean;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SecuritySessionMgr
 *
 * @author <a href="mailto:jzchen@coremail.cn">jzchen</a>
 */
public class SecuritySessionMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySessionMgr.class);

    private static final Integer sessionTimeOut = CommonConfigBean.getContextPropertyInt("ecmedm.securitySession.timeout");

    private static final Map<String, SecuritySession> sessionMap = new ConcurrentHashMap<String, SecuritySession>();

    private static final ThreadLocal<SecuritySession> SecuritySessionHolder = new ThreadLocal<SecuritySession>();

    public static SecuritySession getSessionBySid(String sid) {
        SecuritySession securitySession = sessionMap.get(sid);
        if (securitySession != null && securitySession.isValid()) {
            return securitySession;
        } else {
            return null;
        }
    }

    public static void addSession(SecuritySession securitySession) {
        sessionMap.put(securitySession.getSid(), securitySession);
    }

    public static void cleanSession() {
        sessionMap.clear();
    }

    public static void setThreadSession(SecuritySession securitySession) {
        SecuritySessionHolder.set(securitySession);
    }

    public static SecuritySession getThreadSession() {
        return SecuritySessionHolder.get();
    }

    /*public static SecuritySession getSessionByTomcatSid(String sid) {
        for (SecuritySession securitySession : sessionMap.values()) {
            if (securitySession.getHttpSession().getId().equals(sid)) {
                return securitySession;
            }
        }
        return null;
    }*/

    public static void removeSession(SecuritySession securitySession) {
        LOGGER.info("remove security session sid: {}", securitySession.getSid());
        sessionMap.remove(securitySession.getSid());
    }

    public static void cleanInvalidSession() {
        Collection<SecuritySession> securitySessions = sessionMap.values();
        Date now = new Date();
        for (SecuritySession securitySession : securitySessions) {
            if (now.after(DateUtils.addSeconds(securitySession.getLastModifyTime(), sessionTimeOut))) {
                LOGGER.info("cleanInvalidSession, sid: {}", securitySession.getSid());
                removeSession(securitySession);
            }
        }
    }
}
