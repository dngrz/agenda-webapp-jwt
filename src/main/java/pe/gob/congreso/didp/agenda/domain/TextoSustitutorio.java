package pe.gob.congreso.didp.agenda.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TextoSustitutorio.
 */
@Entity
@Table(name = "texto_sustitutorio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TextoSustitutorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "archivo", nullable = false)
    private String archivo;

    @Column(name = "fecha_adjunto")
    private ZonedDateTime fechaAdjunto;

    @Column(name = "url")
    private String url;

    @ManyToOne
    private TemaAgenda tema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public TextoSustitutorio archivo(String archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public ZonedDateTime getFechaAdjunto() {
        return fechaAdjunto;
    }

    public TextoSustitutorio fechaAdjunto(ZonedDateTime fechaAdjunto) {
        this.fechaAdjunto = fechaAdjunto;
        return this;
    }

    public void setFechaAdjunto(ZonedDateTime fechaAdjunto) {
        this.fechaAdjunto = fechaAdjunto;
    }

    public String getUrl() {
        return url;
    }

    public TextoSustitutorio url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TemaAgenda getTema() {
        return tema;
    }

    public TextoSustitutorio tema(TemaAgenda temaAgenda) {
        this.tema = temaAgenda;
        return this;
    }

    public void setTema(TemaAgenda temaAgenda) {
        this.tema = temaAgenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextoSustitutorio textoSustitutorio = (TextoSustitutorio) o;
        if (textoSustitutorio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, textoSustitutorio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TextoSustitutorio{" +
            "id=" + id +
            ", archivo='" + archivo + "'" +
            ", fechaAdjunto='" + fechaAdjunto + "'" +
            ", url='" + url + "'" +
            '}';
    }
}
