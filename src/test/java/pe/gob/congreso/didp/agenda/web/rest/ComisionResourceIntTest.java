package pe.gob.congreso.didp.agenda.web.rest;

import pe.gob.congreso.didp.agenda.AgendaApp;

import pe.gob.congreso.didp.agenda.domain.Comision;
import pe.gob.congreso.didp.agenda.repository.ComisionRepository;
import pe.gob.congreso.didp.agenda.service.ComisionService;
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
 * Test class for the ComisionResource REST controller.
 *
 * @see ComisionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgendaApp.class)
public class ComisionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ComisionRepository comisionRepository;

    @Autowired
    private ComisionService comisionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComisionMockMvc;

    private Comision comision;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComisionResource comisionResource = new ComisionResource(comisionService);
        this.restComisionMockMvc = MockMvcBuilders.standaloneSetup(comisionResource)
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
    public static Comision createEntity(EntityManager em) {
        Comision comision = new Comision()
                .name(DEFAULT_NAME);
        return comision;
    }

    @Before
    public void initTest() {
        comision = createEntity(em);
    }

    @Test
    @Transactional
    public void createComision() throws Exception {
        int databaseSizeBeforeCreate = comisionRepository.findAll().size();

        // Create the Comision

        restComisionMockMvc.perform(post("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comision)))
            .andExpect(status().isCreated());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeCreate + 1);
        Comision testComision = comisionList.get(comisionList.size() - 1);
        assertThat(testComision.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createComisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comisionRepository.findAll().size();

        // Create the Comision with an existing ID
        Comision existingComision = new Comision();
        existingComision.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComisionMockMvc.perform(post("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingComision)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = comisionRepository.findAll().size();
        // set the field null
        comision.setName(null);

        // Create the Comision, which fails.

        restComisionMockMvc.perform(post("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comision)))
            .andExpect(status().isBadRequest());

        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComisions() throws Exception {
        // Initialize the database
        comisionRepository.saveAndFlush(comision);

        // Get all the comisionList
        restComisionMockMvc.perform(get("/api/comisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comision.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getComision() throws Exception {
        // Initialize the database
        comisionRepository.saveAndFlush(comision);

        // Get the comision
        restComisionMockMvc.perform(get("/api/comisions/{id}", comision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comision.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComision() throws Exception {
        // Get the comision
        restComisionMockMvc.perform(get("/api/comisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComision() throws Exception {
        // Initialize the database
        comisionService.save(comision);

        int databaseSizeBeforeUpdate = comisionRepository.findAll().size();

        // Update the comision
        Comision updatedComision = comisionRepository.findOne(comision.getId());
        updatedComision
                .name(UPDATED_NAME);

        restComisionMockMvc.perform(put("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComision)))
            .andExpect(status().isOk());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeUpdate);
        Comision testComision = comisionList.get(comisionList.size() - 1);
        assertThat(testComision.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingComision() throws Exception {
        int databaseSizeBeforeUpdate = comisionRepository.findAll().size();

        // Create the Comision

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComisionMockMvc.perform(put("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comision)))
            .andExpect(status().isCreated());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComision() throws Exception {
        // Initialize the database
        comisionService.save(comision);

        int databaseSizeBeforeDelete = comisionRepository.findAll().size();

        // Get the comision
        restComisionMockMvc.perform(delete("/api/comisions/{id}", comision.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comision.class);
    }
}
