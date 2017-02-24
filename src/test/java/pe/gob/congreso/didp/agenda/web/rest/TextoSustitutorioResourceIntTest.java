package pe.gob.congreso.didp.agenda.web.rest;

import pe.gob.congreso.didp.agenda.AgendaApp;

import pe.gob.congreso.didp.agenda.domain.TextoSustitutorio;
import pe.gob.congreso.didp.agenda.repository.TextoSustitutorioRepository;
import pe.gob.congreso.didp.agenda.service.TextoSustitutorioService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static pe.gob.congreso.didp.agenda.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TextoSustitutorioResource REST controller.
 *
 * @see TextoSustitutorioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgendaApp.class)
public class TextoSustitutorioResourceIntTest {

    private static final String DEFAULT_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_ADJUNTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ADJUNTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private TextoSustitutorioRepository textoSustitutorioRepository;

    @Autowired
    private TextoSustitutorioService textoSustitutorioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTextoSustitutorioMockMvc;

    private TextoSustitutorio textoSustitutorio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TextoSustitutorioResource textoSustitutorioResource = new TextoSustitutorioResource(textoSustitutorioService);
        this.restTextoSustitutorioMockMvc = MockMvcBuilders.standaloneSetup(textoSustitutorioResource)
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
    public static TextoSustitutorio createEntity(EntityManager em) {
        TextoSustitutorio textoSustitutorio = new TextoSustitutorio()
                .archivo(DEFAULT_ARCHIVO)
                .fechaAdjunto(DEFAULT_FECHA_ADJUNTO)
                .url(DEFAULT_URL);
        return textoSustitutorio;
    }

    @Before
    public void initTest() {
        textoSustitutorio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTextoSustitutorio() throws Exception {
        int databaseSizeBeforeCreate = textoSustitutorioRepository.findAll().size();

        // Create the TextoSustitutorio

        restTextoSustitutorioMockMvc.perform(post("/api/texto-sustitutorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textoSustitutorio)))
            .andExpect(status().isCreated());

        // Validate the TextoSustitutorio in the database
        List<TextoSustitutorio> textoSustitutorioList = textoSustitutorioRepository.findAll();
        assertThat(textoSustitutorioList).hasSize(databaseSizeBeforeCreate + 1);
        TextoSustitutorio testTextoSustitutorio = textoSustitutorioList.get(textoSustitutorioList.size() - 1);
        assertThat(testTextoSustitutorio.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testTextoSustitutorio.getFechaAdjunto()).isEqualTo(DEFAULT_FECHA_ADJUNTO);
        assertThat(testTextoSustitutorio.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createTextoSustitutorioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = textoSustitutorioRepository.findAll().size();

        // Create the TextoSustitutorio with an existing ID
        TextoSustitutorio existingTextoSustitutorio = new TextoSustitutorio();
        existingTextoSustitutorio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextoSustitutorioMockMvc.perform(post("/api/texto-sustitutorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTextoSustitutorio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TextoSustitutorio> textoSustitutorioList = textoSustitutorioRepository.findAll();
        assertThat(textoSustitutorioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkArchivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = textoSustitutorioRepository.findAll().size();
        // set the field null
        textoSustitutorio.setArchivo(null);

        // Create the TextoSustitutorio, which fails.

        restTextoSustitutorioMockMvc.perform(post("/api/texto-sustitutorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textoSustitutorio)))
            .andExpect(status().isBadRequest());

        List<TextoSustitutorio> textoSustitutorioList = textoSustitutorioRepository.findAll();
        assertThat(textoSustitutorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTextoSustitutorios() throws Exception {
        // Initialize the database
        textoSustitutorioRepository.saveAndFlush(textoSustitutorio);

        // Get all the textoSustitutorioList
        restTextoSustitutorioMockMvc.perform(get("/api/texto-sustitutorios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(textoSustitutorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(DEFAULT_ARCHIVO.toString())))
            .andExpect(jsonPath("$.[*].fechaAdjunto").value(hasItem(sameInstant(DEFAULT_FECHA_ADJUNTO))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getTextoSustitutorio() throws Exception {
        // Initialize the database
        textoSustitutorioRepository.saveAndFlush(textoSustitutorio);

        // Get the textoSustitutorio
        restTextoSustitutorioMockMvc.perform(get("/api/texto-sustitutorios/{id}", textoSustitutorio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(textoSustitutorio.getId().intValue()))
            .andExpect(jsonPath("$.archivo").value(DEFAULT_ARCHIVO.toString()))
            .andExpect(jsonPath("$.fechaAdjunto").value(sameInstant(DEFAULT_FECHA_ADJUNTO)))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTextoSustitutorio() throws Exception {
        // Get the textoSustitutorio
        restTextoSustitutorioMockMvc.perform(get("/api/texto-sustitutorios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTextoSustitutorio() throws Exception {
        // Initialize the database
        textoSustitutorioService.save(textoSustitutorio);

        int databaseSizeBeforeUpdate = textoSustitutorioRepository.findAll().size();

        // Update the textoSustitutorio
        TextoSustitutorio updatedTextoSustitutorio = textoSustitutorioRepository.findOne(textoSustitutorio.getId());
        updatedTextoSustitutorio
                .archivo(UPDATED_ARCHIVO)
                .fechaAdjunto(UPDATED_FECHA_ADJUNTO)
                .url(UPDATED_URL);

        restTextoSustitutorioMockMvc.perform(put("/api/texto-sustitutorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTextoSustitutorio)))
            .andExpect(status().isOk());

        // Validate the TextoSustitutorio in the database
        List<TextoSustitutorio> textoSustitutorioList = textoSustitutorioRepository.findAll();
        assertThat(textoSustitutorioList).hasSize(databaseSizeBeforeUpdate);
        TextoSustitutorio testTextoSustitutorio = textoSustitutorioList.get(textoSustitutorioList.size() - 1);
        assertThat(testTextoSustitutorio.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testTextoSustitutorio.getFechaAdjunto()).isEqualTo(UPDATED_FECHA_ADJUNTO);
        assertThat(testTextoSustitutorio.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingTextoSustitutorio() throws Exception {
        int databaseSizeBeforeUpdate = textoSustitutorioRepository.findAll().size();

        // Create the TextoSustitutorio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTextoSustitutorioMockMvc.perform(put("/api/texto-sustitutorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(textoSustitutorio)))
            .andExpect(status().isCreated());

        // Validate the TextoSustitutorio in the database
        List<TextoSustitutorio> textoSustitutorioList = textoSustitutorioRepository.findAll();
        assertThat(textoSustitutorioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTextoSustitutorio() throws Exception {
        // Initialize the database
        textoSustitutorioService.save(textoSustitutorio);

        int databaseSizeBeforeDelete = textoSustitutorioRepository.findAll().size();

        // Get the textoSustitutorio
        restTextoSustitutorioMockMvc.perform(delete("/api/texto-sustitutorios/{id}", textoSustitutorio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TextoSustitutorio> textoSustitutorioList = textoSustitutorioRepository.findAll();
        assertThat(textoSustitutorioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextoSustitutorio.class);
    }
}
