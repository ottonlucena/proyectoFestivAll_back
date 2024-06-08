package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pagos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long id;
    /*@OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;*/
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;
    @Column(name = "monto_pago")
    private float montoPago;

}
