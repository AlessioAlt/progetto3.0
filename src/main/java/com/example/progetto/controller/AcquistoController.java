package com.example.progetto.controller;


import com.example.progetto.DTO.AcquistoDTO;
import com.example.progetto.DTO.AcquistoRequest;
import com.example.progetto.DTO.ProdottoAcquistatoDTO;
import com.example.progetto.DTO.ProdottoCarrello;
import com.example.progetto.entities.Acquisto;
import com.example.progetto.entities.ProdottoInVendita;
import com.example.progetto.entities.Utente;
import com.example.progetto.service.AcquistoService;
import com.example.progetto.service.UtenteService;
import jakarta.validation.Valid;
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
@RequestMapping("/acquisti")
public class AcquistoController {
    //deve gestire acquisto è prodotto_in_vendita

    @Autowired
    private AcquistoService acquistoService;
    @Autowired
    UtenteService utenteService;


    @PreAuthorize("#acquisto.getEmail() == authentication.principal.username")
    @PostMapping("/generaAcquisto")
    public ResponseEntity<?> generaAcquisto(@RequestBody AcquistoRequest acquisto) {
        Utente utente= utenteService.getUtenteByEmail(acquisto.getEmail());
        List<ProdottoCarrello> prodottiCarrello= acquisto.getProdotti();
       try {
            Acquisto nuovoAcquisto = acquistoService.generaAcquisto(utente, prodottiCarrello);
           AcquistoDTO acquistoDTO= new AcquistoDTO(nuovoAcquisto.getId(), nuovoAcquisto.getData(), nuovoAcquisto.getUtente().getEmail());
            return new ResponseEntity<>(acquistoDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //quantita immessa maggiore a quella disponibile
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //probemi nella creazione dell'acquisto
        }
    }
    @PreAuthorize("hasAuthority('ADMIN') or #email == authentication.principal.username")
    @GetMapping("/acquistiUtente/{email}")
    public ResponseEntity<?> acquistiUtente(@PathVariable String email) {
        try {
            List<ProdottoAcquistatoDTO> prodotti = acquistoService.getProdottiInVenditaByUtente(email);
            return new ResponseEntity<>(prodotti, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
