package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name= "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;
    @Column(unique = true)
    private String rut;
    private String nombre;
    private String apellido;
    private String telefono;
    @Column(unique = true)
    private String email;
    private String direccion;

   @OneToOne(mappedBy = "usuario")
   private Pago pago;


    public Usuario() {
    }

    public Usuario(String rut, String nombre, String apellido, String telefono, String email, String direccion, Pago pago) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.pago = pago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", rut='" + rut + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", pago=" + pago +
                '}';
    }
}
