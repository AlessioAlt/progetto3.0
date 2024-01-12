package com.example.progetto.controller;

import com.example.progetto.DTO.EmailProdottiRequest;
import com.example.progetto.DTO.DettaglioProdottoQntDTO;
import com.example.progetto.DTO.ProdottoInCarrelloDTO;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.service.CarrelloService;
import com.example.progetto.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableMethodSecurity
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/carrello")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;
    @Autowired
    private ProdottoService prodottoService;

    @PreAuthorize("#email == authentication.principal.username")
    @GetMapping("/prodotti")
    public ResponseEntity<?> getProdottiInCarrello(@RequestParam(name = "email") String email) {
        try {
            List<ProdottoInCarrelloDTO> prodotti = carrelloService.getProdottiInCarrelloByUtente(email);
            return new ResponseEntity<>(prodotti, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("#aggiunta.getEmail() == authentication.principal.username")
    @PostMapping("/aggiungiProdotto")
    public ResponseEntity<?> aggiungiProdottoAlCarrello(@RequestBody EmailProdottiRequest aggiunta) {

        try {
            DettaglioProdottoQntDTO pc= aggiunta.getProdotti().get(0);  //ce ne sara solo uno che sara in pos ZERO, a
                                                                   //una richiesta conterrà sempre un solo prodotto

            Prodotto prodotto= prodottoService.getProdottoByNomeAndMarcaAndTaglia(pc.getNome(),pc.getMarca(), pc.getTaglia());
            carrelloService.aggiungiProdottoAlCarrello(aggiunta.getEmail(), prodotto, pc.getQuantita());
            return new ResponseEntity<>(aggiunta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("#elimina.getEmail() == authentication.principal.username")
    @DeleteMapping("/rimuoviProdotto")
    public ResponseEntity<?> rimuoviProdottoDalCarrello(@RequestBody EmailProdottiRequest elimina) {
        try {
            DettaglioProdottoQntDTO pc= elimina.getProdotti().get(0);  //ce ne sara solo uno che sara in pos ZERO
            Prodotto prodotto= prodottoService.getProdottoByNomeAndMarcaAndTaglia(pc.getNome(),pc.getMarca(), pc.getTaglia());
            carrelloService.rimuoviProdottoDalCarrello(elimina.getEmail(), prodotto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("#email == authentication.principal.username")
    @DeleteMapping("/svuotaCarrello")
    public ResponseEntity<?> svuotaCarrello(@RequestParam String email) {
        try {
            carrelloService.svuotaCarrello(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
