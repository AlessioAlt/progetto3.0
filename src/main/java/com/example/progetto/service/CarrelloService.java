package com.example.progetto.service;

import com.example.progetto.DTO.ProdottoDTO;
import com.example.progetto.DTO.ProdottoInCarrelloDTO;
import com.example.progetto.entities.Carrello;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.ProdottoInCarrello;
import com.example.progetto.entities.Utente;
import com.example.progetto.repository.CarrelloRepository;
import com.example.progetto.repository.ProdottoInCarrelloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;
    @Autowired
    private ProdottoInCarrelloRepository prodottoInCarrelloRepository;
    @Autowired
    private UtenteService utenteService;

    public Carrello getCarrelloByUtente(String email) {
        Utente utente = utenteService.getUtenteByEmail(email);
        return carrelloRepository.findByUtente(utente);
    }

    public List<ProdottoInCarrelloDTO> getProdottiInCarrelloByUtente(String email) {
        Carrello carrello = getCarrelloByUtente(email);
        List<ProdottoInCarrello> prodottoInCarrellos = prodottoInCarrelloRepository.findByCarrello(carrello);
        List<ProdottoInCarrelloDTO> ret= new LinkedList<>();
        for(ProdottoInCarrello p:prodottoInCarrellos){
            Prodotto prd= p.getProdotto();
            ProdottoDTO pDTO= new ProdottoDTO(prd);
            ProdottoInCarrelloDTO prdInKart= new ProdottoInCarrelloDTO();
            prdInKart.setProdotto(pDTO);
            prdInKart.setQuantita(p.getQuantita());
            ret.add(prdInKart);
        }
        return ret;
    }
    @Transactional
    public void aggiungiProdottoAlCarrello(String email, Prodotto prodotto, int quantita) {
        Utente utente = utenteService.getUtenteByEmail(email);
        Carrello carrello = carrelloRepository.findByUtente(utente);

        if (carrello == null) {
            carrello = new Carrello();
            carrello.setUtente(utente);
            carrelloRepository.save(carrello);
        }
        if(prodottoInCarrelloRepository.findByCarrelloAndProdotto(carrello, prodotto)!= null){
            int qnt=prodottoInCarrelloRepository.findByCarrelloAndProdotto(carrello, prodotto).getQuantita();
            prodottoInCarrelloRepository.findByCarrelloAndProdotto(carrello, prodotto).setQuantita(qnt+quantita);
        }else {
            ProdottoInCarrello prodottoInCarrello = new ProdottoInCarrello();
            prodottoInCarrello.setProdotto(prodotto);
            prodottoInCarrello.setCarrello(carrello);
            prodottoInCarrello.setQuantita(quantita);
            prodottoInCarrelloRepository.save(prodottoInCarrello);
        }
    }
    @Transactional
    public void rimuoviProdottoDalCarrello(String email, Prodotto prodotto) {
        Carrello carrello = getCarrelloByUtente(email);
        if (carrello != null) {
            ProdottoInCarrello prodottoInCarrello = prodottoInCarrelloRepository.findByCarrelloAndProdotto(carrello, prodotto);
            if (prodottoInCarrello != null) {
                prodottoInCarrelloRepository.delete(prodottoInCarrello);
            }
        }
    }
    @Transactional
    public void svuotaCarrello(String email) {
        Carrello carrello = getCarrelloByUtente(email);
        if (carrello != null) {
            prodottoInCarrelloRepository.deleteByCarrello(carrello);
        }
    }


}

