package helab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
@Table(name = "member_info")
@Entity
public class MemberInfo {

    @Id
    @Column(name = "mem_id")
    private Long memId;
    @Column(name = "cn_name")
    private String nameCN;
    @Column(name = "en_name")
    private String nameEN;
    @Column(name = "type")
    private String type;
    @Column(name = "email")
    private String email;
    @Column(name = "avatar")
    private String avatar;

    public Long getMemId() {
        return memId;
    }

    public void setMemId(Long memId) {
        this.memId = memId;
    }

    public String getNameCN() {
        return nameCN;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
