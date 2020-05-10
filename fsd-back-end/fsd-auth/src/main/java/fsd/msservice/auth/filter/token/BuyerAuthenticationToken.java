package fsd.msservice.auth.filter.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * UsernamePasswordAuthenticationToken for Buyer. <br>
 * AuthenticationManager will access Provider#supports() method to validate
 * whether AuthenticationToken is to be used
 * 
 * @author LinChaoYang Created on 2020/05/09 23:58
 */
public class BuyerAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -27077957540015380L;

    public BuyerAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}