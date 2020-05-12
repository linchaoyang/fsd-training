package fsd.msservice.transaction.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CarouselNotFoundException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public CarouselNotFoundException(String productId) {
        this("Could not find carousel with product id '%s'", productId);
    }

    public CarouselNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }

}