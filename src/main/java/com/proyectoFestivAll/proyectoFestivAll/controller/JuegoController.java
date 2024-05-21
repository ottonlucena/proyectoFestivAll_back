package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import com.proyectoFestivAll.proyectoFestivAll.repository.TipoJuegoRepository;
import com.proyectoFestivAll.proyectoFestivAll.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/juegos")
@Validated
@RequiredArgsConstructor
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    private final TipoJuegoRepository tipoJuegoRepository;

    @GetMapping
    public List<Juego> listarJuegos(){
        return juegoService.listarJuegos();
    }

    @PostMapping
    public ResponseEntity<?> guardarJuego(@Valid @RequestBody Juego juego){
        String nombreTipoJuego = juego.getTipo().getNombre();
        if(!tipoJuegoRepository.existsByNombre(nombreTipoJuego)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tipo de juego " + nombreTipoJuego + " no existe");
        }

        Juego juegoGuardado = juegoService.guardarJuego(juego);
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoGuardado);
    }

    @PutMapping
    public ResponseEntity<String> actualizarJuego(@RequestBody Juego juego){
        Optional<Juego> juegoOptional = juegoService.buscarJuegoId(juego.getId());
        if (juegoOptional.isPresent()){
            juegoService.actualizarJuego(juego);
            return ResponseEntity.status(HttpStatus.CREATED).body("Juego id " + juego.getId() + " actualizado");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarJuego(@PathVariable Long id){
        Optional<Juego> buscarJuego = juegoService.buscarJuegoId(id);
        if (buscarJuego.isPresent()){
            juegoService.eliminarJuego(id);
            return ResponseEntity.ok("Juego con id: " + id + " eliminado correctamente" );
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/type")
    public ResponseEntity<?> getGamesByType(@RequestParam("type") String tipo) {
        List<Juego> juegos = juegoService.buscarJuegosPorTipo(tipo);
        if (juegos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de juego no encontrado");
        }
        return ResponseEntity.ok(juegos);
    }


}
