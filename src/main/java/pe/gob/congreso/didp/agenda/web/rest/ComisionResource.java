package pe.gob.congreso.didp.agenda.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.gob.congreso.didp.agenda.domain.Comision;
import pe.gob.congreso.didp.agenda.service.ComisionService;
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
 * REST controller for managing Comision.
 */
@RestController
@RequestMapping("/api")
public class ComisionResource {

    private final Logger log = LoggerFactory.getLogger(ComisionResource.class);

    private static final String ENTITY_NAME = "comision";
        
    private final ComisionService comisionService;

    public ComisionResource(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    /**
     * POST  /comisions : Create a new comision.
     *
     * @param comision the comision to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comision, or with status 400 (Bad Request) if the comision has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comisions")
    @Timed
    public ResponseEntity<Comision> createComision(@Valid @RequestBody Comision comision) throws URISyntaxException {
        log.debug("REST request to save Comision : {}", comision);
        if (comision.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comision cannot already have an ID")).body(null);
        }
        Comision result = comisionService.save(comision);
        return ResponseEntity.created(new URI("/api/comisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comisions : Updates an existing comision.
     *
     * @param comision the comision to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comision,
     * or with status 400 (Bad Request) if the comision is not valid,
     * or with status 500 (Internal Server Error) if the comision couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comisions")
    @Timed
    public ResponseEntity<Comision> updateComision(@Valid @RequestBody Comision comision) throws URISyntaxException {
        log.debug("REST request to update Comision : {}", comision);
        if (comision.getId() == null) {
            return createComision(comision);
        }
        Comision result = comisionService.save(comision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comision.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comisions : get all the comisions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comisions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/comisions")
    @Timed
    public ResponseEntity<List<Comision>> getAllComisions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Comisions");
        Page<Comision> page = comisionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comisions/:id : get the "id" comision.
     *
     * @param id the id of the comision to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comision, or with status 404 (Not Found)
     */
    @GetMapping("/comisions/{id}")
    @Timed
    public ResponseEntity<Comision> getComision(@PathVariable Long id) {
        log.debug("REST request to get Comision : {}", id);
        Comision comision = comisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comision));
    }

    /**
     * DELETE  /comisions/:id : delete the "id" comision.
     *
     * @param id the id of the comision to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comisions/{id}")
    @Timed
    public ResponseEntity<Void> deleteComision(@PathVariable Long id) {
        log.debug("REST request to delete Comision : {}", id);
        comisionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
