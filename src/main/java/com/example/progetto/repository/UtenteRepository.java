package com.example.progetto.repository;

import com.example.progetto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    List<Utente> findByNome(String nome);

    List<Utente> findByCognome(String cognome);

    List<Utente> findByNomeAndCognome(String nome, String cognome);

    Utente findByEmail(String email);

    List<Utente> findByIndirizzo(String indirizzo);

    boolean existsByEmail(String email);
}
