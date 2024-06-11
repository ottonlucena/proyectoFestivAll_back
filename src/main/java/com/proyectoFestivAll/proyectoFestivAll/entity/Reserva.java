package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    @JoinTable(
            name = "reserva_juegos",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    private List<Juego> juegos;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @NotNull
    private LocalDate fecha;
    @NotNull
    @Column(name = "hora_inicio" )
    private LocalTime horaInicio;
    @NotNull
    @Column(name = "hora_termino" )
    private LocalTime horaTermino;
    @Column(name = "total", nullable = false)
    private float total;
    @Column(name = "cantidad_juego", nullable = false )
    @NotNull
    @Positive(message = "La cantidad de juegos debe ser un valor positivo")
    private int cantidadJuego;

    @PrePersist
    @PreUpdate
    private void calcularTotal(){
        this.total = (float) this.juegos.stream()
                .mapToDouble(Juego::getValorArriendo)
                .sum()*this.cantidadJuego;
    }


}
