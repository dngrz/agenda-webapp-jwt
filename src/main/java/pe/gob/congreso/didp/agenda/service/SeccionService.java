package pe.gob.congreso.didp.agenda.service;

import pe.gob.congreso.didp.agenda.domain.Seccion;
import java.util.List;

/**
 * Service Interface for managing Seccion.
 */
public interface SeccionService {

    /**
     * Save a seccion.
     *
     * @param seccion the entity to save
     * @return the persisted entity
     */
    Seccion save(Seccion seccion);

    /**
     *  Get all the seccions.
     *  
     *  @return the list of entities
     */
    List<Seccion> findAll();

    /**
     *  Get the "id" seccion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Seccion findOne(Long id);

    /**
     *  Delete the "id" seccion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
