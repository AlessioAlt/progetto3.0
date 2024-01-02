package com.example.progetto.repository;

import com.example.progetto.entities.Acquisto;
import com.example.progetto.entities.ProdottoInVendita;
import com.example.progetto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto, Long> {
    // Trova tutti gli acquisti di un determinato utente
    List<Acquisto> findByUtente(Utente utente);

    // Trova tutti gli acquisti effettuati in una data specifica
    List<Acquisto> findByData(Date data);

    // Trova tutti gli acquisti di un determinato utente in una data specifica
    List<Acquisto> findByUtenteAndData(Utente utente, Date data);

    // Trova tutti gli acquisti che contengono un certo prodotto in vendita
    List<Acquisto> findByProdottoInVenditaListContaining(ProdottoInVendita prodotto);

    // Trova tutti gli acquisti effettuati da un determinato utente che contengono un certo prodotto in vendita
    List<Acquisto> findByUtenteAndProdottoInVenditaListContaining(Utente utente, ProdottoInVendita prodotto);


}

