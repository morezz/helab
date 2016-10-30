package helab.resource.impl;

import com.google.gson.JsonObject;
import helab.common.JsonUtils;
import helab.model.AdminInfo;
import helab.resource.ResourceResult;
import helab.resource.AdminInfoResource;
import helab.security.SecuritySession;
import helab.security.SecuritySessionMgr;
import helab.service.AdminInfoService;
import helab.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public class AdminInfoResourceImpl implements AdminInfoResource {


    @Autowired
    private AdminInfoService adminInfoService;

    public ResourceResult login(String body, @Context HttpServletResponse response, @Context HttpServletRequest request) {
        JsonObject jsonObject = JsonUtils.String2JsonObject(body);
        String userName = jsonObject.get("username").getAsString();
        String passwd = jsonObject.get("passwd").getAsString();
        ServiceResult result = adminInfoService.login(userName, passwd);
        if (result.getCode() == 0) {
            AdminInfo adminInfo = (AdminInfo) result.getResult();
            String sid = UUID.randomUUID().toString();
            SecuritySession securitySession = new SecuritySession();
            securitySession.setSid(sid);
            securitySession.setLogin(true);
            securitySession.setUsername(userName);
            SecuritySessionMgr.addSession(securitySession);
            Map<String, Object> sidResult = new HashMap<String, Object>();
            sidResult.put("sid", securitySession.getSid());
            sidResult.put("username", adminInfo.getUserName());
            Cookie cookie = new Cookie("HELAB-SID", securitySession.getSid());
            response.addCookie(cookie);
            return ResourceResult.successResult(sidResult);
        }
        return ResourceResult.failResult("ADMININFO_LOGIN_FAIL");
    }

    // {"username":"admin", "passwd":"123456"}
    public ResourceResult createAdmin(String body) {
        SecuritySession session = SecuritySessionMgr.getThreadSession();
        String currentUser = session.getUsername();
        if (!"admin".equals(currentUser)) {
            return ResourceResult.failResult("PERMISSION_DENIED");
        }
        JsonObject jsonObject = JsonUtils.String2JsonObject(body);
        if (jsonObject.get("username") == null || jsonObject.get("username").isJsonNull()) {
            return ResourceResult.failResult("ADMININFO_CREATE_USERNAME_NULL");
        }
        if (jsonObject.get("password") == null || jsonObject.get("passwd").isJsonNull()) {
            return ResourceResult.failResult("ADMININFO_CREATE_PASSWD_NULL");
        }
        String username = jsonObject.get("username").getAsString();
        String passwd = jsonObject.get("passwd").getAsString();
        Long orgId = jsonObject.get("orgId").getAsLong();
        ServiceResult serviceResult = adminInfoService.createAdminInfo(username, passwd, orgId);
        return serviceResult.getCode() == 0 ? ResourceResult.successResult() : ResourceResult.failResult(serviceResult.getMessage());
    }
    // {"passwd": "213456"}
    public ResourceResult resetPasswd(String body) {
        SecuritySession session = SecuritySessionMgr.getThreadSession();
        String currentUser = session.getUsername();
        JsonObject jsonObject = JsonUtils.String2JsonObject(body);
        if (jsonObject.get("passwd") == null || jsonObject.get("password").isJsonNull()) {
            return ResourceResult.failResult("ADMININFO_RESETPASSWD_PASSWD_NULL");
        }
        String passwd = jsonObject.get("passwd").getAsString();
        ServiceResult serviceResult = adminInfoService.resetPasswd(currentUser, passwd);
        return serviceResult.getCode() == 0 ? ResourceResult.successResult() : ResourceResult.failResult("ADMININFO_RESETPASSWD_FAILED");
    }

    public ResourceResult logout(HttpServletResponse response, HttpServletRequest request) {
        SecuritySession securitySession = SecuritySessionMgr.getThreadSession();
        SecuritySessionMgr.removeSession(securitySession);
        return ResourceResult.successResult();
    }

}
