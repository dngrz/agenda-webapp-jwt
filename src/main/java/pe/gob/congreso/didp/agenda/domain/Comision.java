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
 * A Comision.
 */
@Entity
@Table(name = "comision")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "comisions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TemaAgenda> temaAgenda = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Comision name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TemaAgenda> getTemaAgenda() {
        return temaAgenda;
    }

    public Comision temaAgenda(Set<TemaAgenda> temaAgenda) {
        this.temaAgenda = temaAgenda;
        return this;
    }

    public Comision addTemaAgenda(TemaAgenda temaAgenda) {
        this.temaAgenda.add(temaAgenda);
        temaAgenda.getComisions().add(this);
        return this;
    }

    public Comision removeTemaAgenda(TemaAgenda temaAgenda) {
        this.temaAgenda.remove(temaAgenda);
        temaAgenda.getComisions().remove(this);
        return this;
    }

    public void setTemaAgenda(Set<TemaAgenda> temaAgenda) {
        this.temaAgenda = temaAgenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comision comision = (Comision) o;
        if (comision.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comision.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comision{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
