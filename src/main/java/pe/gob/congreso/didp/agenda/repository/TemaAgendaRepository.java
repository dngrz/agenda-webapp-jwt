package pe.gob.congreso.didp.agenda.repository;

import pe.gob.congreso.didp.agenda.domain.TemaAgenda;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TemaAgenda entity.
 */
@SuppressWarnings("unused")
public interface TemaAgendaRepository extends JpaRepository<TemaAgenda,Long> {

    @Query("select distinct temaAgenda from TemaAgenda temaAgenda left join fetch temaAgenda.seccions left join fetch temaAgenda.comisions")
    List<TemaAgenda> findAllWithEagerRelationships();

    @Query("select temaAgenda from TemaAgenda temaAgenda left join fetch temaAgenda.seccions left join fetch temaAgenda.comisions where temaAgenda.id =:id")
    TemaAgenda findOneWithEagerRelationships(@Param("id") Long id);

}
