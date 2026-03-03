package auto.auto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

@Autowired
private DatabaseUserDetailsService userDetailsService;


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
        .authorizeHttpRequests(auth -> auth
        // 1. ADMIN: Gestione Auto e Categorie
        .requestMatchers("/auto/create", "/auto/edit/**", "/auto/delete/**", "/categories/**").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.POST, "/auto/**").hasAuthority("ADMIN")
        
        // 2. USER + ADMIN: Possono NOLEGGIARE
        .requestMatchers("/rents/create", "/rents/quickrent/**").hasAnyAuthority("USER", "ADMIN")
        
        // 3. SOLO ADMIN: Vedere la lista noleggi, i dettagli e cancellarli
        .requestMatchers("/rents", "/rents/", "/rents/show/**").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.POST, "/rents/delete/**").hasAuthority("ADMIN")
        
        // 4. USER + ADMIN: Vedere il catalogo auto
        .requestMatchers("/auto", "/auto/**").hasAnyAuthority("USER", "ADMIN")

        // 5. TUTTI: Home page e risorse pubbliche
        .anyRequest().permitAll()
    )

        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/auto/", true)
            .permitAll()
        )

        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        );

        return http.build();

    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean 
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean 
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

}
