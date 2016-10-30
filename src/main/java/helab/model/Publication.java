package helab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
@Table(name = "publication")
@Entity
public class Publication {
    @Id
    @Column(name = "pub_id")
    private Long pubId;
    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "desc")
    private String desc;
    @Column(name = "snapshot")
    private String snapshot;


    public Long getPubId() {
        return pubId;
    }

    public void setPubId(Long pubId) {
        this.pubId = pubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }
}
