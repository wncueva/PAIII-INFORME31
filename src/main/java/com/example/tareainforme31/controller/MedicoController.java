package com.example.tareainforme31.controller;

// src/main/java/com/example/tareainforme31/controller/MedicoController.java


import com.example.tareainforme31.model.Medico;
import com.example.tareainforme31.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public List<Medico> getAllMedicos() {
        return medicoService.getAllMedicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable Integer id) {
        Medico medico = medicoService.getMedicoById(id);
        return medico != null ? ResponseEntity.ok(medico) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Medico createMedico(@RequestBody Medico medico) {
        return medicoService.saveMedico(medico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> updateMedico(@PathVariable Integer id, @RequestBody Medico medicoDetails) {
        Medico medico = medicoService.getMedicoById(id);
        if (medico != null) {
            medico.setNombre(medicoDetails.getNombre());
            medico.setApellido(medicoDetails.getApellido());
            medico.setEspecialidad(medicoDetails.getEspecialidad());
            return ResponseEntity.ok(medicoService.saveMedico(medico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Integer id) {
        medicoService.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }
}
