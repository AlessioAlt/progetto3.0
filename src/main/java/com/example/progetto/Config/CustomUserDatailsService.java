package com.example.progetto.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.progetto.entities.Utente;
import com.example.progetto.repository.UtenteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDatailsService implements UserDetailsService {

    private final UtenteRepository userRepository;

    @Autowired
    public CustomUserDatailsService(UtenteRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Uso l'email per identificare l'utente
        Utente utente = userRepository.findByEmail(email);

        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato con l'email: " + email);
        }

        // Definisco le autorizzazioni (ruoli) dell'utente
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (utente.isRuolo()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }

        return new User(utente.getEmail(), utente.getPassword(), authorities);
    }






}

