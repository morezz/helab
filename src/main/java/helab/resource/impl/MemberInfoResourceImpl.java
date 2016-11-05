package helab.resource.impl;

import com.google.gson.JsonObject;
import helab.common.CommonConfigBean;
import helab.common.FileUtils;
import helab.common.JsonUtils;
import helab.resource.MemberInfoResource;
import helab.resource.ResourceResult;
import helab.service.MemberInfoService;
import helab.service.ServiceResult;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public class MemberInfoResourceImpl implements MemberInfoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoResourceImpl.class);

    private static final String AVATAR_PATH = CommonConfigBean.getContextProperty("avatar.path");

    @Autowired
    private MemberInfoService memberInfoService;

    public ResourceResult listMember(String type) {
        ServiceResult serviceResult = memberInfoService.listMember(type);
        Map<String, Object> resultMap = (Map<String, Object>) serviceResult.getResult();
        return ResourceResult.successResult(resultMap);
    }


    public ResourceResult create(String nameCN, String nameEN, String type, String email, Attachment attachment) {
        LOGGER.debug("createMember, nameCN: {}, nameEN: {}, type: {}, email: {}", nameCN, nameEN, type, email);
        String fileName = attachment.getContentDisposition().getFilename();
        try {
            fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
            if (fileName.lastIndexOf(".") != -1) {
                InputStream in = attachment.getDataHandler().getInputStream();
                FileUtils.upload4Stream(AVATAR_PATH + fileName, in);
                ServiceResult serviceResult = memberInfoService.create(nameCN, nameEN, type, email, AVATAR_PATH + fileName);
                if (serviceResult.getCode() == 0) {
                    LOGGER.debug("createMember success, nameCN: {}, nameEN: {}, type: {}, email: {}, avatar: {}", nameCN, nameEN, type, email, fileName);
                    return ResourceResult.successResult();
                } else {
                    LOGGER.error("createMember error, nameCN: {}, nameEN: {}, type: {}, email: {}, avatar: {}", nameCN, nameEN, type, email, fileName);
                    return ResourceResult.failResult(serviceResult.getMessage());
                }
            } else {
                return ResourceResult.failResult("AVATAR_FILE_ERR");
            }
        } catch (Exception e) {
            // ignore
        }
        return ResourceResult.failResult("MEMBER_CREATE_FAILED");
    }


    public ResourceResult update(Long id, String nameCN, String nameEN, String type, String email, Attachment attachment) {
        LOGGER.debug("createMember, nameCN: {}, nameEN: {}, type: {}, email: {}", nameCN, nameEN, type, email);
        String fileName = attachment.getContentDisposition().getFilename();
        try {
            fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
            if (fileName.lastIndexOf(".") != -1) {
                InputStream in = attachment.getDataHandler().getInputStream();
                helab.common.FileUtils.upload4Stream(AVATAR_PATH + fileName, in);
            } else {
                return ResourceResult.failResult("AVATAR_FILE_ERR");
            }
            ServiceResult serviceResult = memberInfoService.update(id, nameCN, nameEN, type, email, AVATAR_PATH + fileName);
            if (serviceResult.getCode() == 0) {
                LOGGER.debug("updateMember success, id: {}, nameCN: {}, nameEN: {}, type: {}, email: {}, avatar: {}", id, nameCN, nameEN, type, email, fileName);
                return ResourceResult.successResult();
            } else {
                LOGGER.error("updateMember error, id: {}, nameCN: {}, nameEN: {}, type: {}, email: {}, avatar: {}", id, nameCN, nameEN, type, email, fileName);
                return ResourceResult.failResult(serviceResult.getMessage());
            }
        } catch (Exception e) {
            // ignore
        }
        return ResourceResult.failResult("MEMBER_UPDATE_FAILED");
    }


    public ResourceResult delete(Long id) {
        ServiceResult result = memberInfoService.delete(id);
        if (result.getCode() == 0) {
            return ResourceResult.successResult();
        } else {
            LOGGER.error(result.getMessage());
            return ResourceResult.failResult(result.getMessage());
        }
    }
}
