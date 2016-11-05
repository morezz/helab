package helab.service;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public interface MemberInfoService {

    ServiceResult listMember(String type);

    ServiceResult create(String nameCN, String nameEN, String type, String email, String avatarPath);

    ServiceResult update(Long id, String nameCN, String nameEN, String type, String email, String avatarPath);

    ServiceResult delete(Long id);

}
