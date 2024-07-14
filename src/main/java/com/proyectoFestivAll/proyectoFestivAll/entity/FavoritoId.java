package com.proyectoFestivAll.proyectoFestivAll.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@Getter
@Setter
public class FavoritoId implements Serializable {
    private Long usuario;
    private Long juego;

    // Constructor, getters, setters, equals, y hashCode

    public FavoritoId(Long usuario, Long juego) {
        this.usuario = usuario;
        this.juego = juego;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritoId that = (FavoritoId) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(juego, that.juego);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, juego);
    }
}
