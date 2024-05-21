package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import com.proyectoFestivAll.proyectoFestivAll.service.TipoJuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tipo-juegos")
public class TipoJuegoController {

    @Autowired
    private TipoJuegoService tipoJuegoService;

    @GetMapping
    public ResponseEntity<List<TipoJuegoEntity>> obtenerTipoJuegos(){
        List<TipoJuegoEntity> tiposDeJuegos = tipoJuegoService.obtenerTiposDeJuegos();
        return new ResponseEntity<>(tiposDeJuegos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> agregarTipoJuego(@RequestBody TipoJuegoEntity tipoJuego){
        try{
            TipoJuegoEntity nuevoTipoJuego = tipoJuegoService.agregarTipoJuego(tipoJuego);
            return new ResponseEntity<>(nuevoTipoJuego, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
