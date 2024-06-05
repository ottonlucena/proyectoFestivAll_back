package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "valoracion")
public class Valoracion {
    @Id
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false)
    @NotNull(message = "Debe ingresar un usuario")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "juego_id", referencedColumnName = "juego_id", nullable = false)
    @NotNull(message = "Debe ingresar un juego")
    private Juego juego;

    @NotNull(message = "Debe ingresar una valoración")
    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
    private int valoracion;
}
