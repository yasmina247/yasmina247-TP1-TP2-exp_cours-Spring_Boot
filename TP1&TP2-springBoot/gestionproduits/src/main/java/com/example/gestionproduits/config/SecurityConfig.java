
package com.example.gestionproduits.config;

import com.example.gestionproduits.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomUserDetailsService userDetailsService) throws Exception {

        http
            .userDetailsService(userDetailsService)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login",
                    "/access-denied",
                    "/css/**"
                ).permitAll()

                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                .requestMatchers("/stocks/**")
                    .hasAnyRole("ADMIN", "PHARMACIEN")

                .requestMatchers("/ventes/**")
                    .hasAnyRole("ADMIN", "PHARMACIEN", "CAISSIER")

                .requestMatchers(HttpMethod.GET,
                    "/produits", "/produits/**",
                    "/api/produits", "/api/produits/**")
                    .hasAnyRole("ADMIN", "PHARMACIEN", "CLIENT")

                .requestMatchers(HttpMethod.POST,
                    "/api/produits", "/api/produits/**")
                    .hasAnyRole("ADMIN", "PHARMACIEN")

                .requestMatchers(HttpMethod.PUT,
                    "/api/produits", "/api/produits/**")
                    .hasAnyRole("ADMIN", "PHARMACIEN")

                .requestMatchers(HttpMethod.DELETE,
                    "/api/produits", "/api/produits/**")
                    .hasRole("ADMIN")

                .requestMatchers("/api/**")
                    .authenticated()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }
}
