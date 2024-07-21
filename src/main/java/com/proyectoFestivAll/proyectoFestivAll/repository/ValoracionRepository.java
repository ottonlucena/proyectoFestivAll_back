package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Valoracion;
import com.proyectoFestivAll.proyectoFestivAll.entity.ValoracionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, ValoracionId> {
    Optional<Valoracion> findByUsuarioIdAndJuegoId(Long usuarioId, Long juegoId);
}
