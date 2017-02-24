package pe.gob.congreso.didp.agenda.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.gob.congreso.didp.agenda.domain.TemaAgenda;
import pe.gob.congreso.didp.agenda.service.TemaAgendaService;
import pe.gob.congreso.didp.agenda.web.rest.util.HeaderUtil;
import pe.gob.congreso.didp.agenda.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TemaAgenda.
 */
@RestController
@RequestMapping("/api")
public class TemaAgendaResource {

    private final Logger log = LoggerFactory.getLogger(TemaAgendaResource.class);

    private static final String ENTITY_NAME = "temaAgenda";
        
    private final TemaAgendaService temaAgendaService;

    public TemaAgendaResource(TemaAgendaService temaAgendaService) {
        this.temaAgendaService = temaAgendaService;
    }

    /**
     * POST  /tema-agenda : Create a new temaAgenda.
     *
     * @param temaAgenda the temaAgenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new temaAgenda, or with status 400 (Bad Request) if the temaAgenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tema-agenda")
    @Timed
    public ResponseEntity<TemaAgenda> createTemaAgenda(@Valid @RequestBody TemaAgenda temaAgenda) throws URISyntaxException {
        log.debug("REST request to save TemaAgenda : {}", temaAgenda);
        if (temaAgenda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new temaAgenda cannot already have an ID")).body(null);
        }
        TemaAgenda result = temaAgendaService.save(temaAgenda);
        return ResponseEntity.created(new URI("/api/tema-agenda/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tema-agenda : Updates an existing temaAgenda.
     *
     * @param temaAgenda the temaAgenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated temaAgenda,
     * or with status 400 (Bad Request) if the temaAgenda is not valid,
     * or with status 500 (Internal Server Error) if the temaAgenda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tema-agenda")
    @Timed
    public ResponseEntity<TemaAgenda> updateTemaAgenda(@Valid @RequestBody TemaAgenda temaAgenda) throws URISyntaxException {
        log.debug("REST request to update TemaAgenda : {}", temaAgenda);
        if (temaAgenda.getId() == null) {
            return createTemaAgenda(temaAgenda);
        }
        TemaAgenda result = temaAgendaService.save(temaAgenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, temaAgenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tema-agenda : get all the temaAgenda.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of temaAgenda in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tema-agenda")
    @Timed
    public ResponseEntity<List<TemaAgenda>> getAllTemaAgenda(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TemaAgenda");
        Page<TemaAgenda> page = temaAgendaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tema-agenda");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tema-agenda/:id : get the "id" temaAgenda.
     *
     * @param id the id of the temaAgenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the temaAgenda, or with status 404 (Not Found)
     */
    @GetMapping("/tema-agenda/{id}")
    @Timed
    public ResponseEntity<TemaAgenda> getTemaAgenda(@PathVariable Long id) {
        log.debug("REST request to get TemaAgenda : {}", id);
        TemaAgenda temaAgenda = temaAgendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(temaAgenda));
    }

    /**
     * DELETE  /tema-agenda/:id : delete the "id" temaAgenda.
     *
     * @param id the id of the temaAgenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tema-agenda/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemaAgenda(@PathVariable Long id) {
        log.debug("REST request to delete TemaAgenda : {}", id);
        temaAgendaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
