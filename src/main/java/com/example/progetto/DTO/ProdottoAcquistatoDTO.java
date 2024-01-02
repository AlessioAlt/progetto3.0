package com.example.progetto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoAcquistatoDTO {
    private long  idAcquisto;
    private double prezzoProdotto;
    private int quantitaProdotto;
    private Date data;
    private String emailUtente;
    private String nomeProdotto, marcaProdotto, tagliaProdotto;


}
