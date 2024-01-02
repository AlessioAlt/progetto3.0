package com.example.progetto.Config;

import com.example.progetto.entities.Utente;
import com.example.progetto.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtenteRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse registrazione(Utente u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        User.UserBuilder userBuilder = User.builder()
                .username(u.getEmail())
                .password(u.getPassword())
                .authorities(ruoli(u.isRuolo()));

        UserDetails userDetails = userBuilder.build();


        userRepository.save(u);

        String jwt = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponse(jwt);
    }

    private List<GrantedAuthority> ruoli(boolean isAdmin) {
        List<GrantedAuthority> ruoli = new ArrayList<>();
        ruoli.add(new SimpleGrantedAuthority(isAdmin ? "ADMIN" : "USER"));
        return ruoli;
    }


    public JwtAuthenticationResponse accesso(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponse(jwt);
    }

    public static class JwtAuthenticationResponse implements Serializable {
        private static final long serialVersionUID = 1250166508152483573L;
        private final String token;

        public JwtAuthenticationResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }



}
