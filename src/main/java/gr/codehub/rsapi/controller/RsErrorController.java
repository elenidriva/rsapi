package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RsErrorController implements ErrorController {
    @RequestMapping("error")
    @ResponseBody
    public ErrorDetails handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String[] s;
        if (exception != null) {
            s = exception.getMessage().split(":", 2);
        } else {
            return new ErrorDetails(500, "Something went wrong.");
        }
        if (exception instanceof BusinessException)
            return new ErrorDetails(404, exception.getMessage());
        return new ErrorDetails(statusCode, exception == null ? "N/A" : s[1]);
    }

    @Override
    public String getErrorPath() {
        return null;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorDetails {
        private Integer errorCode;
        private String errorMessage;
    }
}