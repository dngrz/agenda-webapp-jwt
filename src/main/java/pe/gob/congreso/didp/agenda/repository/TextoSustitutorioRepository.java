package pe.gob.congreso.didp.agenda.repository;

import pe.gob.congreso.didp.agenda.domain.TextoSustitutorio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TextoSustitutorio entity.
 */
@SuppressWarnings("unused")
public interface TextoSustitutorioRepository extends JpaRepository<TextoSustitutorio,Long> {

}
