package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
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
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    public Reserva() {
    }

    public Reserva(Juego juego, Usuario usuario, LocalDate fecha, LocalTime horaInicio, LocalTime horaTermino, int total, int cantidadJuego, EstadoReserva estadoReserva) {
        this.juego = juego;
        this.usuario = usuario;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.total = total;
        this.cantidadJuego = cantidadJuego;
        this.estadoReserva = estadoReserva;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(LocalTime horaTermino) {
        this.horaTermino = horaTermino;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCantidadJuego() {
        return cantidadJuego;
    }

    public void setCantidadJuego(int cantidadJuego) {
        this.cantidadJuego = cantidadJuego;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", juego=" + juego +
                ", usuario=" + usuario +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaTermino=" + horaTermino +
                ", total=" + total +
                ", cantidadJuego=" + cantidadJuego +
                ", estadoReserva=" + estadoReserva +
                '}';
    }
}
