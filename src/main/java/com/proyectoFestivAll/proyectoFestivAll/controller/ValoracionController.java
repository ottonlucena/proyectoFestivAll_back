package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Valoracion;
import com.proyectoFestivAll.proyectoFestivAll.service.ValoracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/valoracion")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ValoracionController {

    private final ValoracionService valoracionService;

    @PostMapping
    public Valoracion guardarYDevolverValoracion(@RequestBody Valoracion valoracion) {
        return valoracionService.guardarValoracion(valoracion);
    }

    @GetMapping("/{juegoId}")
    public Map<String, Object> obtenerValoracionesYPromedio(@PathVariable int juegoId) {
        return valoracionService.obtenerValoracionesYPromedio(juegoId);
    }
}
