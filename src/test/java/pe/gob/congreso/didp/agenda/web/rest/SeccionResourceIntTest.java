package pe.gob.congreso.didp.agenda.web.rest;

import pe.gob.congreso.didp.agenda.AgendaApp;

import pe.gob.congreso.didp.agenda.domain.Seccion;
import pe.gob.congreso.didp.agenda.repository.SeccionRepository;
import pe.gob.congreso.didp.agenda.service.SeccionService;
import pe.gob.congreso.didp.agenda.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeccionResource REST controller.
 *
 * @see SeccionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgendaApp.class)
public class SeccionResourceIntTest {

    private static final String DEFAULT_SUBTITULO = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITULO = "BBBBBBBBBB";

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private SeccionService seccionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeccionMockMvc;

    private Seccion seccion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeccionResource seccionResource = new SeccionResource(seccionService);
        this.restSeccionMockMvc = MockMvcBuilders.standaloneSetup(seccionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seccion createEntity(EntityManager em) {
        Seccion seccion = new Seccion()
                .subtitulo(DEFAULT_SUBTITULO);
        return seccion;
    }

    @Before
    public void initTest() {
        seccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccion() throws Exception {
        int databaseSizeBeforeCreate = seccionRepository.findAll().size();

        // Create the Seccion

        restSeccionMockMvc.perform(post("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isCreated());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeCreate + 1);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getSubtitulo()).isEqualTo(DEFAULT_SUBTITULO);
    }

    @Test
    @Transactional
    public void createSeccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionRepository.findAll().size();

        // Create the Seccion with an existing ID
        Seccion existingSeccion = new Seccion();
        existingSeccion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionMockMvc.perform(post("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSeccion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubtituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = seccionRepository.findAll().size();
        // set the field null
        seccion.setSubtitulo(null);

        // Create the Seccion, which fails.

        restSeccionMockMvc.perform(post("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isBadRequest());

        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeccions() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get all the seccionList
        restSeccionMockMvc.perform(get("/api/seccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].subtitulo").value(hasItem(DEFAULT_SUBTITULO.toString())));
    }

    @Test
    @Transactional
    public void getSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get the seccion
        restSeccionMockMvc.perform(get("/api/seccions/{id}", seccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seccion.getId().intValue()))
            .andExpect(jsonPath("$.subtitulo").value(DEFAULT_SUBTITULO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeccion() throws Exception {
        // Get the seccion
        restSeccionMockMvc.perform(get("/api/seccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccion() throws Exception {
        // Initialize the database
        seccionService.save(seccion);

        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Update the seccion
        Seccion updatedSeccion = seccionRepository.findOne(seccion.getId());
        updatedSeccion
                .subtitulo(UPDATED_SUBTITULO);

        restSeccionMockMvc.perform(put("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeccion)))
            .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getSubtitulo()).isEqualTo(UPDATED_SUBTITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Create the Seccion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeccionMockMvc.perform(put("/api/seccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isCreated());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeccion() throws Exception {
        // Initialize the database
        seccionService.save(seccion);

        int databaseSizeBeforeDelete = seccionRepository.findAll().size();

        // Get the seccion
        restSeccionMockMvc.perform(delete("/api/seccions/{id}", seccion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seccion.class);
    }
}
