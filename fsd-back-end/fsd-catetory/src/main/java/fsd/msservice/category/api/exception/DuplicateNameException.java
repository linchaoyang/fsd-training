package fsd.msservice.category.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class DuplicateNameException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateNameException(String name) {
		this("Name '%s' already exist.", name);
	}

	public DuplicateNameException(String message, Object... args) {
		super(String.format(message, args));
	}

}