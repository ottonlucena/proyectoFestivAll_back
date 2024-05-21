package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuario(Long id) throws GlobalNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()){
            throw new GlobalNotFoundException("Usuario no encontrado");
        }
        return usuarioOptional;
    }
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
    @Transactional
    public Usuario actualizarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    @Transactional
    public boolean eliminarUsuario(Long id){
        try{
            usuarioRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Transactional
    public Optional<Usuario> buscarUsuarioRut(String rut){
        return usuarioRepository.findByRut(rut);
    }


}
