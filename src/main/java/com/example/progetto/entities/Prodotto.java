package com.example.progetto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prodotto")
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "modello", nullable = false)
    private String modello;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "taglia", nullable = false)
    private String taglia;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "quantita", nullable = false)
    private int quantita;

    @Column(name = "prezzo", nullable = false)
    private double prezzo;

    @Column(name = "immagine")
    private String immagine;

    @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "prodottoInVenditaList")
    private List<ProdottoInVendita> prodottoInVenditaList;

}
