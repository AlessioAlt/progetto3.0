package com.example.progetto.DTO;


import com.example.progetto.entities.Prodotto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoDTO {
    private int id;
    private String modello;
    private String marca;
    private String taglia;
    private String nome;
    private int quantita;
    private double prezzo;
    private String immagine;

    public ProdottoDTO(Prodotto prodotto) {
        this.id=prodotto.getId();
        this.modello = prodotto.getModello();
        this.marca = prodotto.getMarca();
        this.taglia = prodotto.getTaglia();
        this.nome = prodotto.getNome();
        this.quantita = prodotto.getQuantita();
        this.prezzo = prodotto.getPrezzo();
        this.immagine = prodotto.getImmagine();
    }




}
