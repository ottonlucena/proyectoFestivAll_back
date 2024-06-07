package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Politica;
import com.proyectoFestivAll.proyectoFestivAll.exception.JuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.exception.PoliticaNoEncontradaException;
import com.proyectoFestivAll.proyectoFestivAll.repository.JuegoRepository;
import com.proyectoFestivAll.proyectoFestivAll.repository.PoliticaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoliticaService {
    @Autowired
    private PoliticaRepository politicaRepository;

    private final JuegoRepository juegoRepository;

    @Transactional
    public Politica guardarPolitica(Politica politica, Long juegoId) {
        Juego juego = juegoRepository.findById(juegoId)
                .orElseThrow(() -> new JuegoNoEncontradoException("Juego con id: " + juegoId + " no encontrado"));
        politica.setJuego(juego);
        return politicaRepository.save(politica);
    }

    public List<Politica> listarPoliticas() {
        return politicaRepository.findAll();
    }

    public List<Politica> buscarPoliticasPorJuego(Long juegoId) {
        return politicaRepository.findByJuegoId(juegoId);
    }

    @Transactional
    public Politica actualizarPolitica(Long juegoId, Politica politica) {
        Juego juego = juegoRepository.findById(juegoId)
                .orElseThrow(() -> new JuegoNoEncontradoException("Juego con id: " + juegoId + " no encontrado"));

        List<Politica> politicas = politicaRepository.findByJuegoId(juegoId);
        if (politicas.isEmpty()) {
            throw new PoliticaNoEncontradaException("No se encontraron políticas para el juego con id: " + juegoId);
        }

        Politica politicaExiste = politicas.get(0); // Suponiendo que hay una sola política por juego

        BeanUtils.copyProperties(politica, politicaExiste, "id", "juego");
        politicaExiste.setJuego(juego);
        return politicaRepository.save(politicaExiste);
    }

    @Transactional
    public void eliminarPolitica(Long juegoId) {
        List<Politica> politicas = politicaRepository.findByJuegoId(juegoId);
        if (politicas.isEmpty()) {
            throw new PoliticaNoEncontradaException("No se encontraron políticas para el juego con id: " + juegoId);
        }
        Politica politica = politicas.get(0); // Suponiendo que hay una sola política por juego
        politicaRepository.deleteById(politica.getId());
    }
}
