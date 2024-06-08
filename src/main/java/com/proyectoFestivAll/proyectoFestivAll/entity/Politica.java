package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "politicas")
public class Politica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "politica_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    @NotBlank(message = "Debe ingresar un título")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "Debe ingresar una descripción")
    @Column(name = "descripcion")
    private String descripcion;
}
