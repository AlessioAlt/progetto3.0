package com.example.progetto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoCarrello {

    private String nome;
    private String marca;
    private String taglia;
    private int quantita;
}