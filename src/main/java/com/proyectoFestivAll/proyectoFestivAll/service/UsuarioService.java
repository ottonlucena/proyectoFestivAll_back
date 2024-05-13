package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario actualizarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public boolean eliminarUsuario(Long id){
        try{
            usuarioRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }


}
