package pe.gob.congreso.didp.agenda.service.impl;

import pe.gob.congreso.didp.agenda.service.AgendaService;
import pe.gob.congreso.didp.agenda.domain.Agenda;
import pe.gob.congreso.didp.agenda.repository.AgendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Agenda.
 */
@Service
@Transactional
public class AgendaServiceImpl implements AgendaService{

    private final Logger log = LoggerFactory.getLogger(AgendaServiceImpl.class);
    
    private final AgendaRepository agendaRepository;

    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    /**
     * Save a agenda.
     *
     * @param agenda the entity to save
     * @return the persisted entity
     */
    @Override
    public Agenda save(Agenda agenda) {
        log.debug("Request to save Agenda : {}", agenda);
        Agenda result = agendaRepository.save(agenda);
        return result;
    }

    /**
     *  Get all the agenda.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Agenda> findAll(Pageable pageable) {
        log.debug("Request to get all Agenda");
        Page<Agenda> result = agendaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one agenda by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Agenda findOne(Long id) {
        log.debug("Request to get Agenda : {}", id);
        Agenda agenda = agendaRepository.findOne(id);
        return agenda;
    }

    /**
     *  Delete the  agenda by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agenda : {}", id);
        agendaRepository.delete(id);
    }
}
