package helab.service;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public interface AdminInfoService {

    ServiceResult login(String username, String passwd);

    ServiceResult createAdminInfo(String username, String password, Long orgId);

    ServiceResult resetPasswd(String username, String passwd);
}
