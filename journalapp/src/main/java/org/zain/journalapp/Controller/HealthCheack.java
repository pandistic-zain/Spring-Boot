package org.zain.journalapp.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HealthCheack {
    
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }
    
}
