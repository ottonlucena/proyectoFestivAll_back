package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
}
