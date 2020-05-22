package fsd.common.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String name) {
		this("Could not find product with name '%s'", name);
	}

	public ProductNotFoundException(String message, Object... args) {
		super(String.format(message, args));
	}

}