package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.ReservaJuego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.ReservaDTO;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.UsuarioDTO;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.exception.InsufficientQuantityException;
import com.proyectoFestivAll.proyectoFestivAll.exception.JuegoNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.exception.UsuarioNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.repository.ReservaJuegoRepository;
import com.proyectoFestivAll.proyectoFestivAll.repository.ReservaRepository;
import com.proyectoFestivAll.proyectoFestivAll.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
   private JuegoService juegoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReservaJuegoRepository reservaJuegoRepository;
    @Autowired
    private UsuarioService usuarioService;

    //METODOS INTERNOS


    private void verificarDisponibilidad(Juego juegoExistente, Reserva reserva, int cantidad){
        List<Reserva> reservaList = reservaRepository.findByReservaJuegos_Juego_IdAndFechaInicio(juegoExistente.getId(), reserva.getFechaInicio());
        int cantidadReservada = reservaList.stream()
                .mapToInt(r -> r.getReservaJuegos().stream()
                        .filter(rj -> rj.getJuego().getId().equals(juegoExistente.getId()))
                        .mapToInt(ReservaJuego::getCantidad)
                        .sum())
                .sum();

        if (cantidadReservada + cantidad > juegoExistente.getCantidad()){
            throw new InsufficientQuantityException(("No hay suficiente cantidad de juegos disponibles."));
        }
    }

    // Método para calcular el total y la cantidad de juegos
    private void calcularTotal(Reserva reserva) {
        float total = (float) reserva.getReservaJuegos().stream()
                .mapToDouble(rj -> rj.getJuego().getValorArriendo() * rj.getCantidad())
                .sum();
        reserva.setTotal(total);

        int cantidadJuego = reserva.getReservaJuegos().stream()
                .mapToInt(ReservaJuego::getCantidad)
                .sum();
        reserva.setCantidadJuego(cantidadJuego);
    }

    private Optional<Reserva> buscarReservaExistente(ReservaDTO reservaDTO, Usuario usuario){
        return usuario.getReservas().stream()
                .filter(r -> r.getFechaInicio().equals(reservaDTO.getFechaInicio()) && r.getFechaFin().equals(reservaDTO.getFechaFin()))
                .findFirst();
    }

    private void agregarOActualizarReservaJuego(Reserva reserva,Juego juego, int cantidad){
        Optional<ReservaJuego> reservaJuegoExistente = reserva.getReservaJuegos().stream()
                .filter(rj -> rj.getJuego().getId().equals(juego.getId()))
                        .findFirst();

        if (reservaJuegoExistente.isPresent()){
            ReservaJuego reservaJuego = reservaJuegoExistente.get();
            reservaJuego.setCantidad(reservaJuego.getCantidad() + cantidad);
        }else {
            ReservaJuego reservaJuego = new ReservaJuego();
            reservaJuego.setJuego(juego);
            reservaJuego.setReserva(reserva);
            reservaJuego.setCantidad(cantidad);
            reserva.getReservaJuegos().add(reservaJuego);
        }
    }

    public UsuarioDTO createReserva(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con correo: " + usuarioDTO.getEmail() + " no encontrado"));

        if (usuarioDTO.getReservasDTO() == null) {
            usuarioDTO.setReservasDTO(new ArrayList<>());
        }

        for (ReservaDTO reservaDTO : usuarioDTO.getReservasDTO()) {
            // Buscar si ya existe una reserva con las mismas fechas
            Optional<Reserva> reservaExistente = buscarReservaExistente(reservaDTO, usuario);

            Reserva reserva;
            if (reservaExistente.isPresent()) {
                reserva = reservaExistente.get();
            } else {
                // Crear nueva reserva si no existe una con las mismas fechas
                reserva = new Reserva();
                reserva.setFechaInicio(reservaDTO.getFechaInicio());
                reserva.setFechaFin(reservaDTO.getFechaFin());
                reserva.setUsuario(usuario);
                usuario.getReservas().add(reserva);
            }

            // Encontrar juegos por nombre
            List<Juego> juegos = juegoService.buscarJuegoPorNombre(reservaDTO.getTituloJuego());
            if (juegos.isEmpty()) {
                throw new JuegoNoEncontradoException("Juego no encontrado " + reservaDTO.getTituloJuego());
            }

            for (Juego juego : juegos) {
                // Verificar disponibilidad del juego
                verificarDisponibilidad(juego, reserva, reservaDTO.getCantidad());

                // Verificar si el juego ya está en la reserva
                agregarOActualizarReservaJuego(reserva,juego,reservaDTO.getCantidad());
            }

            // Calcular total y cantidad de juegos
            calcularTotal(reserva);
        }

        // Guardar el usuario con las reservas actualizadas
        usuarioRepository.save(usuario);

        return usuarioService.usuarioAUsuarioDTO(usuario);
    }

    public List<Reserva> reservaList() throws GlobalNotFoundException {
        List<Reserva> reservaList = reservaRepository.findAll();
        if (reservaList.isEmpty()){
            throw new GlobalNotFoundException("No se encontraron reservas");
        }
        return reservaList;
    }

    public List<Juego> JuegosDiponiblesFechas(String nombreJuego, LocalDate fechaInicio, LocalDate fechaFin){
        // Obtener los juegos que coinciden con el nombre
        List<Juego> juegosBuscados = juegoService.buscarJuegoPorNombre(nombreJuego);
        System.out.println("Juegos buscados: " + juegosBuscados);

        // Obtener las reservas en el rango de fechas especificado
        List<Reserva> reservas;
        if (fechaInicio != null && fechaFin != null) {
            reservas = reservaRepository.findByFechaInicioBetween(fechaInicio, fechaFin);
        } else {
            LocalDate now = LocalDate.now();
            LocalDate startOfMonth = now.withDayOfMonth(1);
            LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
            reservas = reservaRepository.findByFechaInicioBetween(startOfMonth, endOfMonth);
        }
        System.out.println("Reservas en el rango de fechas: " + reservas);

        // Identificar los juegos que están completamente reservados
        Set<Long> juegosReservados = reservas.stream()
                .flatMap(reserva -> reserva.getReservaJuegos().stream())
                .collect(Collectors.groupingBy(
                        reservaJuego -> reservaJuego.getJuego().getId(),
                        Collectors.summingInt(ReservaJuego::getCantidad)
                ))
                .entrySet().stream()
                .filter(entry -> {
                    Juego juego = juegoService.buscarJuegoId(entry.getKey());
                    System.out.println("Juego: " + juego);
                    System.out.println("Cantidad reservada: " + entry.getValue());
                    System.out.println("Cantidad disponible: " + juego.getCantidad());
                    return entry.getValue() >= juego.getCantidad();
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        System.out.println("Juegos reservados completamente: " + juegosReservados);

        // Filtrar los juegos buscados para excluir los que están completamente reservados
        List<Juego> juegosDisponibles = juegosBuscados.stream()
                .filter(juego -> !juegosReservados.contains(juego.getId()))
                .collect(Collectors.toList());

        System.out.println("Juegos disponibles: " + juegosDisponibles);
        return juegosDisponibles;
    }

    public void EliminarReserva(Long id){
         reservaRepository.deleteById(id);
    }

    public Reserva buscarReservaId(Long id) throws GlobalNotFoundException{
       return reservaRepository.findById(id)
               .orElseThrow(() -> new GlobalNotFoundException("Reserva con id " + id + " no encontrada"));
    }

}
