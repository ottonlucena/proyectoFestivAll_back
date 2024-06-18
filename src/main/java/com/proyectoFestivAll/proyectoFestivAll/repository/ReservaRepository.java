package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    /*@Query("SELECT r FROM Reserva r JOIN r.juegos j WHERE j.id = :juegoId AND r.fecha = :fecha")
    List<Reserva> findByJuegoIdAndFecha(@Param("juegoId") Long juegoId,@Param("fecha") LocalDate fecha);*/

    List<Reserva> findByReservaJuegos_Juego_IdAndFecha(Long juegoId, LocalDate fecha);
    List<Reserva> findByFecha(LocalDate fecha);

    List<Reserva> findByFechaBetween(LocalDate startDate, LocalDate endDate);

}
