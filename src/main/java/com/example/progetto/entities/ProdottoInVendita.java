package com.example.progetto.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProdottoInVendita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;


    @ManyToOne
    @JoinColumn(name = "acquisto_id")
    private Acquisto acquisto;

    @Column
    private int quantita;
}
