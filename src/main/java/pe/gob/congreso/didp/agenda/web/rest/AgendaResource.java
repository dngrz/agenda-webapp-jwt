package pe.gob.congreso.didp.agenda.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.gob.congreso.didp.agenda.domain.Agenda;
import pe.gob.congreso.didp.agenda.service.AgendaService;
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
 * REST controller for managing Agenda.
 */
@RestController
@RequestMapping("/api")
public class AgendaResource {

    private final Logger log = LoggerFactory.getLogger(AgendaResource.class);

    private static final String ENTITY_NAME = "agenda";
        
    private final AgendaService agendaService;

    public AgendaResource(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * POST  /agenda : Create a new agenda.
     *
     * @param agenda the agenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agenda, or with status 400 (Bad Request) if the agenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agenda")
    @Timed
    public ResponseEntity<Agenda> createAgenda(@Valid @RequestBody Agenda agenda) throws URISyntaxException {
        log.debug("REST request to save Agenda : {}", agenda);
        if (agenda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new agenda cannot already have an ID")).body(null);
        }
        Agenda result = agendaService.save(agenda);
        return ResponseEntity.created(new URI("/api/agenda/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agenda : Updates an existing agenda.
     *
     * @param agenda the agenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agenda,
     * or with status 400 (Bad Request) if the agenda is not valid,
     * or with status 500 (Internal Server Error) if the agenda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agenda")
    @Timed
    public ResponseEntity<Agenda> updateAgenda(@Valid @RequestBody Agenda agenda) throws URISyntaxException {
        log.debug("REST request to update Agenda : {}", agenda);
        if (agenda.getId() == null) {
            return createAgenda(agenda);
        }
        Agenda result = agendaService.save(agenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agenda : get all the agenda.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agenda in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/agenda")
    @Timed
    public ResponseEntity<List<Agenda>> getAllAgenda(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Agenda");
        Page<Agenda> page = agendaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agenda");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agenda/:id : get the "id" agenda.
     *
     * @param id the id of the agenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agenda, or with status 404 (Not Found)
     */
    @GetMapping("/agenda/{id}")
    @Timed
    public ResponseEntity<Agenda> getAgenda(@PathVariable Long id) {
        log.debug("REST request to get Agenda : {}", id);
        Agenda agenda = agendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agenda));
    }

    /**
     * DELETE  /agenda/:id : delete the "id" agenda.
     *
     * @param id the id of the agenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agenda/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {
        log.debug("REST request to delete Agenda : {}", id);
        agendaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
