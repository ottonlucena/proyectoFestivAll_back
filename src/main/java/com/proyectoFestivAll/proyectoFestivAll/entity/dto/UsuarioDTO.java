package com.proyectoFestivAll.proyectoFestivAll.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private List<ReservaDTO> reservasDTO;

    public UsuarioDTO() {
        this.reservasDTO = new ArrayList<>();
    }
}
