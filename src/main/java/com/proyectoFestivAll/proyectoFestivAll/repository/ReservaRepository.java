package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByReservaJuegos_Juego_IdAndFechaInicio(Long juegoId, LocalDate fecha);
    List<Reserva> findByFechaInicio(LocalDate fecha);
    List<Reserva> findByFechaInicioBetween(LocalDate startDate, LocalDate endDate);
    List<Reserva> findByUsuarioIdAndFechaInicioAndFechaFin(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin);
}
