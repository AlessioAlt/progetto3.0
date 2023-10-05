package com.example.progetto.controller;

import com.example.progetto.Config.AuthenticationService;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.Utente;
import com.example.progetto.service.ProdottoService;
import com.example.progetto.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;


    @GetMapping
    public ResponseEntity<List<Prodotto>> getAllProdotti() {
        List<Prodotto> prodotti = prodottoService.getAllProdotti();
        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prodotto> getProdottoById(@PathVariable int id) {
        Prodotto prodotto = prodottoService.getProdottoById(id);
        if (prodotto != null) {
            return new ResponseEntity<>(prodotto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/nuovoProdotto")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Prodotto> createProdotto(@RequestBody @Valid Prodotto prodotto) {
        Prodotto newProdotto = prodottoService.saveProdotto(prodotto);
        return new ResponseEntity<>(newProdotto, HttpStatus.CREATED);
    }

    @DeleteMapping("eliminaProdotto/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProdotto(@PathVariable int id) {
        prodottoService.deleteProdottoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/modello/{modello}")
    public ResponseEntity<List<Prodotto>> getProdottiByModello(@PathVariable String modello) {
        List<Prodotto> prodotti = prodottoService.getProdottoByModello(modello);
        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }

    @GetMapping("/{nome}/{marca}")
    public ResponseEntity<List<Prodotto>> getTagliaByNomeAndMarca(@PathVariable String nome,@PathVariable String marca){
        List<Prodotto> prodotti= prodottoService.getProdottiPerNomeEMarca(nome, marca);
        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }

    //DA CANCELLARE
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/utenti/{email}")
    @PreAuthorize("#email == authentication.principal.username or hasAuthority('ADMIN')")
    public ResponseEntity<Utente> getByEmail(@PathVariable @Valid String email) {
        Utente utente = utenteService.getUtenteByEmail(email);
        if (utente != null) {
            return new ResponseEntity<>(utente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    private  AuthenticationService authService;


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


}

