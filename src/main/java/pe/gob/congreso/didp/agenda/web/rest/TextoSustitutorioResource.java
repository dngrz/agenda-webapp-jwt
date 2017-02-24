package pe.gob.congreso.didp.agenda.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.gob.congreso.didp.agenda.domain.TextoSustitutorio;
import pe.gob.congreso.didp.agenda.service.TextoSustitutorioService;
import pe.gob.congreso.didp.agenda.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TextoSustitutorio.
 */
@RestController
@RequestMapping("/api")
public class TextoSustitutorioResource {

    private final Logger log = LoggerFactory.getLogger(TextoSustitutorioResource.class);

    private static final String ENTITY_NAME = "textoSustitutorio";
        
    private final TextoSustitutorioService textoSustitutorioService;

    public TextoSustitutorioResource(TextoSustitutorioService textoSustitutorioService) {
        this.textoSustitutorioService = textoSustitutorioService;
    }

    /**
     * POST  /texto-sustitutorios : Create a new textoSustitutorio.
     *
     * @param textoSustitutorio the textoSustitutorio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new textoSustitutorio, or with status 400 (Bad Request) if the textoSustitutorio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/texto-sustitutorios")
    @Timed
    public ResponseEntity<TextoSustitutorio> createTextoSustitutorio(@Valid @RequestBody TextoSustitutorio textoSustitutorio) throws URISyntaxException {
        log.debug("REST request to save TextoSustitutorio : {}", textoSustitutorio);
        if (textoSustitutorio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new textoSustitutorio cannot already have an ID")).body(null);
        }
        TextoSustitutorio result = textoSustitutorioService.save(textoSustitutorio);
        return ResponseEntity.created(new URI("/api/texto-sustitutorios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /texto-sustitutorios : Updates an existing textoSustitutorio.
     *
     * @param textoSustitutorio the textoSustitutorio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated textoSustitutorio,
     * or with status 400 (Bad Request) if the textoSustitutorio is not valid,
     * or with status 500 (Internal Server Error) if the textoSustitutorio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/texto-sustitutorios")
    @Timed
    public ResponseEntity<TextoSustitutorio> updateTextoSustitutorio(@Valid @RequestBody TextoSustitutorio textoSustitutorio) throws URISyntaxException {
        log.debug("REST request to update TextoSustitutorio : {}", textoSustitutorio);
        if (textoSustitutorio.getId() == null) {
            return createTextoSustitutorio(textoSustitutorio);
        }
        TextoSustitutorio result = textoSustitutorioService.save(textoSustitutorio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, textoSustitutorio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /texto-sustitutorios : get all the textoSustitutorios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of textoSustitutorios in body
     */
    @GetMapping("/texto-sustitutorios")
    @Timed
    public List<TextoSustitutorio> getAllTextoSustitutorios() {
        log.debug("REST request to get all TextoSustitutorios");
        return textoSustitutorioService.findAll();
    }

    /**
     * GET  /texto-sustitutorios/:id : get the "id" textoSustitutorio.
     *
     * @param id the id of the textoSustitutorio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the textoSustitutorio, or with status 404 (Not Found)
     */
    @GetMapping("/texto-sustitutorios/{id}")
    @Timed
    public ResponseEntity<TextoSustitutorio> getTextoSustitutorio(@PathVariable Long id) {
        log.debug("REST request to get TextoSustitutorio : {}", id);
        TextoSustitutorio textoSustitutorio = textoSustitutorioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(textoSustitutorio));
    }

    /**
     * DELETE  /texto-sustitutorios/:id : delete the "id" textoSustitutorio.
     *
     * @param id the id of the textoSustitutorio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/texto-sustitutorios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTextoSustitutorio(@PathVariable Long id) {
        log.debug("REST request to delete TextoSustitutorio : {}", id);
        textoSustitutorioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
