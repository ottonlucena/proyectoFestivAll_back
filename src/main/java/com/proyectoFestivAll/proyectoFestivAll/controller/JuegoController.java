package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.JuegoDTO;
import com.proyectoFestivAll.proyectoFestivAll.exception.JuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.exception.TipoJuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.exception.dto.ErrorMessage;
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
    public ResponseEntity<?> actualizarJuego(@RequestBody @Valid Juego juego){
        try{
            juegoService.actualizarJuego(juego);
            return ResponseEntity.status(HttpStatus.OK).body(juego);
        }catch (TipoJuegoNoEncontradoException exception){
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }catch (JuegoNoEncontradoException exception){
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarJuego(@PathVariable Long id){
        Juego juego = juegoService.buscarJuegoId(id);
        juegoService.eliminarJuego(id);
        return ResponseEntity.ok("Juego con id " + id + " eliminado correctamente");

    }

    @GetMapping("/{id}")
    public ResponseEntity<Juego> obtenerJuegoPorId(@PathVariable Long id) {
        Juego juego = juegoService.buscarJuegoId(id);
        return ResponseEntity.ok(juego);
    }


    @GetMapping("/type")
    public ResponseEntity<?> getGamesByType(@RequestParam("type") List<String> tipos) {
        List<Juego> juegos = juegoService.buscarJuegosPorTipo(tipos);
        if (juegos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de juego no encontrado");
        }
        return ResponseEntity.ok(juegos);
    }

    @GetMapping("/suggestion")
    public ResponseEntity<List<JuegoDTO>> listarJuegosDTO(){
        List<JuegoDTO> juegoDTOS = juegoService.listarJuegosDTO();
        return ResponseEntity.ok(juegoDTOS);
    }




}
