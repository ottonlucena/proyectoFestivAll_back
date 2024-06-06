package com.proyectoFestivAll.proyectoFestivAll.controller;

import com.proyectoFestivAll.proyectoFestivAll.entity.Caracteristica;
import com.proyectoFestivAll.proyectoFestivAll.exception.CaracteristicaDuplicadaException;
import com.proyectoFestivAll.proyectoFestivAll.service.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
@CrossOrigin(origins = "http://localhost:5173")
public class CaracteristicaController {

    @Autowired
    CaracteristicaService caracteristicaService;

    @GetMapping
    public List<Caracteristica> getAllCaracteristicas(){
        return caracteristicaService.getAllCaracteristicas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caracteristica> getCaracteristicaById(@PathVariable Long id){
        Caracteristica caracteristica = caracteristicaService.getCaracteristicaById(id);
        return ResponseEntity.ok(caracteristica);
    }

    @PostMapping
    public ResponseEntity<Caracteristica> createCaracteristica(@RequestBody Caracteristica caracteristica){
        try {
            Caracteristica savedCaracteristica = caracteristicaService.saveCaracteristica(caracteristica);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCaracteristica);
        }catch (CaracteristicaDuplicadaException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Caracteristica> updateCaracteristica(@PathVariable Long id, @RequestBody Caracteristica caracteristica){
        try {
            caracteristica.setId(id);
            Caracteristica updateCaracteristica = caracteristicaService.saveCaracteristica(caracteristica);
            return ResponseEntity.ok(updateCaracteristica);
        }catch (CaracteristicaDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    @DeleteMapping("{id}")
    public void deleteCaracteristica(@PathVariable Long id){
          caracteristicaService.deleteCaracteristica(id);
    }
}
