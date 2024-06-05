package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.service.ValoracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/valoracion")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ValoracionController {

    private final ValoracionService valoracionService;

    @PostMapping
    public String chaoMundo() {
        return valoracionService.guardarValoracion();
    }

    //    @PostMapping
    //    public ResponseEntity<?> guardarJuego(@Valid @RequestBody Juego juego){
    //        Juego juegoGuardado = juegoService.guardarJuego(juego);
    //        return ResponseEntity.status(HttpStatus.CREATED).body(juegoGuardado);
    //    }
}
