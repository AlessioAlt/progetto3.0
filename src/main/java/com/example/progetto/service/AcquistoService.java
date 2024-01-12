package com.example.progetto.service;

import com.example.progetto.DTO.ProdottoAcquistatoDTO;
import com.example.progetto.DTO.DettaglioProdottoQntDTO;
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


    //genera acquisto
    @Transactional//(rollbackOn = )
    public Acquisto generaAcquisto(Utente utente, List<DettaglioProdottoQntDTO> dettProdList) {
        // Crea obj  acquisto
        //("prima di creare acquisto");
        Date dataAcquisto = new Date(System.currentTimeMillis());
        Acquisto nuovoAcquisto= new Acquisto();
        nuovoAcquisto.setData(dataAcquisto);
        nuovoAcquisto.setUtente(utente);
        acquistoRepository.save(nuovoAcquisto);
        //("acquisto generato e salvato");


        for (DettaglioProdottoQntDTO prodottoInAcquisto : dettProdList) {

            Prodotto prodotto = prodottoService.getProdottoByNomeAndMarcaAndTaglia(
                    prodottoInAcquisto.getNome(),
                    prodottoInAcquisto.getMarca(),
                    prodottoInAcquisto.getTaglia()
            );
            int quantAttuale= prodottoService.getQuantitaByNomeMarcaTaglia(prodotto.getNome(),
                    prodotto.getMarca(),
                    prodotto.getTaglia());

            // Verifica la disponibilità del prodotto
            if (prodotto != null && quantAttuale>= prodottoInAcquisto.getQuantita()) {

                ProdottoInVendita prodottoInVendita = new ProdottoInVendita();
                prodottoInVendita.setProdotto(prodotto);
                prodottoInVendita.setAcquisto(nuovoAcquisto);
                prodottoInVendita.setQuantita(prodottoInAcquisto.getQuantita());
                prodottoService.setQuantitaByNomeMarcaTaglia(prodotto.getNome(),
                        prodotto.getMarca(),
                        prodotto.getTaglia(), (quantAttuale- prodottoInAcquisto.getQuantita()));
                prodottoInVenditaRepository.save(prodottoInVendita);
            } else {

                throw new IllegalArgumentException();
            }

        }
        return nuovoAcquisto;
    }





}
