package com.example.tareainforme31.service;

// src/main/java/com/example/tareainforme31/service/ConsultorioService.java


import com.example.tareainforme31.model.Consultorio;
import com.example.tareainforme31.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public List<Consultorio> getAllConsultorios() {
        return consultorioRepository.findAll();
    }

    public Consultorio getConsultorioById(Integer id) {
        return consultorioRepository.findById(id).orElse(null);
    }

    public Consultorio saveConsultorio(Consultorio consultorio) {
        return consultorioRepository.save(consultorio);
    }

    public void deleteConsultorio(Integer id) {
        consultorioRepository.deleteById(id);
    }
}