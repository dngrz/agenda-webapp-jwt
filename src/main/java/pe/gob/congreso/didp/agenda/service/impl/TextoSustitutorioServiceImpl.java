package pe.gob.congreso.didp.agenda.service.impl;

import pe.gob.congreso.didp.agenda.service.TextoSustitutorioService;
import pe.gob.congreso.didp.agenda.domain.TextoSustitutorio;
import pe.gob.congreso.didp.agenda.repository.TextoSustitutorioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TextoSustitutorio.
 */
@Service
@Transactional
public class TextoSustitutorioServiceImpl implements TextoSustitutorioService{

    private final Logger log = LoggerFactory.getLogger(TextoSustitutorioServiceImpl.class);
    
    private final TextoSustitutorioRepository textoSustitutorioRepository;

    public TextoSustitutorioServiceImpl(TextoSustitutorioRepository textoSustitutorioRepository) {
        this.textoSustitutorioRepository = textoSustitutorioRepository;
    }

    /**
     * Save a textoSustitutorio.
     *
     * @param textoSustitutorio the entity to save
     * @return the persisted entity
     */
    @Override
    public TextoSustitutorio save(TextoSustitutorio textoSustitutorio) {
        log.debug("Request to save TextoSustitutorio : {}", textoSustitutorio);
        TextoSustitutorio result = textoSustitutorioRepository.save(textoSustitutorio);
        return result;
    }

    /**
     *  Get all the textoSustitutorios.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TextoSustitutorio> findAll() {
        log.debug("Request to get all TextoSustitutorios");
        List<TextoSustitutorio> result = textoSustitutorioRepository.findAll();

        return result;
    }

    /**
     *  Get one textoSustitutorio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TextoSustitutorio findOne(Long id) {
        log.debug("Request to get TextoSustitutorio : {}", id);
        TextoSustitutorio textoSustitutorio = textoSustitutorioRepository.findOne(id);
        return textoSustitutorio;
    }

    /**
     *  Delete the  textoSustitutorio by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TextoSustitutorio : {}", id);
        textoSustitutorioRepository.delete(id);
    }
}
