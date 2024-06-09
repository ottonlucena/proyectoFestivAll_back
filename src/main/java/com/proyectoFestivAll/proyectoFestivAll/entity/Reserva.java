package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "juego_id")
    private Juego juego;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private LocalDate fecha;
    @Column(name = "hora_inicio" )
    private LocalTime horaInicio;
    @Column(name = "hora_termino" )
    private LocalTime horaTermino;
    private int total;
    @Column(name = "cantidad_juego" )
    private int cantidadJuego;


}
