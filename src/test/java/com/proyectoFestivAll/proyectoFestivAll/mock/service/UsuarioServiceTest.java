package com.proyectoFestivAll.proyectoFestivAll.mock.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.service.UsuarioService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(UsuarioService.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private UsuarioService usuarioService;

    @Test
    @Transactional
    @Order(1)
    public void testGuardarUsuario() {
        logger.info("Iniciando testGuardarUsuario");
        //Given
        Usuario newUsuario = new Usuario(1L, "555321-5","Jose","Lugo","12345","jose@gmail.com","diego 123", "12345", null);

        //When
        Usuario savedUsuario = usuarioService.guardarUsuario(newUsuario);

        //Then
        List<Usuario> usuarioList = usuarioService.listarUsuarios();
        assertNotNull(savedUsuario);
        assertTrue(usuarioList.contains(savedUsuario));
        assertEquals("Jose", savedUsuario.getNombre());
        assertEquals("Lugo", savedUsuario.getApellido());

    }

    @Test
    @Transactional(readOnly = true)
    @Order(2)
    public void testListarUsuarios() {
        logger.info("Iniciando testListarUsuarios");

        //When
        List<Usuario> result = usuarioService.listarUsuarios();

        //Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("Perez", result.get(0).getApellido());
        logger.info(result.get(1).getEmail());


    }

}


