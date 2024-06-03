package com.proyectoFestivAll.proyectoFestivAll.service;

import com.proyectoFestivAll.proyectoFestivAll.entity.Caracteristica;
import com.proyectoFestivAll.proyectoFestivAll.exception.CaracteristicaNoEncontradaException;
import com.proyectoFestivAll.proyectoFestivAll.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    public List<Caracteristica> getAllCaracteristicas(){
        return caracteristicaRepository.findAll();
    }

    public Caracteristica getCaracteristicaById(Long id) {
        return caracteristicaRepository.findById(id).orElseThrow(() -> new CaracteristicaNoEncontradaException("Característica no encontrada"));
    }

    @Transactional
    public Caracteristica saveCaracteristica(Caracteristica caracteristica){
        List<Caracteristica> existingCaracteristicas = caracteristicaRepository.findByNombre(caracteristica.getNombre());

        if (!existingCaracteristicas.isEmpty()){
            throw new CaracteristicaNoEncontradaException("Ya existe una característica con el mismo nombre");
        }

        return caracteristicaRepository.save(caracteristica);
    }

    @Transactional
    public void deleteCaracteristica(Long id){
        Caracteristica findCaracteristic = getCaracteristicaById(id);
        caracteristicaRepository.delete(findCaracteristic);
    }

}
