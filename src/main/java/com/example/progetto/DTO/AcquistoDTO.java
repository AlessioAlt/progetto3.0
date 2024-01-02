package com.example.progetto.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquistoDTO {
    private Long id;
    private Date data;
    private String mailUtente;


}
