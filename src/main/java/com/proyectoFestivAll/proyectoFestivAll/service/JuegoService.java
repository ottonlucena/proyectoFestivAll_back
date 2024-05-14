package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {
    @Autowired
    private JuegoRepository juegoRepository;

    public Juego guardarJuego(Juego juego){
        return juegoRepository.save(juego);
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

    public boolean eliminarJuego(Long id){
        try{
            juegoRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }


}
