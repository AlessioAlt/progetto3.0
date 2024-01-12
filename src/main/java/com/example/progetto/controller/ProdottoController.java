package com.example.progetto.controller;


import com.example.progetto.DTO.IncrementaProdottoDTO;
import com.example.progetto.DTO.ProdottoDTO;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;


    @GetMapping
    public ResponseEntity<?> getAllProdotti() {
        List<Prodotto> prodotti = prodottoService.getAllProdotti();
        List<ProdottoDTO> prodottiDTO= new ArrayList<>(prodotti.toArray().length);
        for(Prodotto p: prodotti){
            prodottiDTO.add(new ProdottoDTO(p));
        }
        return new ResponseEntity<>(prodottiDTO, HttpStatus.OK);
    }

    @GetMapping("/univoci")
    public ResponseEntity<?> getProdottiUnivoci() {
        List<Prodotto> prodotti = prodottoService.getProdUnivoci();
        List<ProdottoDTO> prodottiDTO= new ArrayList<>(prodotti.toArray().length);
        for(Prodotto p: prodotti){
            prodottiDTO.add(new ProdottoDTO(p));
        }
        return new ResponseEntity<>(prodottiDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProdottoById(@PathVariable int id) {
        Prodotto prodotto = prodottoService.getProdottoById(id);
        ProdottoDTO prodottoDTO=new ProdottoDTO(prodotto);
        if (prodotto != null) {
            return new ResponseEntity<>(prodotto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/nuovoProdotto")
    public ResponseEntity<?> createOrUpdateProdotto(@RequestBody @Valid ProdottoDTO prodotto) {

        Prodotto existingProdotto = prodottoService.getProdottoByNomeAndMarcaAndTaglia(prodotto.getNome(), prodotto.getMarca(), prodotto.getTaglia());

        if (existingProdotto != null) {
            // Il prodotto esiste già, quindi aggiorna la quantità
            existingProdotto.setQuantita(existingProdotto.getQuantita() + prodotto.getQuantita());
            Prodotto updatedProdotto = prodottoService.saveProdotto(existingProdotto);
            ProdottoDTO prod= new ProdottoDTO(updatedProdotto); //serve come semplice risposta1
            return new ResponseEntity<>(prod, HttpStatus.OK);
        } else {
            // Il prodotto non esiste, quindi crea un nuovo prodotto
            Prodotto newProdotto = prodottoService.saveProdotto(prodotto);
            ProdottoDTO prod= new ProdottoDTO(newProdotto);
            return new ResponseEntity<>(prod, HttpStatus.CREATED);
        }
    }



    @DeleteMapping("/eliminaProdotto/{id}")
    public ResponseEntity<Void> deleteProdotto(@PathVariable int id) {
        try {
            prodottoService.deleteProdottoById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // In caso di errore durante l'eliminazione, restituisce stato HTTP 500 (Internal Server Error)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/modello/{modello}")
    public ResponseEntity<?> getProdottiByModello(@PathVariable String modello) {
        List<Prodotto> prodotti = prodottoService.getProdottoByModello(modello);
        List<ProdottoDTO> prodottiDTO= new ArrayList<>(prodotti.toArray().length);
        for(Prodotto p: prodotti){
            prodottiDTO.add(new ProdottoDTO(p));
        }
        return new ResponseEntity<>(prodottiDTO, HttpStatus.OK);
    }




    @GetMapping("/{nome}/{marca}")
    public ResponseEntity<?> getTagliaByNomeAndMarca(@PathVariable String nome, @PathVariable String marca) {
        List<Prodotto> prodotti = prodottoService.getProdottiPerNomeEMarca(nome, marca);
        List<ProdottoDTO> prodottiDTO= new ArrayList<>(prodotti.toArray().length);
        for(Prodotto p: prodotti){
            prodottiDTO.add(new ProdottoDTO(p));
        }
        return new ResponseEntity<>(prodottiDTO, HttpStatus.OK);
    }



    @GetMapping("/quantita/{nome}/{marca}/{taglia}")
    public ResponseEntity<Integer> getQuantitaByNomeMarcaTaglia(@PathVariable String nome, @PathVariable String marca, @PathVariable String taglia) {
        int quantita = prodottoService.getQuantitaByNomeMarcaTaglia(nome, marca, taglia);
        return new ResponseEntity<>(quantita, HttpStatus.OK);
    }


    // non usato ma potrebbe essere utile per future implementazioni
    @PostMapping("/modificaProdotto")
    public ResponseEntity<?> modificaProdotto(@RequestBody @Valid ProdottoDTO prodottoDTO) {
        Prodotto prodottoModificato = prodottoService.modificaProdotto(prodottoDTO);

        if (prodottoModificato != null) {
            ProdottoDTO prodottoDTOmodificato = new ProdottoDTO(prodottoModificato);
            return new ResponseEntity<>(prodottoDTOmodificato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    @PostMapping("/incrementoProdotto")
    public ResponseEntity<?> incrementaProdotto(@RequestBody @Valid IncrementaProdottoDTO p) {

        int quantita= prodottoService.getQuantitaByNomeMarcaTaglia(p.getNome(), p.getMarca(), p.getTaglia());
        System.out.println("attuale quantita: "+p.getIncremento()+" per il prod: "+p.getNome());
        Prodotto prodottoModificato = prodottoService.setQuantitaByNomeMarcaTaglia(p.getNome(), p.getMarca(), p.getTaglia(), quantita+p.getIncremento());

        if (prodottoModificato != null) {
            ProdottoDTO prodottoDTOmodificato = new ProdottoDTO(prodottoModificato);
            return new ResponseEntity<>(prodottoDTOmodificato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

