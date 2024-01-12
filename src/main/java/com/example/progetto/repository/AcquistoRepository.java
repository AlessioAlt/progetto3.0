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




}

