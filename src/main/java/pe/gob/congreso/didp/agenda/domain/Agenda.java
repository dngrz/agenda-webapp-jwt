package pe.gob.congreso.didp.agenda.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pe.gob.congreso.didp.agenda.domain.enumeration.OrdenLegislatura;

import pe.gob.congreso.didp.agenda.domain.enumeration.TipoLegislatura;

import pe.gob.congreso.didp.agenda.domain.enumeration.IndicadorPublicado;

import pe.gob.congreso.didp.agenda.domain.enumeration.EstadoAgenda;

/**
 * A Agenda.
 */
@Entity
@Table(name = "agenda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fec_sesion")
    private ZonedDateTime fecSesion;

    @Enumerated(EnumType.STRING)
    @Column(name = "legislatura")
    private OrdenLegislatura legislatura;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_legislatura")
    private TipoLegislatura tipoLegislatura;

    @Enumerated(EnumType.STRING)
    @Column(name = "ind_publicado")
    private IndicadorPublicado indPublicado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_agenda")
    private EstadoAgenda estadoAgenda;

    @OneToMany(mappedBy = "agenda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TemaAgenda> temaAgendas = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Agenda nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getFecSesion() {
        return fecSesion;
    }

    public Agenda fecSesion(ZonedDateTime fecSesion) {
        this.fecSesion = fecSesion;
        return this;
    }

    public void setFecSesion(ZonedDateTime fecSesion) {
        this.fecSesion = fecSesion;
    }

    public OrdenLegislatura getLegislatura() {
        return legislatura;
    }

    public Agenda legislatura(OrdenLegislatura legislatura) {
        this.legislatura = legislatura;
        return this;
    }

    public void setLegislatura(OrdenLegislatura legislatura) {
        this.legislatura = legislatura;
    }

    public TipoLegislatura getTipoLegislatura() {
        return tipoLegislatura;
    }

    public Agenda tipoLegislatura(TipoLegislatura tipoLegislatura) {
        this.tipoLegislatura = tipoLegislatura;
        return this;
    }

    public void setTipoLegislatura(TipoLegislatura tipoLegislatura) {
        this.tipoLegislatura = tipoLegislatura;
    }

    public IndicadorPublicado getIndPublicado() {
        return indPublicado;
    }

    public Agenda indPublicado(IndicadorPublicado indPublicado) {
        this.indPublicado = indPublicado;
        return this;
    }

    public void setIndPublicado(IndicadorPublicado indPublicado) {
        this.indPublicado = indPublicado;
    }

    public EstadoAgenda getEstadoAgenda() {
        return estadoAgenda;
    }

    public Agenda estadoAgenda(EstadoAgenda estadoAgenda) {
        this.estadoAgenda = estadoAgenda;
        return this;
    }

    public void setEstadoAgenda(EstadoAgenda estadoAgenda) {
        this.estadoAgenda = estadoAgenda;
    }

    public Set<TemaAgenda> getTemaAgendas() {
        return temaAgendas;
    }

    public Agenda temaAgendas(Set<TemaAgenda> temaAgenda) {
        this.temaAgendas = temaAgenda;
        return this;
    }

    public Agenda addTemaAgendas(TemaAgenda temaAgenda) {
        this.temaAgendas.add(temaAgenda);
        temaAgenda.setAgenda(this);
        return this;
    }

    public Agenda removeTemaAgendas(TemaAgenda temaAgenda) {
        this.temaAgendas.remove(temaAgenda);
        temaAgenda.setAgenda(null);
        return this;
    }

    public void setTemaAgendas(Set<TemaAgenda> temaAgenda) {
        this.temaAgendas = temaAgenda;
    }

    public User getUser() {
        return user;
    }

    public Agenda user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Agenda agenda = (Agenda) o;
        if (agenda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, agenda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Agenda{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", fecSesion='" + fecSesion + "'" +
            ", legislatura='" + legislatura + "'" +
            ", tipoLegislatura='" + tipoLegislatura + "'" +
            ", indPublicado='" + indPublicado + "'" +
            ", estadoAgenda='" + estadoAgenda + "'" +
            '}';
    }
}
