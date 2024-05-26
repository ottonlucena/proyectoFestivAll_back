package com.proyectoFestivAll.proyectoFestivAll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name= "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Por favor ingresar un rut")
    private String rut;
    @Length(min = 3, max = 15)
    @NotBlank(message = "Por favor ingresar nombre")
    private String nombre;
    @Length(min = 3, max = 15)
    @NotBlank(message = "Por favor ingresar apellido")
    private String apellido;
    private String telefono;
    @NotBlank(message = "Por favor ingresar un correo electronico")
    @Email
    @Column(unique = true)
    private String email;
    private String direccion;
    @OneToOne(mappedBy = "usuario")
    private Pago pago;
    @NotBlank(message = "Debe ingresar una constraseña")
    private String password;

}
