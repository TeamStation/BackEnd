package com.teamstation.teamstation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webclient")
public class WebserverController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/{param}")
    public String testWebClient(
            @PathVariable String param,
            @RequestHeader HttpHeaders headers,
            @CookieValue(name = "httpclient-type", required=false, defaultValue="undefined") String httpClientType) {

        log.info(">>>> Cookie 'httpclient-type={}'", httpClientType);

        headers.forEach((key, value) -> {
            log.info(String.format(">>>>> Header '%s' => %s", key, value));
        });

        log.info("### Received: /webclient/" + param);

        String msg = param + " => Working successfully !!!";
        log.info("### Sent: " + msg);
        return msg;
    }
}
