package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Juego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Reserva;
import com.proyectoFestivAll.proyectoFestivAll.entity.ReservaJuego;
import com.proyectoFestivAll.proyectoFestivAll.entity.Usuario;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.ReservaDTO;
import com.proyectoFestivAll.proyectoFestivAll.entity.dto.UsuarioDTO;
import com.proyectoFestivAll.proyectoFestivAll.exception.GlobalNotFoundException;
import com.proyectoFestivAll.proyectoFestivAll.exception.InsufficientQuantityException;
import com.proyectoFestivAll.proyectoFestivAll.exception.UsuarioNoEncontradoException;
import com.proyectoFestivAll.proyectoFestivAll.repository.ReservaRepository;
import com.proyectoFestivAll.proyectoFestivAll.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
   private JuegoService juegoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    //METODOS INTERNOS
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

    public UsuarioDTO createReserva(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con correo: " + usuarioDTO.getEmail() + " no encontrado"));

        if (usuarioDTO.getReservasDTO() == null) {
            usuarioDTO.setReservasDTO(new ArrayList<>());
        }

        List<Reserva> reservaList = new ArrayList<>();
        for (ReservaDTO reservaDTO : usuarioDTO.getReservasDTO()) {
            Reserva reserva = new Reserva();
            reserva.setFechaInicio(reservaDTO.getFechaInicio());
            reserva.setFechaFin(reservaDTO.getFechaFin());
            reserva.setUsuario(usuario);

            List<Juego> juegos = juegoService.buscarJuegoPorNombre(reservaDTO.getTituloJuego());
            if (juegos.isEmpty()) {
                throw new EntityNotFoundException("El juego con título " + reservaDTO.getTituloJuego() + " no existe");
            }

            Juego juego = juegos.get(0);
            verificarDisponibilidad(juego, reserva, reservaDTO.getCantidad());

            ReservaJuego reservaJuego = new ReservaJuego();
            reservaJuego.setJuego(juego);
            reservaJuego.setReserva(reserva);
            reservaJuego.setCantidad(reservaDTO.getCantidad()); // Asegúrate de asignar la cantidad aquí
            reserva.getReservaJuegos().add(reservaJuego);

            reservaList.add(reserva);
        }

        // Asignar lista de reservas al usuario
        usuario.getReservas().addAll(reservaList);

        // Guardar las reservas (esto también actualizará el usuario debido a la cascada)
        usuario = usuarioRepository.save(usuario);



        return convertirAUsuarioDTO(usuario);
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
