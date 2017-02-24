package pe.gob.congreso.didp.agenda.service;

import pe.gob.congreso.didp.agenda.domain.TextoSustitutorio;
import java.util.List;

/**
 * Service Interface for managing TextoSustitutorio.
 */
public interface TextoSustitutorioService {

    /**
     * Save a textoSustitutorio.
     *
     * @param textoSustitutorio the entity to save
     * @return the persisted entity
     */
    TextoSustitutorio save(TextoSustitutorio textoSustitutorio);

    /**
     *  Get all the textoSustitutorios.
     *  
     *  @return the list of entities
     */
    List<TextoSustitutorio> findAll();

    /**
     *  Get the "id" textoSustitutorio.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TextoSustitutorio findOne(Long id);

    /**
     *  Delete the "id" textoSustitutorio.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
