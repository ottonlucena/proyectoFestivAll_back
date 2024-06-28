package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.exception.UsuarioNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuarioService.usuarioExiste(usuario.getRut())) {
            return ResponseEntity.badRequest().body("El RUT ya existe: " + usuario.getRut());
        }
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioBuscado = usuarioService.buscarUsuario(usuario.getId());
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuarioBuscado);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioActualizado);
        }catch (UsuarioNoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Long id) {
        try {
            Usuario usuarioBuscado = usuarioService.buscarUsuario(id);
            return ResponseEntity.ok(usuarioBuscado);
        }catch (UsuarioNoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        }catch (UsuarioNoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

    @GetMapping("/rut")
    public ResponseEntity<Usuario> buscarUsuarioRut(@RequestParam("rut") String rut) {
        Optional<Usuario> usuarioBuscado = usuarioService.buscarUsuarioRut(rut);
        if (usuarioBuscado.isPresent()) {
            return ResponseEntity.ok(usuarioBuscado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/reservas")
    public ResponseEntity<List<Usuario>> obtenerUsuariosConReservas() {
        List<Usuario> usuariosConReservas = usuarioService.buscarUsuariosConReservas();
        return ResponseEntity.ok(usuariosConReservas);
    }

}


