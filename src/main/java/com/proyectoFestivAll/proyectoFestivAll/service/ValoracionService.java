package com.proyectoFestivAll.proyectoFestivAll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    @Transactional
    public String guardarValoracion() {
        return "Chao Mundo";
    }

    //    @Transactional
    //    public Juego guardarJuego(Juego juego){
    //        String nombreTipoJuego = juego.getTipo().getTitle();
    //
    //        TipoJuegoEntity tipoJuego = tipoJuegoRepository.findByTitle(nombreTipoJuego)
    //                .orElseThrow(() -> new TipoJuegoNoEncontradoException("Tipo de juego " + nombreTipoJuego + " no existe"));
    //
    //        juego.setTipo(tipoJuego);
    //        return juegoRepository.save(juego);
    //    }

    // Aquí puedes agregar otros métodos para manejar la lógica de negocio relacionada con Valoracion.
}
