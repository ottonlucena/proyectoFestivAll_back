package com.proyectoFestivAll.proyectoFestivAll.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(Reserva.class);

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
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "total", nullable = false)
    private float total;

    @Column(name = "cantidad_juego", nullable = false )
    private int cantidadJuego;
    @PrePersist
    @PreUpdate
    public void calcularTotal(){
        logger.info("Ejecutando calcularTotal");
        logger.info("ReservaJuegos size: {}", this.reservaJuegos.size());
        this.total = (float) this.reservaJuegos.stream()
                .mapToDouble(rj -> rj.getJuego().getValorArriendo() * rj.getCantidad())
                .sum();

        System.out.println("ejecuantado calcular");

        this.cantidadJuego = this.reservaJuegos.stream()
                .mapToInt(ReservaJuego::getCantidad)
                .sum();
        logger.info("Total calculado: {}", this.total);
        logger.info("Cantidad de juegos calculada: {}", this.cantidadJuego);
    }

}
