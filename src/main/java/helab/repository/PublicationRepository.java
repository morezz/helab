package helab.repository;

import helab.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
