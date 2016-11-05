package helab.service.impl;

import helab.model.Publication;
import helab.repository.PublicationRepository;
import helab.service.PublicationService;
import helab.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */
@Service
public class PublicationServiceImpl implements PublicationService {


    @Autowired
    PublicationRepository publicationRepository;

    public ServiceResult list() {
        List<Publication> list = publicationRepository.findAll();
        return ServiceResult.successResult(list);
    }
}
