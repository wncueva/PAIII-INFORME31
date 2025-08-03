// src/main/java/com/example/tareainforme31/controller/PacienteController.java

package com.example.tareainforme31.controller;

import com.example.tareainforme31.model.Paciente;
import com.example.tareainforme31.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Obtener todos los pacientes
    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteService.getAllPacientes();
    }

    // Crear nuevo paciente
    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente nuevo = pacienteService.savePaciente(paciente);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Integer id, @RequestBody Paciente pacienteActualizado) {
        Optional<Paciente> existente = pacienteService.getPacienteById(id);
        if (existente.isPresent()) {
            Paciente paciente = existente.get();
            paciente.setNombre(pacienteActualizado.getNombre());
            paciente.setApellido(pacienteActualizado.getApellido());
            paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
            paciente.setEmail(pacienteActualizado.getEmail());
            try {
                Paciente guardado = pacienteService.savePaciente(paciente);
                return ResponseEntity.ok(guardado);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar paciente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Integer id) {
        Optional<Paciente> existente = pacienteService.getPacienteById(id);
        if (existente.isPresent()) {
            pacienteService.deletePaciente(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}