package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.ReservaJuego;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.exception.InsufficientQuantityException;
import com.proyectoFestivAll.proyectoFestivAll.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
   private JuegoService juegoService;

    //METODOS INTERNOS
    private Juego buscarJuegoExistente(Long juegoId){
        Juego juegoExistente = juegoService.buscarJuegoId(juegoId);
        if (juegoExistente == null){
            throw new EntityNotFoundException("El juego con id " + juegoId + " no existe");
        }
        return juegoExistente;
    }

    private void verificarDisponibilidad(Juego juegoExistente, Reserva reserva, int cantidad){
        List<Reserva> reservaList = reservaRepository.findByReservaJuegos_Juego_IdAndFecha(juegoExistente.getId(), reserva.getFecha());
        int cantidadReservada = reservaList.stream()
                .mapToInt(r -> r.getReservaJuegos().stream()
                        .filter(rj -> rj.getJuego().getId().equals(juegoExistente.getId()))
                        .mapToInt(ReservaJuego::getCantidad)
                        .sum())
                .sum();

        if (cantidadReservada + cantidad > juegoExistente.getCantidad()){
            throw new InsufficientQuantityException(("No hay suficiente cantidad de juegos disponibles."));
        }
    }

    public Reserva createReserva(Reserva reserva){
        for (ReservaJuego reservaJuego : reserva.getReservaJuegos()){
            Juego juegoExistente = buscarJuegoExistente(reservaJuego.getJuego().getId());
            verificarDisponibilidad(juegoExistente, reserva, reservaJuego.getCantidad());
            reservaJuego.setJuego(juegoExistente);
            reservaJuego.setReserva(reserva);
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
                .flatMap(reserva -> reserva.getReservaJuegos().stream())
                .collect(Collectors.groupingBy(reservaJuego -> reservaJuego.getJuego().getId(),
                        Collectors.summingInt(ReservaJuego::getCantidad)))
                .entrySet().stream()
                .filter(entry -> {
                    Juego juego = buscarJuegoExistente(entry.getKey());
                    return entry.getValue() >= juego.getCantidad();
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return juegosBuscados.stream()
                .filter(juego -> !juegosReservados.contains(juego.getId()))
                .collect(Collectors.toList());
    }

    public void EliminarReserva(Long id){
         reservaRepository.deleteById(id);
    }

    public Reserva buscarReservaId(Long id) throws GlobalNotFoundException{
       return reservaRepository.findById(id)
               .orElseThrow(() -> new GlobalNotFoundException("Reserva con id " + id + " no encontrada"));
    }

}
