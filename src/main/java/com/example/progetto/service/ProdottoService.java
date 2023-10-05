package com.example.progetto.service;

import com.example.progetto.entities.Prodotto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.progetto.repository.ProdottoRepository;

import java.util.List;
import java.util.Optional;

@jakarta.transaction.Transactional
@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    public List<Prodotto> getAllProdotti() {
        return prodottoRepository.findAll();
    }

    public Prodotto getProdottoById(int id) {
        return prodottoRepository.findById(id);
    }

    public Prodotto saveProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public void deleteProdottoById(int id) {
        prodottoRepository.deleteById(id);
    }

    public List<Prodotto> getProdottoByModello(String modello){ return prodottoRepository.findByModello(modello);}
    public Prodotto modificaPrezzo(int  id, double nuovoPrezzo) {
        Prodotto prodotto = prodottoRepository.findById(id);
        if (prodotto != null) {
            prodotto.setPrezzo(nuovoPrezzo);
            return prodottoRepository.save(prodotto);
        }
        return null;
    }

    @Transactional
    public Prodotto modificaQuantita(int id, int nuovaQuantita) {
        Prodotto p = prodottoRepository.findById(id);
        p.setQuantita(nuovaQuantita);
        return p;
    }

    public List<Prodotto> getProdottiPerNomeEMarca(String nome, String marca){
        return  prodottoRepository.findByNomeAndMarca(nome, marca);

    }


}
