package com.example.tareainforme31.repository;

// src/main/java/com/example/tareainforme31/repository/MedicoRepository.java


import com.example.tareainforme31.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {
}