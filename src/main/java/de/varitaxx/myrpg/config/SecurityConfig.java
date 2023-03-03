package de.varitaxx.myrpg.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //Diese Ordner sollen von der Security nicht geschützt werden
        return web -> web.ignoring().requestMatchers("/css/**","/img/**","js/**", "/webjars/**","/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // Request -> Server -> SecurityChain -> Controller -> Response
        http
                .csrf().disable()
                //.httpBasic()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login/error")
                .defaultSuccessUrl("/chars") // Weiterleitung nach dem Login
                .and()
                .authorizeRequests()
                .requestMatchers("/", "/login/**", "/register/**","/activate/**","/forgot/**").permitAll() // Frei zugänglich
              //  .antMatchers("/admin/**").hasRole("ADMIN") // Freigabe nur mit einer bestimmten Role
                .anyRequest().authenticated() // Alle anderen erfordern Anmeldung
                .and()
                .logout().logoutUrl("/logout")
                .invalidateHttpSession(true); // Session wird ungültig
        //.deleteCookies("JSESSIONID"); // Session-Cookie wird im Browser gelöscht

        http.headers().frameOptions().sameOrigin();// Freigabe der Frames für H2-Datenbank

        return http.build();

    }
}
