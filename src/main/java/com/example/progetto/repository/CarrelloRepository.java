package com.example.progetto.repository;

import com.example.progetto.entities.Carrello;
import com.example.progetto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Long> {
    // Trova il carrello di un determinato utente
    Carrello findByUtente(Utente utente);
}

