package gr.codehub.rsapi.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
public class RsErrorController implements ErrorController {

    @RequestMapping("error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        return "Error code= " + statusCode + " Message= " + ((exception == null) ? "N/A" : exception.getMessage());


    }

    @Override
    public String getErrorPath() {
        return null;
    }
}

