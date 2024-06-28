package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Politica;
import com.proyectoFestivAll.proyectoFestivAll.exception.JuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.exception.PoliticaNoEncontradaException;
import com.proyectoFestivAll.proyectoFestivAll.exception.dto.ErrorMessage;
import com.proyectoFestivAll.proyectoFestivAll.service.PoliticaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/politicas")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PoliticaController {

    @Autowired
    private PoliticaService politicaService;

    @PostMapping("/{juegoId}")
    public ResponseEntity<?> guardarPolitica(@PathVariable Long juegoId, @Valid @RequestBody Politica politica) {
        try {
            Politica politicaGuardada = politicaService.guardarPolitica(politica, juegoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(politicaGuardada);
        } catch (JuegoNoEncontradoException exception) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping
    public List<Politica> listarPoliticas() {
        return politicaService.listarPoliticas();
    }

    @GetMapping("/juego/{juegoId}")
    public ResponseEntity<?> buscarPoliticasPorJuego(@PathVariable Long juegoId) {
        List<Politica> politicas = politicaService.buscarPoliticasPorJuego(juegoId);
        if (politicas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron políticas para el juego con id: " + juegoId);
        }
        return ResponseEntity.ok(politicas);
    }

    @PutMapping("/{juegoId}")
    public ResponseEntity<?> actualizarPolitica(@PathVariable Long juegoId, @RequestBody @Valid Politica politica) {
        try {
            Politica politicaActualizada = politicaService.actualizarPolitica(juegoId, politica);
            return ResponseEntity.status(HttpStatus.OK).body(politicaActualizada);
        } catch (JuegoNoEncontradoException | PoliticaNoEncontradaException exception) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/{juegoId}")
    public ResponseEntity<?> eliminarPolitica(@PathVariable Long juegoId) {
        try {
            politicaService.eliminarPolitica(juegoId);
            return ResponseEntity.ok("Política del juego con id " + juegoId + " eliminada correctamente");
        } catch (PoliticaNoEncontradaException exception) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
