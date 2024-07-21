package com.proyectoFestivAll.proyectoFestivAll.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "caracteristicas")
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caracteristica_id")
    private Long id;
    @NotBlank(message = "Debe ingresar una descripción")
    private String nombre;
    @ManyToMany(mappedBy = "caracteristicas")
    @JsonIgnore
    private List<Juego> juegoList;

}
