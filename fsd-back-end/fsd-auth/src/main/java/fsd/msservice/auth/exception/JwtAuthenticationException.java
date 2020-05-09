package fsd.msservice.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    /**
     * 
     */
    private static final long serialVersionUID = 381802987164606243L;

    /** message code */
    private String code = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}