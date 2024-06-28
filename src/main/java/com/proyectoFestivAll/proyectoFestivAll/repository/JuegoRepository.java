package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByTipo_TitleIn(List<String> tipos);

    @Query("SELECT DISTINCT j FROM Juego j GROUP BY j.nombre")
    List<Juego> findAllByNombreDistinct();

    List<Juego> findByNombreContainingIgnoreCase(String nombre);


}
