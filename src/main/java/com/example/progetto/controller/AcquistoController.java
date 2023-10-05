package com.example.progetto.controller;

import com.example.progetto.entities.Acquisto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.progetto.repository.AcquistoRepository;

@RestController
@RequestMapping("/acquisti")
public class AcquistoController {

    private final AcquistoRepository acquistoRepository;

    @Autowired
    public AcquistoController(AcquistoRepository acquistoRepository) {
        this.acquistoRepository = acquistoRepository;
    }

    // Metodo per ottenere tutti gli acquisti
    @GetMapping
    public ResponseEntity<?> getAllAcquisti() {
        return ResponseEntity.ok(acquistoRepository.findAll());
    }

    // Metodo per ottenere un acquisto dato il suo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAcquistoById(@PathVariable Long id) {
        return ResponseEntity.of(acquistoRepository.findById(id));
    }

    // Metodo per creare un nuovo acquisto
    @PostMapping
    public ResponseEntity<?> createAcquisto(@RequestBody Acquisto nuovoAcquisto) {
        Acquisto savedAcquisto = acquistoRepository.save(nuovoAcquisto);
        return ResponseEntity.ok(savedAcquisto);
    }

    // Metodo per aggiornare un acquisto esistente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAcquisto(@PathVariable Long id, @RequestBody Acquisto acquisto) {
        if (!acquistoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        acquisto.setId(id);
        Acquisto updatedAcquisto = acquistoRepository.save(acquisto);
        return ResponseEntity.ok(updatedAcquisto);
    }

    // Metodo per eliminare un acquisto dato il suo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAcquisto(@PathVariable Long id) {
        if (!acquistoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        acquistoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
