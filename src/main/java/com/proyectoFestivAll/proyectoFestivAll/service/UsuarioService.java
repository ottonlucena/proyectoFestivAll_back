package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.ReservaDTO;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.UsuarioDTO;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    //Metodos internos:
    private UsuarioDTO convertirAUsuarioDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());

        List<ReservaDTO> reservaDTOS = new ArrayList<>();
        for (Reserva reserva : usuario.getReservas()){
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
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuario(Long id) throws GlobalNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            throw new GlobalNotFoundException("Usuario no encontrado");
        }
        return usuarioOptional;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public boolean eliminarUsuario(Long id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }

    @Transactional(readOnly = true)
    public boolean usuarioExiste(String rut) {
        return usuarioRepository.existsByRut(rut);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuariosConReservas() {
        return usuarioRepository.findUsuariosConReservas();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO usuarioAUsuarioDTO(Usuario usuario){
        return convertirAUsuarioDTO(usuario);
    }
}
