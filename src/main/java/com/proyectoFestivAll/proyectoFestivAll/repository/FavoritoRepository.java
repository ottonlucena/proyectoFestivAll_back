package com.proyectoFestivAll.proyectoFestivAll.repository;

import com.proyectoFestivAll.proyectoFestivAll.entity.Favorito;
import com.proyectoFestivAll.proyectoFestivAll.entity.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {
}
