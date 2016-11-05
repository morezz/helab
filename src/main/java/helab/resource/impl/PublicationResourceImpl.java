package helab.resource.impl;

import helab.resource.PublicationResource;
import helab.resource.ResourceResult;
import helab.service.PublicationService;
import helab.service.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public class PublicationResourceImpl implements PublicationResource {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PublicationResourceImpl.class);
    @Autowired
    private PublicationService publicationService;

    public ResourceResult list() {
        ServiceResult resourceResult = publicationService.list();
        return ResourceResult.successResult(resourceResult);
    }

    public ResourceResult create(String name, Attachment path, String desc, Attachment snapshot) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(desc)) {
            LOGGER.error("create publication error, name is null");
            return ServiceResult.failResult("PUBLICATION_CREATE_NAME_NULL");
        }
        if(StringUtils.isBlank(desc)){
            LOGGER.error("create publication error, desc is null");
            return ServiceResult.failResult("MEMBERINFO_CREATE_NAMECN_OR_NAMEEN_OR_TYPE_NULL");
        }

        try {
            fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
            if (fileName.lastIndexOf(".") != -1) {
                InputStream in = attachment.getDataHandler().getInputStream();
                helab.common.FileUtils.upload4Stream(AVATAR_PATH + fileName, in);
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
        ResourceResult resourceResult=publicationService.create(name,)
    }


}
