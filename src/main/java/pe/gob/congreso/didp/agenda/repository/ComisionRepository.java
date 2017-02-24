package pe.gob.congreso.didp.agenda.repository;

import pe.gob.congreso.didp.agenda.domain.Comision;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Comision entity.
 */
@SuppressWarnings("unused")
public interface ComisionRepository extends JpaRepository<Comision,Long> {

}
