package pe.gob.congreso.didp.agenda.web.rest;

import pe.gob.congreso.didp.agenda.AgendaApp;

import pe.gob.congreso.didp.agenda.domain.Agenda;
import pe.gob.congreso.didp.agenda.repository.AgendaRepository;
import pe.gob.congreso.didp.agenda.service.AgendaService;
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

import pe.gob.congreso.didp.agenda.domain.enumeration.OrdenLegislatura;
import pe.gob.congreso.didp.agenda.domain.enumeration.TipoLegislatura;
import pe.gob.congreso.didp.agenda.domain.enumeration.IndicadorPublicado;
import pe.gob.congreso.didp.agenda.domain.enumeration.EstadoAgenda;
/**
 * Test class for the AgendaResource REST controller.
 *
 * @see AgendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgendaApp.class)
public class AgendaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FEC_SESION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FEC_SESION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final OrdenLegislatura DEFAULT_LEGISLATURA = OrdenLegislatura.PRIMERA;
    private static final OrdenLegislatura UPDATED_LEGISLATURA = OrdenLegislatura.SEGUNDA;

    private static final TipoLegislatura DEFAULT_TIPO_LEGISLATURA = TipoLegislatura.ORDINARIA;
    private static final TipoLegislatura UPDATED_TIPO_LEGISLATURA = TipoLegislatura.EXTRAORDINARIA;

    private static final IndicadorPublicado DEFAULT_IND_PUBLICADO = IndicadorPublicado.PUBLICADO;
    private static final IndicadorPublicado UPDATED_IND_PUBLICADO = IndicadorPublicado.NO_PUBLICADO;

    private static final EstadoAgenda DEFAULT_ESTADO_AGENDA = EstadoAgenda.PENDIENTE;
    private static final EstadoAgenda UPDATED_ESTADO_AGENDA = EstadoAgenda.EN_SESION;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgendaMockMvc;

    private Agenda agenda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgendaResource agendaResource = new AgendaResource(agendaService);
        this.restAgendaMockMvc = MockMvcBuilders.standaloneSetup(agendaResource)
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
    public static Agenda createEntity(EntityManager em) {
        Agenda agenda = new Agenda()
                .nombre(DEFAULT_NOMBRE)
                .fecSesion(DEFAULT_FEC_SESION)
                .legislatura(DEFAULT_LEGISLATURA)
                .tipoLegislatura(DEFAULT_TIPO_LEGISLATURA)
                .indPublicado(DEFAULT_IND_PUBLICADO)
                .estadoAgenda(DEFAULT_ESTADO_AGENDA);
        return agenda;
    }

    @Before
    public void initTest() {
        agenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgenda() throws Exception {
        int databaseSizeBeforeCreate = agendaRepository.findAll().size();

        // Create the Agenda

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isCreated());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeCreate + 1);
        Agenda testAgenda = agendaList.get(agendaList.size() - 1);
        assertThat(testAgenda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAgenda.getFecSesion()).isEqualTo(DEFAULT_FEC_SESION);
        assertThat(testAgenda.getLegislatura()).isEqualTo(DEFAULT_LEGISLATURA);
        assertThat(testAgenda.getTipoLegislatura()).isEqualTo(DEFAULT_TIPO_LEGISLATURA);
        assertThat(testAgenda.getIndPublicado()).isEqualTo(DEFAULT_IND_PUBLICADO);
        assertThat(testAgenda.getEstadoAgenda()).isEqualTo(DEFAULT_ESTADO_AGENDA);
    }

    @Test
    @Transactional
    public void createAgendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaRepository.findAll().size();

        // Create the Agenda with an existing ID
        Agenda existingAgenda = new Agenda();
        existingAgenda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAgenda)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setNombre(null);

        // Create the Agenda, which fails.

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList
        restAgendaMockMvc.perform(get("/api/agenda?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fecSesion").value(hasItem(sameInstant(DEFAULT_FEC_SESION))))
            .andExpect(jsonPath("$.[*].legislatura").value(hasItem(DEFAULT_LEGISLATURA.toString())))
            .andExpect(jsonPath("$.[*].tipoLegislatura").value(hasItem(DEFAULT_TIPO_LEGISLATURA.toString())))
            .andExpect(jsonPath("$.[*].indPublicado").value(hasItem(DEFAULT_IND_PUBLICADO.toString())))
            .andExpect(jsonPath("$.[*].estadoAgenda").value(hasItem(DEFAULT_ESTADO_AGENDA.toString())));
    }

    @Test
    @Transactional
    public void getAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        // Get the agenda
        restAgendaMockMvc.perform(get("/api/agenda/{id}", agenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agenda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fecSesion").value(sameInstant(DEFAULT_FEC_SESION)))
            .andExpect(jsonPath("$.legislatura").value(DEFAULT_LEGISLATURA.toString()))
            .andExpect(jsonPath("$.tipoLegislatura").value(DEFAULT_TIPO_LEGISLATURA.toString()))
            .andExpect(jsonPath("$.indPublicado").value(DEFAULT_IND_PUBLICADO.toString()))
            .andExpect(jsonPath("$.estadoAgenda").value(DEFAULT_ESTADO_AGENDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgenda() throws Exception {
        // Get the agenda
        restAgendaMockMvc.perform(get("/api/agenda/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgenda() throws Exception {
        // Initialize the database
        agendaService.save(agenda);

        int databaseSizeBeforeUpdate = agendaRepository.findAll().size();

        // Update the agenda
        Agenda updatedAgenda = agendaRepository.findOne(agenda.getId());
        updatedAgenda
                .nombre(UPDATED_NOMBRE)
                .fecSesion(UPDATED_FEC_SESION)
                .legislatura(UPDATED_LEGISLATURA)
                .tipoLegislatura(UPDATED_TIPO_LEGISLATURA)
                .indPublicado(UPDATED_IND_PUBLICADO)
                .estadoAgenda(UPDATED_ESTADO_AGENDA);

        restAgendaMockMvc.perform(put("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgenda)))
            .andExpect(status().isOk());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeUpdate);
        Agenda testAgenda = agendaList.get(agendaList.size() - 1);
        assertThat(testAgenda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAgenda.getFecSesion()).isEqualTo(UPDATED_FEC_SESION);
        assertThat(testAgenda.getLegislatura()).isEqualTo(UPDATED_LEGISLATURA);
        assertThat(testAgenda.getTipoLegislatura()).isEqualTo(UPDATED_TIPO_LEGISLATURA);
        assertThat(testAgenda.getIndPublicado()).isEqualTo(UPDATED_IND_PUBLICADO);
        assertThat(testAgenda.getEstadoAgenda()).isEqualTo(UPDATED_ESTADO_AGENDA);
    }

    @Test
    @Transactional
    public void updateNonExistingAgenda() throws Exception {
        int databaseSizeBeforeUpdate = agendaRepository.findAll().size();

        // Create the Agenda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgendaMockMvc.perform(put("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isCreated());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgenda() throws Exception {
        // Initialize the database
        agendaService.save(agenda);

        int databaseSizeBeforeDelete = agendaRepository.findAll().size();

        // Get the agenda
        restAgendaMockMvc.perform(delete("/api/agenda/{id}", agenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agenda.class);
    }
}
