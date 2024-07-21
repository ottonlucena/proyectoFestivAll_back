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
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorito_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    @NotNull(message = "El campo favorito no puede ser nulo")
    private boolean favorito;
}
