package com.proyectoFestivAll.proyectoFestivAll.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JuegoFechaDTO {
    private String nombreJuego;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
