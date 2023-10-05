package com.example.progetto.service;

import com.example.progetto.entities.Acquisto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.progetto.repository.AcquistoRepository;

import java.util.List;

@Service
@Transactional
public class AcquistoService {

    @Autowired
    private AcquistoRepository acquistoRepository;

    public List<Acquisto> getAllAcquisti() {
        return acquistoRepository.findAll();
    }

    public Acquisto getAcquistoById(Long id) {
        return acquistoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Acquisto createAcquisto(Acquisto acquisto) {

        return acquistoRepository.save(acquisto);
    }


}
