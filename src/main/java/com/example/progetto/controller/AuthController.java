package com.example.progetto.controller;

import com.example.progetto.Config.AuthenticationService;
import com.example.progetto.entities.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public final class AuthController {

    @Autowired
    private AuthenticationService authService;




    @PostMapping("/api/login")
    public ResponseEntity<?> accesso(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // Esegui l'autenticazione e generazione del token JWT
        AuthenticationService.JwtAuthenticationResponse jwtResponse = authService.accesso(email, password);

        if (jwtResponse != null) {
            return ResponseEntity.ok(jwtResponse);
        } else {
            // Ritorna una risposta di errore se l'accesso non è riuscito
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non riuscito");
        }


    }
    @PostMapping("/api/registrazione")
    public ResponseEntity<?> registrazione(@RequestBody Utente utente) {
        // Esegui la registrazione dell'utente
        AuthenticationService.JwtAuthenticationResponse jwtResponse = authService.registrazione(utente);

        if (jwtResponse != null) {
            return ResponseEntity.ok(jwtResponse);
        } else {
            // Ritorna una risposta di errore se la registrazione non è riuscita
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registrazione non riuscita");
        }
    }

}

