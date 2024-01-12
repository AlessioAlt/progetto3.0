package com.example.progetto.repository;

import com.example.progetto.entities.Carrello;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.ProdottoInCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoInCarrelloRepository extends JpaRepository<ProdottoInCarrello, Long> {

    // Trova i prodotti nel carrello di un determinato utente
    List<ProdottoInCarrello> findByCarrello(Carrello carrello);

    // Trova un prodotto nel carrello di un determinato utente
    ProdottoInCarrello findByCarrelloAndProdotto(Carrello carrello, Prodotto prodotto);

    // Cancella i prodotti nel carrello di un determinato utente
    void deleteByCarrello(Carrello carrello);
}

