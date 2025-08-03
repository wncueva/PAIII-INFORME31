// src/main/java/com/example/tareainforme31/service/PacienteService.java

package com.example.tareainforme31.service;

import com.example.tareainforme31.model.Paciente;
import com.example.tareainforme31.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepo;

    // Obtener todos los pacientes
    public List<Paciente> getAllPacientes() {
        return pacienteRepo.findAll();
    }

    // Guardar o actualizar un paciente con validación
    public Paciente savePaciente(Paciente paciente) {
        if (paciente.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
        return pacienteRepo.save(paciente);
    }

    // Buscar un paciente por ID
    public Optional<Paciente> getPacienteById(Integer id) {
        return pacienteRepo.findById(id);
    }

    // Eliminar un paciente por ID
    public void deletePaciente(Integer id) {
        pacienteRepo.deleteById(id);
    }
}