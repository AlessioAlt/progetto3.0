package com.example.progetto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailProdottiRequest {

    private String email;
    private List<DettaglioProdottoQntDTO> prodotti;
}
