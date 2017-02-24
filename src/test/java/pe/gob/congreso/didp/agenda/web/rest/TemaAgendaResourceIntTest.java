package pe.gob.congreso.didp.agenda.web.rest;

import pe.gob.congreso.didp.agenda.AgendaApp;

import pe.gob.congreso.didp.agenda.domain.TemaAgenda;
import pe.gob.congreso.didp.agenda.repository.TemaAgendaRepository;
import pe.gob.congreso.didp.agenda.service.TemaAgendaService;
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

import pe.gob.congreso.didp.agenda.domain.enumeration.IndicadorTema;
import pe.gob.congreso.didp.agenda.domain.enumeration.EstadoTema;
/**
 * Test class for the TemaAgendaResource REST controller.
 *
 * @see TemaAgendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgendaApp.class)
public class TemaAgendaResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final IndicadorTema DEFAULT_TEMA_ACTIVO = IndicadorTema.ACTIVO;
    private static final IndicadorTema UPDATED_TEMA_ACTIVO = IndicadorTema.INACTIVO;

    private static final EstadoTema DEFAULT_ESTADO_TEMA = EstadoTema.PENDIENTE;
    private static final EstadoTema UPDATED_ESTADO_TEMA = EstadoTema.ACTIVO;

    @Autowired
    private TemaAgendaRepository temaAgendaRepository;

    @Autowired
    private TemaAgendaService temaAgendaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemaAgendaMockMvc;

    private TemaAgenda temaAgenda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TemaAgendaResource temaAgendaResource = new TemaAgendaResource(temaAgendaService);
        this.restTemaAgendaMockMvc = MockMvcBuilders.standaloneSetup(temaAgendaResource)
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
    public static TemaAgenda createEntity(EntityManager em) {
        TemaAgenda temaAgenda = new TemaAgenda()
                .titulo(DEFAULT_TITULO)
                .numero(DEFAULT_NUMERO)
                .contenido(DEFAULT_CONTENIDO)
                .url(DEFAULT_URL)
                .temaActivo(DEFAULT_TEMA_ACTIVO)
                .estadoTema(DEFAULT_ESTADO_TEMA);
        return temaAgenda;
    }

    @Before
    public void initTest() {
        temaAgenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemaAgenda() throws Exception {
        int databaseSizeBeforeCreate = temaAgendaRepository.findAll().size();

        // Create the TemaAgenda

        restTemaAgendaMockMvc.perform(post("/api/tema-agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaAgenda)))
            .andExpect(status().isCreated());

        // Validate the TemaAgenda in the database
        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeCreate + 1);
        TemaAgenda testTemaAgenda = temaAgendaList.get(temaAgendaList.size() - 1);
        assertThat(testTemaAgenda.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testTemaAgenda.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTemaAgenda.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testTemaAgenda.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTemaAgenda.getTemaActivo()).isEqualTo(DEFAULT_TEMA_ACTIVO);
        assertThat(testTemaAgenda.getEstadoTema()).isEqualTo(DEFAULT_ESTADO_TEMA);
    }

    @Test
    @Transactional
    public void createTemaAgendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temaAgendaRepository.findAll().size();

        // Create the TemaAgenda with an existing ID
        TemaAgenda existingTemaAgenda = new TemaAgenda();
        existingTemaAgenda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemaAgendaMockMvc.perform(post("/api/tema-agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTemaAgenda)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = temaAgendaRepository.findAll().size();
        // set the field null
        temaAgenda.setTitulo(null);

        // Create the TemaAgenda, which fails.

        restTemaAgendaMockMvc.perform(post("/api/tema-agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaAgenda)))
            .andExpect(status().isBadRequest());

        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContenidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = temaAgendaRepository.findAll().size();
        // set the field null
        temaAgenda.setContenido(null);

        // Create the TemaAgenda, which fails.

        restTemaAgendaMockMvc.perform(post("/api/tema-agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaAgenda)))
            .andExpect(status().isBadRequest());

        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemaAgenda() throws Exception {
        // Initialize the database
        temaAgendaRepository.saveAndFlush(temaAgenda);

        // Get all the temaAgendaList
        restTemaAgendaMockMvc.perform(get("/api/tema-agenda?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temaAgenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].temaActivo").value(hasItem(DEFAULT_TEMA_ACTIVO.toString())))
            .andExpect(jsonPath("$.[*].estadoTema").value(hasItem(DEFAULT_ESTADO_TEMA.toString())));
    }

    @Test
    @Transactional
    public void getTemaAgenda() throws Exception {
        // Initialize the database
        temaAgendaRepository.saveAndFlush(temaAgenda);

        // Get the temaAgenda
        restTemaAgendaMockMvc.perform(get("/api/tema-agenda/{id}", temaAgenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(temaAgenda.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.temaActivo").value(DEFAULT_TEMA_ACTIVO.toString()))
            .andExpect(jsonPath("$.estadoTema").value(DEFAULT_ESTADO_TEMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTemaAgenda() throws Exception {
        // Get the temaAgenda
        restTemaAgendaMockMvc.perform(get("/api/tema-agenda/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemaAgenda() throws Exception {
        // Initialize the database
        temaAgendaService.save(temaAgenda);

        int databaseSizeBeforeUpdate = temaAgendaRepository.findAll().size();

        // Update the temaAgenda
        TemaAgenda updatedTemaAgenda = temaAgendaRepository.findOne(temaAgenda.getId());
        updatedTemaAgenda
                .titulo(UPDATED_TITULO)
                .numero(UPDATED_NUMERO)
                .contenido(UPDATED_CONTENIDO)
                .url(UPDATED_URL)
                .temaActivo(UPDATED_TEMA_ACTIVO)
                .estadoTema(UPDATED_ESTADO_TEMA);

        restTemaAgendaMockMvc.perform(put("/api/tema-agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemaAgenda)))
            .andExpect(status().isOk());

        // Validate the TemaAgenda in the database
        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeUpdate);
        TemaAgenda testTemaAgenda = temaAgendaList.get(temaAgendaList.size() - 1);
        assertThat(testTemaAgenda.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testTemaAgenda.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTemaAgenda.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testTemaAgenda.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTemaAgenda.getTemaActivo()).isEqualTo(UPDATED_TEMA_ACTIVO);
        assertThat(testTemaAgenda.getEstadoTema()).isEqualTo(UPDATED_ESTADO_TEMA);
    }

    @Test
    @Transactional
    public void updateNonExistingTemaAgenda() throws Exception {
        int databaseSizeBeforeUpdate = temaAgendaRepository.findAll().size();

        // Create the TemaAgenda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemaAgendaMockMvc.perform(put("/api/tema-agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaAgenda)))
            .andExpect(status().isCreated());

        // Validate the TemaAgenda in the database
        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTemaAgenda() throws Exception {
        // Initialize the database
        temaAgendaService.save(temaAgenda);

        int databaseSizeBeforeDelete = temaAgendaRepository.findAll().size();

        // Get the temaAgenda
        restTemaAgendaMockMvc.perform(delete("/api/tema-agenda/{id}", temaAgenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TemaAgenda> temaAgendaList = temaAgendaRepository.findAll();
        assertThat(temaAgendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemaAgenda.class);
    }
}
