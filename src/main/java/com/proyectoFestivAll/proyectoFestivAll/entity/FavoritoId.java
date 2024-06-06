package com.proyectoFestivAll.proyectoFestivAll.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FavoritoId implements Serializable {
    private Long usuario_id;
    private Long juego_id;

    public FavoritoId(Long usuarioId, Long juegoId) {
        this.usuario_id = usuarioId;
        this.juego_id = juegoId;
    }
}
