package com.example.progetto.service;

import com.example.progetto.DTO.UtenteDTO;
import com.example.progetto.entities.Utente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.progetto.repository.UtenteRepository;

import java.util.List;
import java.util.Optional;

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
        return utenteRepository.findById(id).get();
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
    @Transactional
    public void deleteUtenteById(Long id) {
        utenteRepository.deleteById(id);
    }

    @Transactional
    public UtenteDTO modificaUtente(long id, UtenteDTO utenteModificato){
        Utente u= utenteRepository.findById(id).orElse(null);

        if (u != null) {
            if (utenteModificato.getEmail() != null && !utenteModificato.getEmail().isEmpty()) {
                u.setEmail(utenteModificato.getEmail());
            }
            if (utenteModificato.getNome() != null && !utenteModificato.getNome().isEmpty()) {
                u.setNome(utenteModificato.getNome());
            }
            if (utenteModificato.getPassword() != null && !utenteModificato.getPassword().isEmpty()) {
                u.setPassword(passwordEncoder.encode(utenteModificato.getPassword()));
            }
            if (utenteModificato.getIndirizzo() != null && !utenteModificato.getIndirizzo().isEmpty()) {
                u.setIndirizzo(utenteModificato.getIndirizzo());
            }
            if (utenteModificato.getCognome() != null && !utenteModificato.getCognome().isEmpty()) {
                u.setCognome(utenteModificato.getCognome());
            }
            utenteRepository.save(u);
            return utenteModificato;
        } else {//utnte nonntrovato
            return null;
        }
    }


}

