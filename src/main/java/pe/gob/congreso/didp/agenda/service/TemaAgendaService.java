package pe.gob.congreso.didp.agenda.service;

import pe.gob.congreso.didp.agenda.domain.TemaAgenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TemaAgenda.
 */
public interface TemaAgendaService {

    /**
     * Save a temaAgenda.
     *
     * @param temaAgenda the entity to save
     * @return the persisted entity
     */
    TemaAgenda save(TemaAgenda temaAgenda);

    /**
     *  Get all the temaAgenda.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TemaAgenda> findAll(Pageable pageable);

    /**
     *  Get the "id" temaAgenda.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TemaAgenda findOne(Long id);

    /**
     *  Delete the "id" temaAgenda.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
