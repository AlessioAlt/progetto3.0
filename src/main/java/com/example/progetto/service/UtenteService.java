package com.example.progetto.service;

import com.example.progetto.entities.Utente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.progetto.repository.UtenteRepository;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }

    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Utente addUtente(Utente utente) {
        // Crittografa la password prima di salvarla
        String rawPassword = utente.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        utente.setPassword(encodedPassword);
        return utenteRepository.save(utente);
    }

    public void deleteUtenteById(Long id) {
        utenteRepository.deleteById(id);
    }

    @Transactional
    public void modificaMail(Long id, String nuovaMail) {
        Utente u = utenteRepository.findById(id).orElse(null);
        if (u != null) {
            u.setEmail(nuovaMail);
        }
    }
}

