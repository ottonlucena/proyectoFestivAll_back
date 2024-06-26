package com.proyectoFestivAll.proyectoFestivAll.repository;


import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByRut(String rut);

    boolean existsByRut(String rut);
}
