package com.example.tareainforme31.service;

// src/main/java/com/example/tareainforme31/service/MedicoService.java


import com.example.tareainforme31.model.Medico;
import com.example.tareainforme31.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    public Medico getMedicoById(Integer id) {
        return medicoRepository.findById(id).orElse(null);
    }

    public Medico saveMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void deleteMedico(Integer id) {
        medicoRepository.deleteById(id);
    }
}