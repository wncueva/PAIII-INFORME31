package com.example.tareainforme31.controller;

// src/main/java/com/example/tareainforme31/controller/ConsultorioController.java


import com.example.tareainforme31.model.Consultorio;
import com.example.tareainforme31.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping
    public List<Consultorio> getAllConsultorios() {
        return consultorioService.getAllConsultorios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultorio> getConsultorioById(@PathVariable Integer id) {
        Consultorio consultorio = consultorioService.getConsultorioById(id);
        return consultorio != null ? ResponseEntity.ok(consultorio) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Consultorio createConsultorio(@RequestBody Consultorio consultorio) {
        return consultorioService.saveConsultorio(consultorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> updateConsultorio(@PathVariable Integer id, @RequestBody Consultorio consultorioDetails) {
        Consultorio consultorio = consultorioService.getConsultorioById(id);
        if (consultorio != null) {
            consultorio.setNumero(consultorioDetails.getNumero());
            consultorio.setPiso(consultorioDetails.getPiso());
            return ResponseEntity.ok(consultorioService.saveConsultorio(consultorio));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultorio(@PathVariable Integer id) {
        consultorioService.deleteConsultorio(id);
        return ResponseEntity.noContent().build();
    }
}
