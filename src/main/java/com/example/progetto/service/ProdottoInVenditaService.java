package com.example.progetto.service;

import com.example.progetto.entities.Acquisto;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.ProdottoInVendita;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.progetto.repository.ProdottoInVenditaRepository;

import java.util.List;

@Service
@Transactional
public class ProdottoInVenditaService {


    @Autowired
    private ProdottoInVenditaRepository rep;

    public List<ProdottoInVendita> getProdottiInVenditaByProdotto(Prodotto prodotto) {
        return rep.findByProdotto(prodotto);
    }

    public List<ProdottoInVendita> getProdottiInVenditaByAcquisto(Acquisto acquisto) {
        return rep.findByAcquisto(acquisto);
    }

    public ProdottoInVendita getProdottoInVenditaByProdottoAndAcquisto(Prodotto prodotto, Acquisto acquisto) {
        return rep.findByProdottoAndAcquisto(prodotto, acquisto);
    }


}

