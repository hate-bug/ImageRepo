package com.shopify.imagerepo.Security;

import com.shopify.imagerepo.Model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTProvider {

    //Generate a JWT token whenever we have a valid username and password
    public String generateJwtToken (Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date current = new Date(System.currentTimeMillis());
        Date expiryDate = new Date (current.getTime()+ SecurityURLs.EXPIRATION_TIME);

        Map<String,Object> map = new HashMap<>();
        map.put("id", Long.toString(user.getId()));
        map.put("username", user.getUsername());

        String userId = Long.toString(user.getId());
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(map)
                .setIssuedAt(current)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityURLs.SECRET)
                .compact();
    }

    //validate token
    public boolean validateJwt (String token) {
        try {
            Jwts.parser().setSigningKey(SecurityURLs.SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) { // invalid Json signature

        } catch (MalformedJwtException e) { // invalid Json token

        } catch (ExpiredJwtException e) { //expired token

        } catch (UnsupportedJwtException e) {// unsupported Json token

        } catch (IllegalArgumentException e) { // jwt claim string is empty

        }
        return false;
    }

    //get User from token
    public Long getUserIdfromJwt (String token) {
        Claims claim = Jwts.parser().setSigningKey(SecurityURLs.SECRET).parseClaimsJws(token).getBody();
        Long id = Long.parseLong((String) claim.get("id"));
        return id;
    }
}
