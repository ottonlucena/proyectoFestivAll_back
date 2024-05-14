package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "juegos")
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "juego_id")
    private Long id;
    private String nombre;
    private String descripcion;
    private float largo;
    private float ancho;
    private float altura;
    private int capacidad;
    @Column(name = "valor_arriendo")
    private int valorArriendo;
    private int cantidad;
    private String img_url;

    public Juego() {
    }

    public Juego(String nombre, String descripcion, float largo, float ancho, float altura, int capacidad, int valorArriendo, int cantidad, String img_url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.largo = largo;
        this.ancho = ancho;
        this.altura = altura;
        this.capacidad = capacidad;
        this.valorArriendo = valorArriendo;
        this.cantidad = cantidad;
        this.img_url = img_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getLargo() {
        return largo;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public float getValorArriendo() {
        return valorArriendo;
    }

    public void setValorArriendo(int valorArriendo) {
        this.valorArriendo = valorArriendo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", largo=" + largo +
                ", ancho=" + ancho +
                ", altura=" + altura +
                ", capacidad=" + capacidad +
                ", valorArriendo=" + valorArriendo +
                ", cantidad=" + cantidad +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Juego juego = (Juego) o;
        return Float.compare(juego.largo, largo) == 0 && Float.compare(juego.ancho, ancho) == 0 && Float.compare(juego.altura, altura) == 0 && capacidad == juego.capacidad && Float.compare(juego.valorArriendo, valorArriendo) == 0 && cantidad == juego.cantidad && Objects.equals(id, juego.id) && Objects.equals(nombre, juego.nombre) && Objects.equals(descripcion, juego.descripcion) && Objects.equals(img_url, juego.img_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, largo, ancho, altura, capacidad, valorArriendo, cantidad, img_url);
    }
}
