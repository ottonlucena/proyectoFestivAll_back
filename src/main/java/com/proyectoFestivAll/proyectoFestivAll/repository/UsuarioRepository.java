package com.proyectoFestivAll.proyectoFestivAll.repository;


import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByRut(String rut);
    Optional<Usuario> findByEmail(String email);
    boolean existsByRut(String rut);
    @Query("SELECT u FROM Usuario u WHERE u.reservas IS NOT EMPTY")
    List<Usuario> findUsuariosConReservas();
}
