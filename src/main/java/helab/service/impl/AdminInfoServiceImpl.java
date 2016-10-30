package helab.service.impl;

import helab.model.AdminInfo;
import helab.repository.AdminInfoRepository;
import helab.service.ServiceResult;
import helab.service.AdminInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
@Service
public class AdminInfoServiceImpl implements AdminInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminInfoServiceImpl.class);
    @Autowired
    private AdminInfoRepository adminInfoRepository;

    public ServiceResult login(String username, String passwd) {
        String passwdMd5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(passwd);
        AdminInfo adminInfo = adminInfoRepository.findOne(username);
        if (adminInfo == null) {
            return ServiceResult.failResult();
        }
        if (passwdMd5.equals(adminInfo.getPassword())) {
            return ServiceResult.successResult(adminInfo);
        }
        return ServiceResult.failResult();
    }


    public ServiceResult createAdminInfo(String username, String password, Long orgId) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            LOGGER.error("create admin error, username or password is null");
            return ServiceResult.failResult("ADMININFO_CREATE_USERNAMEORPASSWD_NULL");
        }
        AdminInfo adminInfoDB = adminInfoRepository.findOne(username);
        if (adminInfoDB != null) {
            return ServiceResult.failResult("ADMININFO_CREATE_USER_EXISTED");
        }
        String passwdMd5 = DigestUtils.md5Hex(password);
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setUserName(username);
        adminInfo.setPassword(passwdMd5);
        adminInfoRepository.save(adminInfo);
        return ServiceResult.successResult();
    }

    public ServiceResult resetPasswd(String username, String passwd) {
        AdminInfo adminInfo = adminInfoRepository.findOne(username);
        if (adminInfo == null) {
            return ServiceResult.failResult("ADMININFO_RESETPASSWD_USER_NOTFOUND");
        }
        if (StringUtils.isBlank(passwd)) {
            return ServiceResult.failResult("ADMININFO_RESETPASSWD_PASSWD_NULL");
        }
        String passwdMd5 = DigestUtils.md5Hex(passwd);
        adminInfo.setPassword(passwdMd5);
        adminInfoRepository.save(adminInfo);
        return ServiceResult.successResult();
    }
}
