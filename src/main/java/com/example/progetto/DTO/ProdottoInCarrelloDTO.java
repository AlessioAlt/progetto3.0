package com.example.progetto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoInCarrelloDTO {
    private ProdottoDTO prodotto;
    private int quantita;
}
