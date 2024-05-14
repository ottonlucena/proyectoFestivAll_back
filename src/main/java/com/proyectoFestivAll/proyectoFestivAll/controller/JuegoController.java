package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/juegos")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public List<Juego> listarJuegos(){
        return juegoService.listarJuegos();
    }

    @PostMapping
    public ResponseEntity<Juego> guardarJuego(@RequestBody Juego juego){
        return ResponseEntity.ok(juegoService.guardarJuego(juego));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarJuego(@RequestBody Juego juego){
        Optional<Juego> juegoOptional = juegoService.buscarJuegoId(juego.getId());
        if (juegoOptional.isPresent()){
            juegoService.actualizarJuego(juego);
            return ResponseEntity.ok("Juego id: " + juego.getId() + " actualizado");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarJuego(Long id){
        boolean juegoEliminado = juegoService.eliminarJuego(id);
        if (juegoEliminado){
            return ResponseEntity.ok("Juego eliminado");
        }else {
            return ResponseEntity.notFound().build();
        }

    }

}
