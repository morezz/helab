package helab.repository;

import helab.model.ResearchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public interface ResearchRepository  extends JpaRepository<ResearchInfo, Long> {
}
