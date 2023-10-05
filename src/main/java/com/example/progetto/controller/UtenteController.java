package com.example.progetto.controller;

import com.example.progetto.entities.Utente;
import com.example.progetto.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@EnableMethodSecurity

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Utente>> getAll() {
        List<Utente> utenti = utenteService.getAllUtenti();
        return new ResponseEntity<>(utenti, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Utente utente) {
        Utente added = utenteService.addUtente(utente);
        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    @PreAuthorize("#email == authentication.principal.username or hasAuthority('ADMIN')")
    public ResponseEntity<Utente> getByEmail(@PathVariable @Valid String email) {
        Utente utente = utenteService.getUtenteByEmail(email);
        if (utente != null) {
            return new ResponseEntity<>(utente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utente> update(@PathVariable Long id, @RequestBody @Valid Utente updatedUtente) {
        Utente utente = utenteService.getUtenteById(id);
        if (utente != null) {
            updatedUtente.setId(id);
            Utente updated = utenteService.addUtente(updatedUtente);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Utente utente = utenteService.getUtenteById(id);
        if (utente != null) {
            utenteService.deleteUtenteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}



