package com.example.progetto.repository;

import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {

    Prodotto findById(int id);

    List<Prodotto> findByMarca(String marca);

    List<Prodotto> findByModello(String modello);

    List<Prodotto> findByNomeAndMarca(String nome, String marca);


    List<Prodotto> findByTaglia(String taglia);

    List<Prodotto> findByModelloAndTaglia(String modello, String taglia);

    List<Prodotto> findByMarcaAndTaglia(String modello, String taglia);

    @Query("select p from Prodotto p where p.prezzo < ?1")
    List<Prodotto> findBysottoIlPrezzo(double prezzo);


}
