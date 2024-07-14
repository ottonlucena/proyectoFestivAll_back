package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Valoracion;
import com.proyectoFestivAll.proyectoFestivAll.repository.ValoracionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    private final ValoracionRepository valoracionRepository;

    @Transactional
    public Valoracion guardarValoracion(Valoracion valoracion) {
        // Establecer la fecha actual si no está presente
        if (valoracion.getFecha() == null) {
            valoracion.setFecha(LocalDate.now());
        }

        // Buscar una valoración existente por usuario_id y juego_id
        Optional<Valoracion> valoracionExistente = valoracionRepository.findByUsuarioIdAndJuegoId(
                valoracion.getUsuario().getId(), valoracion.getJuego().getId());

        if (valoracionExistente.isPresent()) {
            Valoracion valoracionActualizada = valoracionExistente.get();
            valoracionActualizada.setValoracion(valoracion.getValoracion());
            valoracionActualizada.setComentario(valoracion.getComentario());
            valoracionActualizada.setFecha(valoracion.getFecha());
            return valoracionRepository.save(valoracionActualizada);
        } else {
            return valoracionRepository.save(valoracion);
        }
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
