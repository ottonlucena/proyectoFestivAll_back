package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.ReservaJuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservaJuegoRepository extends JpaRepository<ReservaJuego, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ReservaJuego rj WHERE rj.reserva = :reserva")
    void deleteByReserva(@Param("reserva") Reserva reserva);
}
