package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Valoracion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Valoracion guardarValoracion(Valoracion valoracion) {
        String query = "INSERT INTO valoracion (usuario_id, juego_id, valoracion, comentario) " +
                "VALUES (:usuarioId, :juegoId, :valoracion, :comentario) " +
                "ON DUPLICATE KEY UPDATE valoracion = :valoracion, comentario = :comentario";

        entityManager.createNativeQuery(query)
                .setParameter("usuarioId", valoracion.getUsuario_id())
                .setParameter("juegoId", valoracion.getJuego_id())
                .setParameter("valoracion", valoracion.getValoracion())
                .setParameter("comentario", valoracion.getComentario())
                .executeUpdate();

        return valoracion;
    }

    @Transactional
    public Map<String, Object> obtenerValoracionesYPromedio(int juegoId) {
        String queryStr = "SELECT j.juego_id, j.promedio_valoracion AS promedio, j.cantidad_valoraciones AS cantidadValoraciones, u.nombre, v.valoracion, v.fecha, v.comentario " +
                "FROM valoracion v " +
                "JOIN usuarios u ON v.usuario_id = u.usuario_id " +
                "JOIN juegos j ON v.juego_id = j.juego_id " +
                "WHERE v.juego_id = :juegoId " +
                "AND v.fecha <> '0000-00-00'";

        var query = entityManager.createNativeQuery(queryStr);
        query.setParameter("juegoId", juegoId);
        List<Object[]> results = query.getResultList();

        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> valoraciones = new ArrayList<>();
        double promedio = 0;
        int cantidadValoraciones = 0;

        if (!results.isEmpty()) {
            promedio = ((Number) results.get(0)[1]).doubleValue();
            cantidadValoraciones = ((Number) results.get(0)[2]).intValue();
        }

        for (Object[] result : results) {
            Map<String, Object> detalle = new HashMap<>();
            detalle.put("nombre", result[3]);
            detalle.put("valoracion", result[4]);
            detalle.put("fecha", ((java.sql.Date) result[5]).toLocalDate());
            detalle.put("comentario", result[6]);
            valoraciones.add(detalle);
        }

        response.put("juego_id", juegoId);
        response.put("promedio", promedio);
        response.put("cantidad_valoraciones", cantidadValoraciones);
        response.put("valoraciones", valoraciones);

        return response;
    }
}
