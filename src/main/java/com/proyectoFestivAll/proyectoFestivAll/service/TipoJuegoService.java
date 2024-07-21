package com.proyectoFestivAll.proyectoFestivAll.service;


import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import com.proyectoFestivAll.proyectoFestivAll.exception.TipoJuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.repository.TipoJuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoJuegoService {
    @Autowired
    private TipoJuegoRepository tipoJuegoRepository;

    public List<TipoJuegoEntity> obtenerTiposDeJuegos() {
        return tipoJuegoRepository.findAll();
    }

    @Transactional
    public TipoJuegoEntity agregarTipoJuego(TipoJuegoEntity tipoJuego) {
        if (tipoJuegoRepository.existsByTitle(tipoJuego.getTitle())) {
            throw new IllegalArgumentException("El tipo de juego ya existe");
        }
        return tipoJuegoRepository.save(tipoJuego);
    }

    public TipoJuegoEntity findByTypeGame(String name) {
        return tipoJuegoRepository.findByTitle(name)
                .orElseThrow(() -> new TipoJuegoNoEncontradoException("Game type " + name + " no found"));
    }

    @Transactional
    public void deleteGameType(String name) {
        TipoJuegoEntity tipoJuego = findByTypeGame(name);
        tipoJuegoRepository.delete(tipoJuego);
    }

    @Transactional
    public TipoJuegoEntity updateGameType(String name, TipoJuegoEntity tipoJuego) {
        TipoJuegoEntity existingGameType = findByTypeGame(name);
        existingGameType.setDescription(tipoJuego.getDescription());
        existingGameType.setTitle(tipoJuego.getTitle());
        existingGameType.setImg_url(tipoJuego.getImg_url());
        existingGameType.setFiltro(tipoJuego.getFiltro());
        return tipoJuegoRepository.save(existingGameType);
    }
}
