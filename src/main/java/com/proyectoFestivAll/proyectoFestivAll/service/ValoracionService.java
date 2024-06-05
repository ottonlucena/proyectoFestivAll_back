package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Valoracion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Valoracion guardarValoracion(Valoracion valoracion) {
        String query = "INSERT INTO valoracion (usuario_id, juego_id, valoracion) " +
                "VALUES (:usuarioId, :juegoId, :valoracion) " +
                "ON DUPLICATE KEY UPDATE valoracion = :valoracion";

        entityManager.createNativeQuery(query)
                .setParameter("usuarioId", valoracion.getUsuario_id())
                .setParameter("juegoId", valoracion.getJuego_id())
                .setParameter("valoracion", valoracion.getValoracion())
                .executeUpdate();

        return valoracion;
    }
}
