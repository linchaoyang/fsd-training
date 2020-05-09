package fsd.msservice.auth.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

    /** Private Security key */
    // TODO store this key in the config file
    private static final String base64Security = "emartSecretOfKey";

    /** Token issuser */
    // TODO store this value in the config file
    private static final String ISSUER = "fsd-emart";

    /** Token expire time: 7days */
    // TODO store this value in the config file
    private static final long expire = 7 * 24 * 3600 * 1000;

    /**
     * decode jwt.
     * 
     * @param jsonWebToken   token transfer from client
     * @param base64Security base64 security key
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Generate token.
     * 
     * @param user UserDetails
     * @return token
     */
    public static String createJWT(UserDetails user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // create siging Secret key
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // create JWT builder
        JwtBuilder builder = Jwts.builder()
                // set header "typ" as JWT
                .setHeaderParam("typ", "JWT")
                // add role
                .claim("role", user.getAuthorities())
                // add username
                .claim("username", user.getUsername())
                // set issuer
                .setIssuer(ISSUER)
                // set subject to who
                .setSubject(user.getUsername())
                // issue date
                .setIssuedAt(new Date())
                // sign
                .signWith(signatureAlgorithm, signingKey)
                // validate from
                .setNotBefore(new Date())
                // expire to
                .setExpiration(new Date(System.currentTimeMillis() + expire));

        // Create JWT
        return builder.compact();
    }
}