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


    public Usuario() {
    }

    public Usuario(String rut, String nombre, String apellido, String telefono, String email, String direccion, Rol rol, Pago pago) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;

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



    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", rut='" + rut + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\''
              ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(rut, usuario.rut) && Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(telefono, usuario.telefono) && Objects.equals(email, usuario.email) && Objects.equals(direccion, usuario.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rut, nombre, apellido, telefono, email, direccion);
    }
}
