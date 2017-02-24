package pe.gob.congreso.didp.agenda.service;

import pe.gob.congreso.didp.agenda.domain.Comision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Comision.
 */
public interface ComisionService {

    /**
     * Save a comision.
     *
     * @param comision the entity to save
     * @return the persisted entity
     */
    Comision save(Comision comision);

    /**
     *  Get all the comisions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Comision> findAll(Pageable pageable);

    /**
     *  Get the "id" comision.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Comision findOne(Long id);

    /**
     *  Delete the "id" comision.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
