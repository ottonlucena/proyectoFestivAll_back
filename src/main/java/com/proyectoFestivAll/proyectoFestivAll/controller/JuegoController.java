package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.exception.JuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.exception.TipoJuegoNoEncontradoException;
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
@CrossOrigin(origins = "http://localhost:5173")
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
        Juego juegoGuardado = juegoService.guardarJuego(juego);
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoGuardado);
    }

    @PutMapping
    public ResponseEntity<String> actualizarJuego(@RequestBody Juego juego){
        try{
            juegoService.actualizarJuego(juego);
            return ResponseEntity.status(HttpStatus.CREATED).body("Juego con id " + juego.getId() + " actualizado");
        }catch (TipoJuegoNoEncontradoException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }catch (JuegoNoEncontradoException exception){
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

    @GetMapping("/{id}")
    public ResponseEntity<Juego> obtenerJuegoPorId(@PathVariable Long id) {
        Optional<Juego> juegoOptional = juegoService.buscarJuegoId(id);
        return juegoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/type")
    public ResponseEntity<?> getGamesByType(@RequestParam("type") String tipo) {
        List<Juego> juegos = juegoService.buscarJuegosPorTipo(tipo);
        if (juegos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de juego no encontrado");
        }
        return ResponseEntity.ok(juegos);
    }

    @PutMapping("/{id}/caracteristica")
    public ResponseEntity<Juego> actualizarCaracteristicas(@PathVariable Long id, @RequestBody List<String> nuevaCaracteristicas){
        Optional<Juego> optionalJuego = juegoService.buscarJuegoId(id);
        if (!optionalJuego.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Juego juego = optionalJuego.get();
        juego.setCaracteristicas(nuevaCaracteristicas);
        juegoService.guardarJuego(juego);
        return ResponseEntity.ok(juego);
    }


}
