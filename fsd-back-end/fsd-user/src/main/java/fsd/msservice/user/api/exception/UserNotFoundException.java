package fsd.msservice.user.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String username) {
        this("Could not find user with username '%s'", username);
    }

    public UserNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }

}