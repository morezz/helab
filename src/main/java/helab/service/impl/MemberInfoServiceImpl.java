package helab.service.impl;

import helab.model.MemberInfo;
import helab.repository.MemberInfoRepository;
import helab.service.MemberInfoService;
import helab.service.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    public ServiceResult listMember(String type) {
        StringBuilder sql = new StringBuilder("SELECT infoList.* FROM member_info infoList");
        StringBuilder totalSql = new StringBuilder("SELECT COUNT(*) FROM member_info infoList");
        if (StringUtils.isNotBlank(type)) {
            sql.append(" WHERE infoList.type = " + type);
            totalSql.append(" WHERE infoList.type = " + type);
        }
        Long total = jdbcTemplate.queryForObject(totalSql.toString(), Long.class);
        List<MemberInfo> resultList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<MemberInfo>(MemberInfo.class));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", resultList);
        resultMap.put("total", total);
        return ServiceResult.successResult(resultMap);
    }


    public ServiceResult create(String nameCN, String nameEN, String type, String email, String avatarPath) {
        if (StringUtils.isBlank(nameCN) || StringUtils.isBlank(nameEN) || StringUtils.isBlank(type)) {
            LOGGER.error("create member error, nameCN or nameEN or type is null");
            return ServiceResult.failResult("MEMBERINFO_CREATE_NAMECN_OR_NAMEEN_OR_TYPE_NULL");
        }
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setNameCN(nameCN);
        memberInfo.setNameEN(nameEN);
        memberInfo.setType(type);
        memberInfo.setEmail(email);
        memberInfo.setAvatar(avatarPath);
        memberInfoRepository.save(memberInfo);
        return ServiceResult.successResult();
    }

    public ServiceResult update(Long id, String nameCN, String nameEN, String type, String email, String avatarPath) {
        if (StringUtils.isBlank(nameCN) || StringUtils.isBlank(nameEN) || StringUtils.isBlank(type)) {
            LOGGER.error("update member error, nameCN or nameEN or type is null");
            return ServiceResult.failResult("MEMBERINFO_CREATE_NAMECN_OR_NAMEEN_OR_TYPE_NULL");
        }
        MemberInfo memberInfo = memberInfoRepository.findOne(id);
        memberInfo.setNameCN(nameCN);
        memberInfo.setNameEN(nameEN);
        memberInfo.setType(type);
        memberInfo.setEmail(email);
        memberInfo.setAvatar(avatarPath);
        memberInfoRepository.save(memberInfo);
        return ServiceResult.successResult();
    }

    public ServiceResult delete(Long id) {
        MemberInfo memberInfo = memberInfoRepository.findOne(id);
        File file = new File(memberInfo.getAvatar());
        if (file.exists()) {
            file.delete();
        }
        memberInfoRepository.delete(memberInfo);
        return ServiceResult.successResult();

    }

}
