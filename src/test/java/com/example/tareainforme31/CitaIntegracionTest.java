package com.example.tareainforme31;



import com.example.tareainforme31.model.Cita;
import com.example.tareainforme31.model.Medico;
import com.example.tareainforme31.model.Paciente;
import com.example.tareainforme31.repository.CitaRepository;
import com.example.tareainforme31.repository.MedicoRepository;
import com.example.tareainforme31.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CitaIntegracionTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private CitaRepository citaRepository;

    @BeforeEach
    void setUp() {
        // Limpiar datos antes de cada prueba
        citaRepository.deleteAll();
        medicoRepository.deleteAll();
        pacienteRepository.deleteAll();
    }

    @Test
    void debeCrearCitaConPacienteYMedicoValidos() {
        // Dado: un paciente y un médico existentes
        Paciente paciente = new Paciente("Juan", "Pérez", LocalDate.of(1985, 5, 20), "juan.perez@email.com");
        Medico medico = new Medico("Ana", "López", "Pediatría");

        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        Medico medicoGuardado = medicoRepository.save(medico);

        // Cuando: se crea una cita con esos datos
        Cita cita = new Cita();
        cita.setPaciente(pacienteGuardado);
        cita.setMedico(medicoGuardado);
        cita.setFecha(LocalDate.of(2025, 4, 10));
        cita.setHora(LocalTime.of(14, 30));
        cita.setConsultorio("C201");

        Cita citaGuardada = citaRepository.save(cita);

        // Entonces: la cita se guarda correctamente
        assertNotNull(citaGuardada.getId());
        assertEquals("C201", citaGuardada.getConsultorio());
        assertEquals(pacienteGuardado.getId(), citaGuardada.getPaciente().getId());
        assertEquals(medicoGuardado.getId(), citaGuardada.getMedico().getId());
    }

    @Test
    void noDebeCrearCitaConMedicoInexistente() {
        // Dado: un paciente válido, pero sin médico guardado
        Paciente paciente = new Paciente("Luis", "Torres", LocalDate.of(1992, 3, 10), "luis.torres@email.com");
        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        // Cuando: intentamos crear una cita con un médico que no está en la base de datos
        Medico medico = new Medico("Falso", "Doctor", "Fake"); // NO guardado
        Cita cita = new Cita();
        cita.setPaciente(pacienteGuardado);
        cita.setMedico(medico); // Asociamos médico inexistente
        cita.setFecha(LocalDate.now());
        cita.setHora(LocalTime.now());
        cita.setConsultorio("C101");

        // Entonces: debería fallar al guardar la cita
        Exception exception = assertThrows(Exception.class, () -> {
            citaRepository.save(cita);
        });

        // Verificamos que el mensaje contenga palabras clave que indican el error por entidad no persistida
        String mensaje = exception.getMessage().toLowerCase();
        assertTrue(
                mensaje.contains("unsaved") ||
                        mensaje.contains("transient") ||
                        mensaje.contains("foreign key") ||
                        mensaje.contains("cannot add or update")
        );
    }

    @Test
    void debeRecuperarCitaConDatosRelacionados() {
        // Dado: paciente, médico y cita guardados
        Paciente paciente = new Paciente("María", "Gómez", LocalDate.of(1988, 7, 12), "maria.gomez@email.com");
        Medico medico = new Medico("Carlos", "Ruiz", "Cardiología");

        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        Medico medicoGuardado = medicoRepository.save(medico);

        Cita cita = new Cita(pacienteGuardado, medicoGuardado, LocalDate.of(2025, 4, 15), LocalTime.of(9, 0), "C305");
        Cita citaGuardada = citaRepository.save(cita);

        // Cuando: se busca la cita por ID
        Cita citaEncontrada = citaRepository.findById(citaGuardada.getId()).orElse(null);

        // Entonces: los datos relacionados deben cargarse correctamente
        assertNotNull(citaEncontrada);
        assertEquals("María", citaEncontrada.getPaciente().getNombre());
        assertEquals("Carlos", citaEncontrada.getMedico().getNombre());
        assertEquals("C305", citaEncontrada.getConsultorio());
    }
}