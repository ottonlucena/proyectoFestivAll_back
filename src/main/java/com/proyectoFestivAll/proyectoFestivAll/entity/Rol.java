package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Roles tipo;

    public Rol() {
    }

    public Rol(Roles tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles getTipo() {
        return tipo;
    }

    public void setTipo(Roles tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", tipo=" + tipo +
                '}';
    }
}
