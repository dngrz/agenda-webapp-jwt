package pe.gob.congreso.didp.agenda.repository;

import pe.gob.congreso.didp.agenda.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
