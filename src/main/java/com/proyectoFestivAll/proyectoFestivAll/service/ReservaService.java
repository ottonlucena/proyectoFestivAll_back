package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.exception.InsufficientQuantityException;
import com.proyectoFestivAll.proyectoFestivAll.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
   private JuegoService juegoService;

    public Reserva createReserva(Reserva reserva){
       for (Juego juego : reserva.getJuegos()){
           Juego juegoExistente = juegoService.buscarJuegoId(juego.getId());

           if (juegoExistente == null){
               throw new EntityNotFoundException("El juego con ID " + juego.getId() + " no existe");
           }

        List<Reserva> reservaExists = reservaRepository.findByJuegoIdAndFecha(juegoExistente.getId(),reserva.getFecha());

        int cantReserva = reservaExists.stream()
                .mapToInt(Reserva::getCantidadJuego)
                .sum();

           // Añadir mensajes de depuración para ver valores
           System.out.println("Juego ID: " + juegoExistente.getId());
           System.out.println("Cantidad total reservada en la fecha: " + cantReserva);
           System.out.println("Cantidad solicitada: " + reserva.getCantidadJuego());
           System.out.println("Cantidad disponible del juego: " + juegoExistente.getCantidad());
           // Dentro del bucle for
           System.out.println("Reservas existentes para el juego en la fecha dada: " + reservaExists);
           System.out.println("Cantidad total reservada en la fecha: " + cantReserva);
           System.out.println("Cantidad solicitada para la reserva: " + reserva.getCantidadJuego());
           System.out.println("Cantidad disponible del juego: " + juegoExistente.getCantidad());
           System.out.println("Cantidad disponible del juego: " + juego.getCantidad());
           System.out.println("Cantidad disponible del juego: " + reserva.getJuegos().get(0).getId());


           if (cantReserva + reserva.getCantidadJuego() > juegoExistente.getCantidad()){
            throw new InsufficientQuantityException("No hay suficiente cantidad de juegos disponibles para la reserva");
        }

       }

        return reservaRepository.save(reserva);
    }

    public List<Reserva> reservaList() throws GlobalNotFoundException {
        List<Reserva> reservaList = reservaRepository.findAll();
        if (reservaList.isEmpty()){
            throw new GlobalNotFoundException("No se encontraron reservas");
        }
        return reservaList;
    }

    public List<Juego> JuegosDiponiblesFechas(String nombreJuego, LocalDate fechaInicio, LocalDate fechaFin){
        List<Juego> juegosBuscados = juegoService.buscarJuegoPorNombre(nombreJuego);
        List<Reserva> reservas;

        if (fechaInicio != null && fechaFin != null){
            reservas = reservaRepository.findByFechaBetween(fechaInicio, fechaFin);
        }else {
            LocalDate now = LocalDate.now();
            LocalDate startOfMonth = now.withDayOfMonth(1);
            LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
            reservas = reservaRepository.findByFechaBetween(startOfMonth, endOfMonth);
        }

        Set<Long> juegosReservados = reservas.stream()
                .flatMap(reserva -> reserva.getJuegos().stream())
                .map(Juego::getId)
                .collect(Collectors.toSet());

        return juegosBuscados.stream()
                .filter(juego -> !juegosReservados.contains(juego.getId()))
                .collect(Collectors.toList());
    }

}
