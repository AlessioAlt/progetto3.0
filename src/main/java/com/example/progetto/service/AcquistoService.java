package com.example.progetto.service;

import com.example.progetto.DTO.IncrementaProdottoDTO;
import com.example.progetto.DTO.ProdottoAcquistatoDTO;
import com.example.progetto.DTO.ProdottoCarrello;
import com.example.progetto.entities.Acquisto;
import com.example.progetto.entities.Prodotto;
import com.example.progetto.entities.ProdottoInVendita;
import com.example.progetto.entities.Utente;
import com.example.progetto.repository.ProdottoInVenditaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.progetto.repository.AcquistoRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AcquistoService {

    @Autowired
    private AcquistoRepository acquistoRepository;
    @Autowired
    private ProdottoInVenditaRepository prodottoInVenditaRepository;
    @Autowired
    private ProdottoService prodottoService;
    @Autowired
    private UtenteService utenteService;


    public List<Acquisto> getAllAcquisti() {
        return acquistoRepository.findAll();
    }

    public Acquisto getAcquistoById(Long id) {
        return acquistoRepository.findById(id).orElse(null);
    }

    public List<Acquisto> getAcquistiByUtente(Utente u){
        return acquistoRepository.findByUtente(u);
    }

    //metodi relegati a prodottoInVendita

    public List<ProdottoAcquistatoDTO> getProdottiInVenditaByUtente(String email) {
        Utente u= utenteService.getUtenteByEmail(email);
        List<Acquisto> acquisti = acquistoRepository.findByUtente(u);
        List<ProdottoAcquistatoDTO> ret = new ArrayList<>();

        for (Acquisto acquisto : acquisti) {
            List<ProdottoInVendita> prodottiInVendita = prodottoInVenditaRepository.findByAcquisto(acquisto);
            for(ProdottoInVendita p: prodottiInVendita){
                ProdottoAcquistatoDTO pr= new ProdottoAcquistatoDTO();
                pr.setIdAcquisto(acquisto.getId());
                pr.setData(acquisto.getData());
                pr.setEmailUtente(u.getEmail());
                pr.setNomeProdotto(p.getProdotto().getNome());
                pr.setMarcaProdotto(p.getProdotto().getMarca());
                pr.setTagliaProdotto(p.getProdotto().getTaglia());
                pr.setQuantitaProdotto(p.getQuantita());
                pr.setPrezzoProdotto(p.getProdotto().getPrezzo());

                ret.add(pr);
            }
        }
        return ret;
    }

    public List<ProdottoInVendita> getProdottiInVenditaByAcquisto(Acquisto a){
        return prodottoInVenditaRepository.findByAcquisto(a);

    }



    //genera acquisto
    @Transactional
    public Acquisto generaAcquisto(Utente utente, List<ProdottoCarrello> prodottiCarrello) {
        // Crea obj  acquisto
        //("prima di creare acquisto");
        Date dataAcquisto = new Date(System.currentTimeMillis());
        Acquisto nuovoAcquisto= new Acquisto();
        nuovoAcquisto.setData(dataAcquisto);
        nuovoAcquisto.setUtente(utente);
        acquistoRepository.save(nuovoAcquisto);
        //("acquisto generato e salvato");


        for (ProdottoCarrello prodottoCarrello : prodottiCarrello) {

            Prodotto prodotto = prodottoService.getProdottoByNomeAndMarcaAndTaglia(
                    prodottoCarrello.getNome(),
                    prodottoCarrello.getMarca(),
                    prodottoCarrello.getTaglia()
            );
            int quantAttuale= prodottoService.getQuantitaByNomeMarcaTaglia(prodotto.getNome(),
                    prodotto.getMarca(),
                    prodotto.getTaglia());

            // Verifica la disponibilità del prodotto
            if (prodotto != null && quantAttuale>= prodottoCarrello.getQuantita()) {
               // System.out.println("Entro nell'if");
                ProdottoInVendita prodottoInVendita = new ProdottoInVendita();
                prodottoInVendita.setProdotto(prodotto);
                prodottoInVendita.setAcquisto(nuovoAcquisto);
                prodottoInVendita.setQuantita(prodottoCarrello.getQuantita());
                prodottoService.setQuantitaByNomeMarcaTaglia(prodotto.getNome(),
                        prodotto.getMarca(),
                        prodotto.getTaglia(), (quantAttuale-prodottoCarrello.getQuantita()));
                prodottoInVenditaRepository.save(prodottoInVendita);
                //System.out.println(" prod in vendita creato");
            } else {
                System.out.println("sono entrato nell'else di acquisto ");
                throw new IllegalArgumentException();
            }
        }
        System.out.println("esco fuori dal for");
        return nuovoAcquisto;
    }





}
