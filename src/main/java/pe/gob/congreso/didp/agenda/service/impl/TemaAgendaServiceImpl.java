package pe.gob.congreso.didp.agenda.service.impl;

import pe.gob.congreso.didp.agenda.service.TemaAgendaService;
import pe.gob.congreso.didp.agenda.domain.TemaAgenda;
import pe.gob.congreso.didp.agenda.repository.TemaAgendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TemaAgenda.
 */
@Service
@Transactional
public class TemaAgendaServiceImpl implements TemaAgendaService{

    private final Logger log = LoggerFactory.getLogger(TemaAgendaServiceImpl.class);
    
    private final TemaAgendaRepository temaAgendaRepository;

    public TemaAgendaServiceImpl(TemaAgendaRepository temaAgendaRepository) {
        this.temaAgendaRepository = temaAgendaRepository;
    }

    /**
     * Save a temaAgenda.
     *
     * @param temaAgenda the entity to save
     * @return the persisted entity
     */
    @Override
    public TemaAgenda save(TemaAgenda temaAgenda) {
        log.debug("Request to save TemaAgenda : {}", temaAgenda);
        TemaAgenda result = temaAgendaRepository.save(temaAgenda);
        return result;
    }

    /**
     *  Get all the temaAgenda.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TemaAgenda> findAll(Pageable pageable) {
        log.debug("Request to get all TemaAgenda");
        Page<TemaAgenda> result = temaAgendaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one temaAgenda by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TemaAgenda findOne(Long id) {
        log.debug("Request to get TemaAgenda : {}", id);
        TemaAgenda temaAgenda = temaAgendaRepository.findOneWithEagerRelationships(id);
        return temaAgenda;
    }

    /**
     *  Delete the  temaAgenda by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemaAgenda : {}", id);
        temaAgendaRepository.delete(id);
    }
}
