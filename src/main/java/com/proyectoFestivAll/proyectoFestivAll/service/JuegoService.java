package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import com.proyectoFestivAll.proyectoFestivAll.repository.JuegoRepository;
import com.proyectoFestivAll.proyectoFestivAll.repository.TipoJuegoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JuegoService {
    @Autowired
    private JuegoRepository juegoRepository;

    private final TipoJuegoRepository tipoJuegoRepository;

    public Juego guardarJuego(Juego juego){
        String nombreTipoJuego = juego.getTipo().getNombre();
        TipoJuegoEntity tipoJuego = tipoJuegoRepository.findByNombre(nombreTipoJuego);

        return tipoJuego != null ? juegoRepository.save(juego.withTipo(tipoJuego)) : null;
    }

    public Optional<Juego> buscarJuegoId(Long id){
        return juegoRepository.findById(id);
    }

    public List<Juego> listarJuegos(){
        return juegoRepository.findAll();
    }

    public Juego actualizarJuego(Juego juego){
        return juegoRepository.save(juego);
    }
    @Transactional
    public void eliminarJuego(Long id){
       juegoRepository.deleteById(id);
    }

    public List<Juego> buscarJuegosPorTipo(String tipo){
        return juegoRepository.findByTipoNombre(tipo);
    }


}
