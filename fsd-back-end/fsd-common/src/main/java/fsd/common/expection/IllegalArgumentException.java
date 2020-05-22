package fsd.common.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalArgumentException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public IllegalArgumentException(String name) {
		this("Illegal argument: '%s'", name);
	}

	public IllegalArgumentException(String message, Object... args) {
		super(String.format(message, args));
	}

}