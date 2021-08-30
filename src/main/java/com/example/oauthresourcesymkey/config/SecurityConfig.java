package com.example.oauthresourcesymkey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .oauth2ResourceServer(configurer -> {
                   configurer.jwt(jwtConfigurer -> {
                       jwtConfigurer.decoder(jwtDecoder())
                               .jwtAuthenticationConverter(converter());
                   });
                });

        http
                .authorizeRequests()
                .anyRequest().hasAuthority("read");
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secretKeyString = "da98syaj09ahjd09i12i31eqnwjawndoijsa0ju10jejjif09ug98rheinf";
        SecretKey secretKey = new SecretKeySpec(
                                                secretKeyString.getBytes(),
                                                0,
                                                secretKeyString.getBytes().length,
                                                "AES");

        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter converter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return converter;
    }
}
