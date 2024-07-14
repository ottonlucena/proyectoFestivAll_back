package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Favorito;
import com.proyectoFestivAll.proyectoFestivAll.entity.FavoritoId;
import com.proyectoFestivAll.proyectoFestivAll.repository.FavoritoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    private final FavoritoRepository favoritoRepository;

    @Transactional
    public Favorito guardarFavorito(Favorito favorito) {
        // Inserción o actualización del favorito
//        String query = "INSERT INTO favoritos (usuario_id, juego_id, favorito) " +
//                "VALUES (:usuarioId, :juegoId, :favorito) " +
//                "ON DUPLICATE KEY UPDATE favorito = :favorito";
//
//        entityManager.createNativeQuery(query)
//                .setParameter("usuarioId", favorito.getUsuario_id())
//                .setParameter("juegoId", favorito.getJuego_id())
//                .setParameter("favorito", favorito.isFavorito())
//                .executeUpdate();

        return favoritoRepository.save(favorito);
    }

}
