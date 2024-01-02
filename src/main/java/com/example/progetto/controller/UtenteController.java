package com.example.progetto.controller;

import com.example.progetto.DTO.ProdottoDTO;
import com.example.progetto.DTO.UtenteDTO;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.Utente;
import com.example.progetto.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@EnableMethodSecurity
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAll() {
        List<Utente> utenti = utenteService.getAllUtenti();
        List<UtenteDTO> utentiDTO= new ArrayList<>(utenti.toArray().length);
        for(Utente u:  utenti){
            utentiDTO.add(new UtenteDTO(u));
        }
        return new ResponseEntity<>(utentiDTO, HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    @PreAuthorize("#email == authentication.principal.username or hasAuthority('ADMIN')")
    public ResponseEntity<?> getByEmail(@PathVariable @Valid String email) {
        Utente utente = utenteService.getUtenteByEmail(email);
        if (utente != null) {
            UtenteDTO utenteDTO= new UtenteDTO(utente);
            return new ResponseEntity<>(utenteDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("modifica/{id}")
    public ResponseEntity<?> modifica(@PathVariable Long id, @RequestBody @Valid UtenteDTO updatedUtente) {
        Utente utente = utenteService.getUtenteById(id); //vedo se l'utente con quel determinato id esiste
        if (utente != null) {
            UtenteDTO updated = utenteService.modificaUtente(id, updatedUtente);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("elimina/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Utente utente = utenteService.getUtenteById(id);
        if (utente != null) {
            utenteService.deleteUtenteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ruolo/{email}")
    @PreAuthorize("#email == authentication.principal.username or hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> getRuolobyEmail(@PathVariable @Valid String email) {
        Utente utente = utenteService.getUtenteByEmail(email);
        if (utente != null) {
            return new ResponseEntity<>(utente.isRuolo(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}



