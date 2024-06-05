package com.proyectoFestivAll.proyectoFestivAll.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ValoracionId implements Serializable {
    private Long usuario_id;
    private Long juego_id;
}
