package helab.resource.impl;

import com.google.gson.JsonObject;
import helab.common.JsonUtils;
import helab.resource.MemberInfoResource;
import helab.resource.ResourceResult;
import helab.service.MemberInfoService;
import helab.service.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public class MemberInfoResourceImpl implements MemberInfoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoResourceImpl.class);

    @Autowired
    private MemberInfoService memberInfoService;

    public ResourceResult listMember(String type) {
        ServiceResult serviceResult = memberInfoService.listMember(type);
        Map<String, Object> resultMap = (Map<String, Object>) serviceResult.getResult();
        return ResourceResult.successResult(resultMap);
    }


    public ResourceResult create(String body) {
        LOGGER.debug("createMember, body: {}", body);
        JsonObject jsonObject = JsonUtils.String2JsonObject(body);
        String nameCN = jsonObject.get("cn_name").getAsString();
        String nameEN = jsonObject.get("en_name").getAsString();
        String type = jsonObject.get("type").getAsString();
        String email = jsonObject.get("email").getAsString();
        ServiceResult serviceResult = memberInfoService.create(nameCN, nameEN, type, email);
        if (serviceResult.getCode() == 0) {
            LOGGER.debug("createMember success, nameCN: {}, nameEN: {}, type: {}, email: {}", nameCN, nameEN, type, email);
            return ResourceResult.successResult();
        } else {
            LOGGER.error("createMember error, nameCN: {}, nameEN: {}, type: {}, email: {}", nameCN, nameEN, type, email);
            return ResourceResult.failResult(serviceResult.getMessage());
        }
    }


    public ResourceResult update(Long id, String body) {
        LOGGER.debug("updateMember, body: {}", body);
        JsonObject jsonObject = JsonUtils.String2JsonObject(body);
        String nameCN = jsonObject.get("cn_name").getAsString();
        String nameEN = jsonObject.get("en_name").getAsString();
        String type = jsonObject.get("type").getAsString();
        String email = jsonObject.get("email").getAsString();
        ServiceResult serviceResult = memberInfoService.update(id, nameCN, nameEN, type, email);
        if (serviceResult.getCode() == 0) {
            LOGGER.debug("updateMember success, id: {}, nameCN: {}, nameEN: {}, type: {}, email: {}", id, nameCN, nameEN, type, email);
            return ResourceResult.successResult();
        } else {
            LOGGER.error("updateMember error, id: {}, nameCN: {}, nameEN: {}, type: {}, email: {}", id, nameCN, nameEN, type, email);
            return ResourceResult.failResult(serviceResult.getMessage());
        }
    }
}
