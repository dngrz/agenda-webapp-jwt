package pe.gob.congreso.didp.agenda.service.impl;

import pe.gob.congreso.didp.agenda.service.ComisionService;
import pe.gob.congreso.didp.agenda.domain.Comision;
import pe.gob.congreso.didp.agenda.repository.ComisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Comision.
 */
@Service
@Transactional
public class ComisionServiceImpl implements ComisionService{

    private final Logger log = LoggerFactory.getLogger(ComisionServiceImpl.class);
    
    private final ComisionRepository comisionRepository;

    public ComisionServiceImpl(ComisionRepository comisionRepository) {
        this.comisionRepository = comisionRepository;
    }

    /**
     * Save a comision.
     *
     * @param comision the entity to save
     * @return the persisted entity
     */
    @Override
    public Comision save(Comision comision) {
        log.debug("Request to save Comision : {}", comision);
        Comision result = comisionRepository.save(comision);
        return result;
    }

    /**
     *  Get all the comisions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Comision> findAll(Pageable pageable) {
        log.debug("Request to get all Comisions");
        Page<Comision> result = comisionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one comision by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Comision findOne(Long id) {
        log.debug("Request to get Comision : {}", id);
        Comision comision = comisionRepository.findOne(id);
        return comision;
    }

    /**
     *  Delete the  comision by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comision : {}", id);
        comisionRepository.delete(id);
    }
}
