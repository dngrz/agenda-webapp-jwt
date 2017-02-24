package pe.gob.congreso.didp.agenda.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pe.gob.congreso.didp.agenda.domain.enumeration.IndicadorTema;

import pe.gob.congreso.didp.agenda.domain.enumeration.EstadoTema;

/**
 * A TemaAgenda.
 */
@Entity
@Table(name = "tema_agenda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TemaAgenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "numero")
    private Integer numero;

    @NotNull
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "tema_activo")
    private IndicadorTema temaActivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_tema")
    private EstadoTema estadoTema;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tema_agenda_seccion",
               joinColumns = @JoinColumn(name="tema_agenda_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="seccions_id", referencedColumnName="id"))
    private Set<Seccion> seccions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tema_agenda_comision",
               joinColumns = @JoinColumn(name="tema_agenda_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="comisions_id", referencedColumnName="id"))
    private Set<Comision> comisions = new HashSet<>();

    @ManyToOne
    private Agenda agenda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public TemaAgenda titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public TemaAgenda numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getContenido() {
        return contenido;
    }

    public TemaAgenda contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrl() {
        return url;
    }

    public TemaAgenda url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public IndicadorTema getTemaActivo() {
        return temaActivo;
    }

    public TemaAgenda temaActivo(IndicadorTema temaActivo) {
        this.temaActivo = temaActivo;
        return this;
    }

    public void setTemaActivo(IndicadorTema temaActivo) {
        this.temaActivo = temaActivo;
    }

    public EstadoTema getEstadoTema() {
        return estadoTema;
    }

    public TemaAgenda estadoTema(EstadoTema estadoTema) {
        this.estadoTema = estadoTema;
        return this;
    }

    public void setEstadoTema(EstadoTema estadoTema) {
        this.estadoTema = estadoTema;
    }

    public Set<Seccion> getSeccions() {
        return seccions;
    }

    public TemaAgenda seccions(Set<Seccion> seccions) {
        this.seccions = seccions;
        return this;
    }

    public TemaAgenda addSeccion(Seccion seccion) {
        this.seccions.add(seccion);
        seccion.getSeccions().add(this);
        return this;
    }

    public TemaAgenda removeSeccion(Seccion seccion) {
        this.seccions.remove(seccion);
        seccion.getSeccions().remove(this);
        return this;
    }

    public void setSeccions(Set<Seccion> seccions) {
        this.seccions = seccions;
    }

    public Set<Comision> getComisions() {
        return comisions;
    }

    public TemaAgenda comisions(Set<Comision> comisions) {
        this.comisions = comisions;
        return this;
    }

    public TemaAgenda addComision(Comision comision) {
        this.comisions.add(comision);
        comision.getTemaAgenda().add(this);
        return this;
    }

    public TemaAgenda removeComision(Comision comision) {
        this.comisions.remove(comision);
        comision.getTemaAgenda().remove(this);
        return this;
    }

    public void setComisions(Set<Comision> comisions) {
        this.comisions = comisions;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public TemaAgenda agenda(Agenda agenda) {
        this.agenda = agenda;
        return this;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemaAgenda temaAgenda = (TemaAgenda) o;
        if (temaAgenda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, temaAgenda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TemaAgenda{" +
            "id=" + id +
            ", titulo='" + titulo + "'" +
            ", numero='" + numero + "'" +
            ", contenido='" + contenido + "'" +
            ", url='" + url + "'" +
            ", temaActivo='" + temaActivo + "'" +
            ", estadoTema='" + estadoTema + "'" +
            '}';
    }
}
