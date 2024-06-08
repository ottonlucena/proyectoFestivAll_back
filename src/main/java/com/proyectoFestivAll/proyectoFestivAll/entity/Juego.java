package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "juegos")
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "juego_id")
    private Long id;

    @NotBlank(message = "Debe ingresar un nombre")
    private String nombre;

    @NotBlank(message = "Debe ingresar una descripción")
    private String descripcion;

    @NotNull(message = "Debe ingresar un largo")
    @Positive(message = "El largo debe ser un valor positivo")
    private float largo;

    @NotNull(message = "Debe ingresar un ancho")
    @Positive(message = "El ancho debe ser un valor positivo")
    private float ancho;

    @NotNull(message = "Debe ingresar una altura")
    @Positive(message = "La altura debe ser un valor positivo")
    private float altura;

    @NotNull(message = "Debe ingresar una capacidad")
    @Positive(message = "La capacidad debe ser un valor positivo")
    private int capacidad;

    @Column(name = "valor_arriendo")
    @NotNull(message = "Debe ingresar un valor de arriendo")
    @Positive(message = "Valor de arriendo debe ser positivo")
    private float valorArriendo;

    @NotNull(message = "Debe una cantidad")
    @Positive(message = "La cantidad debe ser un valor positivo")
    private int cantidad;

    @NotBlank(message = "Debe ingresar cargar un imagen")
    private String img_url;

    @ManyToOne
    @JoinColumn(name = "tipo_juegos_id", nullable = false)
    @NotNull(message = "Debe elegir un tipo de juego")
    private TipoJuegoEntity tipo;

    @ManyToMany
    @JoinTable(
            name = "juego_caracteristicas",
            joinColumns = @JoinColumn(name = "juego_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )
    @Column(name = "caracteristicas")
    private List<Caracteristica> caracteristicas;

    @Column(name = "promedio_valoracion", columnDefinition = "decimal(3,1) default 0.0")
    private float promedioValoracion;
}
