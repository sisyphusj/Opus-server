package com.opus.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

@Slf4j
@RestController
public class ErrorController {

    @GetMapping("/error")
    public void error(HttpServletRequest request) throws AuthenticationException {

        String message = (String) request.getAttribute("message");
        String exception = (String) request.getAttribute("exception");

        log.info("error message = {}", message);

        if("AuthenticationException".equals(exception))
            throw new AuthenticationException(message);
    }

}
