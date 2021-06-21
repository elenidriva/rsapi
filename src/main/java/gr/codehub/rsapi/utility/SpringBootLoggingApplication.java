package gr.codehub.rsapi.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootLoggingApplication {
    Logger log = LoggerFactory.getLogger(SpringBootApplication.class);

    @RequestMapping("/test/{name}")
    public String greeting(@PathVariable String name) {
        log.debug("Request{}", name);
        String response = "Hi" + name + "Welcome to java Techie";
        log.debug("Response{}", response);
        return response;

    }
}


