package com.example.progetto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acquisto")
public class Acquisto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private Date data;


    @OneToMany(mappedBy = "acquisto", cascade = CascadeType.ALL)
    private List<ProdottoInVendita> prodottoInVenditaList;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

}

