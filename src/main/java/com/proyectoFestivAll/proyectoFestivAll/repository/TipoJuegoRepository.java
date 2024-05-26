package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoJuegoRepository extends JpaRepository<TipoJuegoEntity, Long> {
    boolean existsByTitle(String nombre);

    Optional<TipoJuegoEntity> findByTitle(String nombre);


}
