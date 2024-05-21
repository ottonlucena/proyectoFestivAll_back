package com.proyectoFestivAll.proyectoFestivAll.service;


import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import com.proyectoFestivAll.proyectoFestivAll.repository.TipoJuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoJuegoService {
    @Autowired
    private TipoJuegoRepository tipoJuegoRepository;

    public List<TipoJuegoEntity> obtenerTiposDeJuegos(){
        return tipoJuegoRepository.findAll();
    }

    public TipoJuegoEntity agregarTipoJuego(TipoJuegoEntity tipoJuego){
        if (tipoJuegoRepository.existsByNombre(tipoJuego.getNombre())){
            throw new IllegalArgumentException("El tipo de juego ya existe");
        }
        return tipoJuegoRepository.save(tipoJuego);
    }
}
