package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pagos")
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

    public Pago() {
    }

    public Pago(Usuario usuario, MetodoPago metodoPago, float montoPago) {
        this.usuario = usuario;
        this.metodoPago = metodoPago;
        this.montoPago = montoPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public float getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(float montoPago) {
        this.montoPago = montoPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", metodoPago=" + metodoPago +
                ", montoPago=" + montoPago +
                '}';
    }
}
