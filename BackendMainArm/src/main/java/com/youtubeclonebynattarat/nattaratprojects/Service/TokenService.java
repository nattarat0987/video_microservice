package com.youtubeclonebynattarat.nattaratprojects.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.youtubeclonebynattarat.nattaratprojects.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    @Value(value = "${app.token.SECRET}")
    private String SECRET;
    @Value(value = "${app.token.ISSUER}")
    private String ISSUER;

    public Algorithm algorithm(){
        return  Algorithm.HMAC512(SECRET);
    }
    public String CreateToken(User user){
        log.info("-----------CreateToken = "+ user.getId()+"---------------");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,60);
        Date ExpiresToken = calendar.getTime();
        String token = JWT.create()
                .withClaim("principal",user.getId())
                .withClaim("role","User")
                .withExpiresAt(ExpiresToken)
                .withIssuer(ISSUER)
                .sign(algorithm());
        return token;
    }

    public DecodedJWT VerifierToken(String token){
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    // specify an specific claim validations
                    .withIssuer(ISSUER)
                    // reusable verifier instance
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            return null;
        }
    }
}
