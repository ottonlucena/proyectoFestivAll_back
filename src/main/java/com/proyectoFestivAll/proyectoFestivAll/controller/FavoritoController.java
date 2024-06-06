package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Favorito;
import com.proyectoFestivAll.proyectoFestivAll.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favoritos")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FavoritoController {

    private final FavoritoService favoritoService;

    @PostMapping
    public Favorito guardarYDevolverFavorito(@RequestBody Favorito favorito) {
        return favoritoService.guardarFavorito(favorito);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Favorito> obtenerFavoritosPorUsuario(@PathVariable Long usuarioId) {
        return favoritoService.obtenerFavoritosPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/juego/{juegoId}")
    public Optional<Favorito> obtenerFavorito(@PathVariable Long usuarioId, @PathVariable Long juegoId) {
        return favoritoService.obtenerFavorito(usuarioId, juegoId);
    }
}
