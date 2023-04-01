package com.youtubeclonebynattarat.nattaratprojects.Configurations;

import com.youtubeclonebynattarat.nattaratprojects.Service.TokenService;
import com.youtubeclonebynattarat.nattaratprojects.Token.TokenFilter;
import com.youtubeclonebynattarat.nattaratprojects.Token.TokenFilterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebsecurityConfiguration {
    private final TokenService tokenService;

    private String [] PUBLIC= new String[]{
            "/api/user/login",
            "/api/user/register",
            "/socket/**",
            "/api/chat/message",
            "/api/user/activate/**",
            "/api/user/resend-activate-email"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.cors(config -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowCredentials(true);
                    cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
                    cors.addAllowedHeader("*");
                    cors.addAllowedMethod("GET");
                    cors.addAllowedMethod("POST");
                    cors.addAllowedMethod("PUT");
                    cors.addAllowedMethod("DELETE");
                    cors.addAllowedMethod("OPTIONS");
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", cors);
                    config.configurationSource(source);
                }).csrf().disable()
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry
                        -> authorizationManagerRequestMatcherRegistry.requestMatchers(PUBLIC).anonymous().anyRequest().authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer
                        -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .apply(tokenFilterConfig())
               .and().build();
    }
    @Bean
    public TokenFilterConfig tokenFilterConfig(){
        return new TokenFilterConfig(tokenFilter());
    }
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter(tokenService);
    }


}
