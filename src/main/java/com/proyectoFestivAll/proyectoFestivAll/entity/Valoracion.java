package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
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
@IdClass(ValoracionId.class)
public class Valoracion {
    @Id
    private Long usuario_id;

    @Id
    private Long juego_id;

    @NotNull(message = "Debe ingresar una valoración")
    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
    private int valoracion;
}
