package pe.gob.congreso.didp.agenda.repository;

import pe.gob.congreso.didp.agenda.domain.Seccion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Seccion entity.
 */
@SuppressWarnings("unused")
public interface SeccionRepository extends JpaRepository<Seccion,Long> {

}
