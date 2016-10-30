package helab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */

@Table(name = "research")
@Entity
public class ResearchInfo {
    @Id
    @Column(name = "sem_id")
    private Long semId;
    @Column(name = "title")
    private String title;
    @Column(name = "speaker")
    private String speaker;
    @Column(name = "url")
    private String url;
    @Column(name = "sem_time")
    private Date semTime;

    public Long getSemId() {
        return semId;
    }

    public void setSemId(Long semId) {
        this.semId = semId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getSemTime() {
        return semTime;
    }

    public void setSemTime(Date semTime) {
        this.semTime = semTime;
    }
}
