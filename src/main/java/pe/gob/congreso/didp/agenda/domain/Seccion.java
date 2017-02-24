package pe.gob.congreso.didp.agenda.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Seccion.
 */
@Entity
@Table(name = "seccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "subtitulo", nullable = false)
    private String subtitulo;

    @ManyToMany(mappedBy = "seccions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TemaAgenda> seccions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public Seccion subtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
        return this;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public Set<TemaAgenda> getSeccions() {
        return seccions;
    }

    public Seccion seccions(Set<TemaAgenda> temaAgenda) {
        this.seccions = temaAgenda;
        return this;
    }

    public Seccion addSeccion(TemaAgenda temaAgenda) {
        this.seccions.add(temaAgenda);
        temaAgenda.getSeccions().add(this);
        return this;
    }

    public Seccion removeSeccion(TemaAgenda temaAgenda) {
        this.seccions.remove(temaAgenda);
        temaAgenda.getSeccions().remove(this);
        return this;
    }

    public void setSeccions(Set<TemaAgenda> temaAgenda) {
        this.seccions = temaAgenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seccion seccion = (Seccion) o;
        if (seccion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, seccion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seccion{" +
            "id=" + id +
            ", subtitulo='" + subtitulo + "'" +
            '}';
    }
}
