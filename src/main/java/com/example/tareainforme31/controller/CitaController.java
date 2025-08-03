package com.example.tareainforme31.controller;

// src/main/java/com/example/tareainforme31/controller/CitaController.java


import com.example.tareainforme31.model.Cita;
import com.example.tareainforme31.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Integer id) {
        Cita cita = citaService.getCitaById(id);
        return cita != null ? ResponseEntity.ok(cita) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Cita createCita(@RequestBody Cita cita) {
        return citaService.saveCita(cita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Integer id, @RequestBody Cita citaDetails) {
        Cita cita = citaService.getCitaById(id);
        if (cita != null) {
            cita.setPaciente(citaDetails.getPaciente());
            cita.setMedico(citaDetails.getMedico());
            cita.setFecha(citaDetails.getFecha());
            cita.setHora(citaDetails.getHora());
            cita.setConsultorio(citaDetails.getConsultorio());
            return ResponseEntity.ok(citaService.saveCita(cita));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Integer id) {
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}
