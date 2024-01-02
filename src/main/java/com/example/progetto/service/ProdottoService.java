package com.example.progetto.service;

import com.example.progetto.DTO.ProdottoDTO;
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
    @Transactional
    public Prodotto saveProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    @Transactional
    public Prodotto saveProdotto(ProdottoDTO prodotto){
        Prodotto ret= new Prodotto();
        ret.setNome(prodotto.getNome());
        ret.setMarca(prodotto.getMarca());
        ret.setTaglia(prodotto.getTaglia());
        ret.setModello(prodotto.getModello());
        ret.setImmagine(prodotto.getImmagine());
        ret.setQuantita(prodotto.getQuantita());
        ret.setPrezzo(prodotto.getPrezzo());
        return saveProdotto(ret);

    }
    @Transactional
    public void deleteProdottoById(int id) {
        prodottoRepository.deleteById(id);
    }

    public List<Prodotto> getProdottoByModello(String modello){ return prodottoRepository.findByModello(modello);}



    public List<Prodotto> getProdottiPerNomeEMarca(String nome, String marca){
        return  prodottoRepository.findByNomeAndMarca(nome, marca);

    }

    public Prodotto getProdottoByNomeAndMarcaAndTaglia(String nome, String marca, String taglia){
        return prodottoRepository.findByNomeAndMarcaAndTaglia(nome, marca,taglia);
    }


    @Transactional(readOnly = true) //per prevenire eventuali problemi di consistenza dei dati in lettura sopratutto nella quantità
    public int getQuantitaByNomeMarcaTaglia(String nome, String marca, String taglia) {
        Prodotto prodotto = prodottoRepository.findByNomeAndMarcaAndTaglia(nome, marca, taglia);
        int quantitaTotale = prodotto.getQuantita();
        return quantitaTotale;
    }
    @Transactional
    public Prodotto setQuantitaByNomeMarcaTaglia(String nome, String marca, String taglia, int quantita){
        Prodotto prodotto = prodottoRepository.findByNomeAndMarcaAndTaglia(nome, marca, taglia);
        prodotto.setQuantita(quantita);
        return prodottoRepository.save(prodotto);
    }


    @Transactional
    public Prodotto modificaProdotto(ProdottoDTO prodottoDTO) {
        Prodotto prodotto = prodottoRepository.findById(prodottoDTO.getId());

        if (prodotto!=null) {
            prodotto.setNome(prodottoDTO.getNome());
            prodotto.setMarca(prodottoDTO.getMarca());
            prodotto.setTaglia(prodottoDTO.getTaglia());
            prodotto.setQuantita(prodottoDTO.getQuantita());
            prodotto.setPrezzo(prodottoDTO.getPrezzo());
            prodotto.setImmagine(prodottoDTO.getImmagine());

            // Salva il prodotto aggiornato
            return prodottoRepository.save(prodotto);
        } else {

            return null;
        }
    }





}
