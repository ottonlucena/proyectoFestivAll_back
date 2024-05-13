package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;
    private MetodoPago metodoPago;
    private float monto_pago;

    public Pago() {
    }

    public Pago(Reserva reserva, Usuario usuario, MetodoPago metodoPago, float monto_pago) {
        this.reserva = reserva;
        this.usuario = usuario;
        this.metodoPago = metodoPago;
        this.monto_pago = monto_pago;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public float getMonto_pago() {
        return monto_pago;
    }

    public void setMonto_pago(float monto_pago) {
        this.monto_pago = monto_pago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", reserva=" + reserva +
                ", usuario=" + usuario +
                ", metodoPago=" + metodoPago +
                ", monto_pago=" + monto_pago +
                '}';
    }
}
