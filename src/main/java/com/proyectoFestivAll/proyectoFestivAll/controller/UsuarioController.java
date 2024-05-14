package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarUsuario(@RequestBody Usuario usuario){
             Optional<Usuario> usuarioBuscado = usuarioService.buscarUsuario(usuario.getId());
        if (usuarioBuscado.isPresent()){
            usuarioService.actualizarUsuario(usuario);
            return ResponseEntity.ok("Usuario con id: " + usuario.getId() + " actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id){
        return usuarioService.buscarUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        boolean usuarioEliminado = usuarioService.eliminarUsuario(id);
        if (usuarioEliminado) {
            return ResponseEntity.ok("Usuario eliminado");
        }else {
            return ResponseEntity.notFound().build();
        }
    }


}
