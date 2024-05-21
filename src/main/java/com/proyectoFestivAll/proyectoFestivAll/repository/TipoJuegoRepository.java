package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.TipoJuegoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoJuegoRepository extends JpaRepository<TipoJuegoEntity, Long> {
    boolean existsByNombre(String nombre);

    TipoJuegoEntity findByNombre(String nombre);


}
