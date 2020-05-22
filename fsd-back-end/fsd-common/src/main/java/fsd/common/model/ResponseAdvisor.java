package fsd.common.model;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import fsd.common.expection.ProductNotFoundException;

@ControllerAdvice
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body instanceof Resource) {
			return body;
		}
		if (body instanceof Result) {
			return body;
		}
		return new Result.Builder<>().data(body).build();
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleException(ProductNotFoundException e) {
		// log exception
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");
	}

}
