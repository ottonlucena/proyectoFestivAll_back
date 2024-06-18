package com.proyectoFestivAll.proyectoFestivAll.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reserva_juegos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaJuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_juego_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    @JsonBackReference("reserva-reservaJuego")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    @Column(name = "cantidad", nullable = false)
    @NotNull
    @Positive(message = "La cantidad de juegos debe ser un valor positivo")
    private int cantidad;


}
