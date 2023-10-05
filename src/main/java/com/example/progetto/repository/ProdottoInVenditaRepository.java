package com.example.progetto.repository;

import com.example.progetto.entities.Acquisto;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.ProdottoInVendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoInVenditaRepository extends JpaRepository<ProdottoInVendita, Long> {

    // Trova tutti i prodotti in vendita associati a un determinato prodotto
    List<ProdottoInVendita> findByProdotto(Prodotto prodotto);

    // Trova tutti i prodotti in vendita associati a un determinato acquisto
    List<ProdottoInVendita> findByAcquisto(Acquisto acquisto);

    // Trova tutti i prodotti in vendita associati a un determinato prodotto in un dato acquisto
    ProdottoInVendita findByProdottoAndAcquisto(Prodotto prodotto, Acquisto acquisto);


}

