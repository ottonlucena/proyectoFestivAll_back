package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "favoritos")
@IdClass(FavoritoId.class)
public class Favorito {
    @Id
    @ManyToOne
    @Column(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Id
    @ManyToOne
    @Column(name = "juego_id", nullable = false)
    private Juego juego;

    @NotNull(message = "El campo favorito no puede ser nulo")
    private boolean favorito;
}
