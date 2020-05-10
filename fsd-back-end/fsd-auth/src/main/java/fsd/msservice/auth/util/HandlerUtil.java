package fsd.msservice.auth.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import fsd.model.ResponseResult;

public class HandlerUtil {

    @Autowired
    private static ObjectMapper objectMapper;

    public static void setResponse(HttpServletResponse response, HttpStatus status, ResponseResult result)
            throws IOException {
        // Access-Control-Allow-Origin
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        // "application/json;charset=UTF-8"
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // set response code
        response.setStatus(status.value());
        // write result
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}