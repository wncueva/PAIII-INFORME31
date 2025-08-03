package com.example.tareainforme31.model;

// src/main/java/com/example/tareainforme31/model/Consultorio.java


import jakarta.persistence.*;

@Entity
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero")
    private String numero;

    private Integer piso;

    // Constructor vacío
    public Consultorio() {
    }

    // Constructor con parámetros
    public Consultorio(String numero, Integer piso) {
        this.numero = numero;
        this.piso = piso;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }
}
