package com.proyectoFestivAll.proyectoFestivAll.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("reserva-reservaJuego")
    private List<ReservaJuego> reservaJuegos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-reserva")
    private Usuario usuario;

    @NotNull
    private LocalDate fecha;

    @Column(name = "total", nullable = false)
    private float total;

    @Column(name = "cantidad_juego", nullable = false )
    private int cantidadJuego;
    @PrePersist
    @PreUpdate
    private void calcularTotal(){
        this.total = (float) this.reservaJuegos.stream()
                .mapToDouble(rj -> rj.getJuego().getValorArriendo() * rj.getCantidad())
                .sum();

        this.cantidadJuego = this.reservaJuegos.stream()
                .mapToInt(ReservaJuego::getCantidad)
                .sum();
    }


}
