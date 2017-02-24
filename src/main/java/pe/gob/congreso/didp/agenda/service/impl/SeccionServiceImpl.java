package pe.gob.congreso.didp.agenda.service.impl;

import pe.gob.congreso.didp.agenda.service.SeccionService;
import pe.gob.congreso.didp.agenda.domain.Seccion;
import pe.gob.congreso.didp.agenda.repository.SeccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Seccion.
 */
@Service
@Transactional
public class SeccionServiceImpl implements SeccionService{

    private final Logger log = LoggerFactory.getLogger(SeccionServiceImpl.class);
    
    private final SeccionRepository seccionRepository;

    public SeccionServiceImpl(SeccionRepository seccionRepository) {
        this.seccionRepository = seccionRepository;
    }

    /**
     * Save a seccion.
     *
     * @param seccion the entity to save
     * @return the persisted entity
     */
    @Override
    public Seccion save(Seccion seccion) {
        log.debug("Request to save Seccion : {}", seccion);
        Seccion result = seccionRepository.save(seccion);
        return result;
    }

    /**
     *  Get all the seccions.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Seccion> findAll() {
        log.debug("Request to get all Seccions");
        List<Seccion> result = seccionRepository.findAll();

        return result;
    }

    /**
     *  Get one seccion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Seccion findOne(Long id) {
        log.debug("Request to get Seccion : {}", id);
        Seccion seccion = seccionRepository.findOne(id);
        return seccion;
    }

    /**
     *  Delete the  seccion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seccion : {}", id);
        seccionRepository.delete(id);
    }
}
