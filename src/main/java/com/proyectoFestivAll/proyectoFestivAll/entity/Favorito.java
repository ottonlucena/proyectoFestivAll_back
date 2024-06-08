package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
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
    @Column(name = "usuario_id")
    private Long usuario_id;

    @Id
    @Column(name = "juego_id")
    private Long juego_id;

    @NotNull(message = "El campo favorito no puede ser nulo")
    private boolean favorito;
}
