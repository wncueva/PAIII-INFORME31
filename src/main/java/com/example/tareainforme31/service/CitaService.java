package com.example.tareainforme31.service;

// src/main/java/com/example/tareainforme31/service/CitaService.java


import com.example.tareainforme31.model.Cita;
import com.example.tareainforme31.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Cita getCitaById(Integer id) {
        return citaRepository.findById(id).orElse(null);
    }

    // En CitaService.java

    public Cita saveCita(Cita cita) {
        LocalTime hora = cita.getHora();

        if (hora.isBefore(LocalTime.of(8, 0)) || hora.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("La hora debe estar entre las 08:00 y 18:00");
        }

        return citaRepository.save(cita);
    }

    public void deleteCita(Integer id) {
        citaRepository.deleteById(id);
    }
}
