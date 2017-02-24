package pe.gob.congreso.didp.agenda.repository;

import pe.gob.congreso.didp.agenda.domain.Agenda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Agenda entity.
 */
@SuppressWarnings("unused")
public interface AgendaRepository extends JpaRepository<Agenda,Long> {

    @Query("select agenda from Agenda agenda where agenda.user.login = ?#{principal.username}")
    List<Agenda> findByUserIsCurrentUser();

}
