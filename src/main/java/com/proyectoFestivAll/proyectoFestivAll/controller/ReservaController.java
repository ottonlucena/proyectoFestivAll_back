package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.JuegoFechaDTO;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.UsuarioDTO;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> createReserva(@RequestBody @Valid UsuarioDTO usuarioReservaDTO) {
        UsuarioDTO usuarioDTO = reservaService.createReserva(usuarioReservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() throws GlobalNotFoundException {
        List<Reserva> reservaList = reservaService.reservaList();
        return ResponseEntity.status(HttpStatus.OK).body(reservaList);
    }

    @PostMapping("/disponibles")
    public ResponseEntity<List<Juego>> buscarJuegosDisponibles(@RequestBody @Valid JuegoFechaDTO juegoFechaDTO) {
        List<Juego> juegosDisponibles = reservaService.JuegosDiponiblesFechas(juegoFechaDTO.getNombreJuego(), juegoFechaDTO.getFechaInicio(), juegoFechaDTO.getFechaFin());
        return ResponseEntity.ok(juegosDisponibles);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) throws GlobalNotFoundException {
        Reserva reservaBuscada = reservaService.buscarReservaId(id);
        reservaService.EliminarReserva(reservaBuscada.getId());
        return ResponseEntity.ok("Reserva eliminada");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaId(@PathVariable Long id) throws GlobalNotFoundException {
        Reserva reservaBuscada = reservaService.buscarReservaId(id);
        return ResponseEntity.ok(reservaBuscada);
    }


}
