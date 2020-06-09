package fsd.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.model.Result;

public class ResponseUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> void setResponse(HttpServletResponse response, HttpStatus status, Result<T> result)
			throws IOException {
		// Access-Control-Allow-Origin
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		// "application/json;charset=UTF-8"
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		// set response code
		response.setStatus(status.value());
		// write result
		String jsonString = objectMapper.writeValueAsString(result);
		response.getWriter().write(jsonString);
	}
}