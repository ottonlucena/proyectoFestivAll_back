package com.proyectoFestivAll.proyectoFestivAll.util;

import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.ReservaDTO;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public class ConverteDTO {
    public static UsuarioDTO convertirUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());

        List<ReservaDTO> reservaDTOS = new ArrayList<>();
        for (Reserva reserva : usuario.getReservas()) {
            ReservaDTO reservaDTO = new ReservaDTO();
            reservaDTO.setId(reserva.getId());
            reservaDTO.setTituloJuego(reserva.getReservaJuegos().get(0).getJuego().getNombre());
            reservaDTO.setFechaInicio(reserva.getFechaInicio());
            reservaDTO.setFechaFin(reserva.getFechaFin());
            reservaDTO.setCantidad(reserva.getReservaJuegos().get(0).getCantidad());
            reservaDTOS.add(reservaDTO);

        }
        usuarioDTO.setReservasDTO(reservaDTOS);
        return usuarioDTO;
    }
}
