package helab.security;

import java.util.Date;

/**
 * SecuritySession
 *
 * @author <a href="mailto:jzchen@coremail.cn">jzchen</a>
 */
public class SecuritySession {

    private String username;
    private String sid;
    private Date createTime = new Date();
    private boolean login = false;
    private boolean valid = true;
    private Date lastModifyTime = new Date();


    public void login() {
        this.login = true;
    }

    public void logout() {
        this.login = false;
    }

    public boolean isLogin() {
        return login;
    }

    public boolean isValid() {
        return valid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
