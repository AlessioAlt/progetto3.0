package com.example.progetto.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDatailsService userService;

    @Bean
    public FilterRegistrationBean<CorsFilter> coresFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", corsConfiguration);

        return new FilterRegistrationBean<>(new CorsFilter(source));

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Utilizziamo BCrypt come algoritmo di hashing per la crittografia delle password.
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .csrf(AbstractHttpConfigurer::disable)


                .authorizeHttpRequests((requests) -> requests


                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/api/registrazione")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/api/login")).permitAll()

                        // Gestione accesso utenti
                        .requestMatchers(mvcMatcherBuilder.pattern("/utenti/modifica/{id}")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/utenti")).permitAll()

                        // Altre regole di autorizzazione per prodotti
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/nuovoProdotto")).hasAuthority("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/rimuoviProdotto/{id}")).hasAuthority("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/incrementoProdotto")).hasAuthority("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/modello/{modello}")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/{nome}/{marca}")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/quantita/{nome}/{marca}/{taglia}")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti/univoci")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/prodotti")).permitAll()


                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .httpBasic(Customizer.withDefaults());
        http.cors();


        return http.build();
    }


}