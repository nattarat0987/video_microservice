package com.youtubeclonebynattarat.nattaratprojects.Token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.youtubeclonebynattarat.nattaratprojects.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {
    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)  request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if(ObjectUtils.isEmpty(authorization)){
            chain.doFilter(request,response);
            return;
        }
        if(!authorization.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }
        String token = authorization.substring(7);
        DecodedJWT decodedJWT = tokenService.VerifierToken(token);
        if(decodedJWT == null){
            chain.doFilter(request,response);
            return;
        }
        String principal = decodedJWT.getClaim("principal").asString();
        String role =  decodedJWT.getClaim("role").asString();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principal,"(protected)",grantedAuthorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request,response);
    }
}
