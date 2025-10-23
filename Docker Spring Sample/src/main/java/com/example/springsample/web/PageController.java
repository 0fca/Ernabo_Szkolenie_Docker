package com.example.springsample.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() {
        String html = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"utf-8\"/>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>" +
                "<title>Spring Boot Docker Demo</title>" +
                "<link rel=\"stylesheet\" href=\"/assets/styles.css\"/>" +
                "</head>" +
                "<body>" +
                "<main class=\"page\">" +
                "<h1>Spring Boot In Docker</h1>" +
                "<p>This HTML is rendered by a Spring Boot REST endpoint inside a Debian-based container.</p>" +
                "<p class=\"hint\">Expose container port 80 to host port 6090 to visit this page.</p>" +
                "</main>" +
                "</body>" +
                "</html>";
        return ResponseEntity.ok(html);
    }

    @GetMapping(path = "/assets/styles.css", produces = "text/css")
    public ResponseEntity<String> styles() {
        String css = "*{box-sizing:border-box;}" +
                "body{margin:0;font-family:system-ui,-apple-system,\"Segoe UI\",sans-serif;background:linear-gradient(135deg,#2a5298,#1e3c72);color:#ffffff;min-height:100vh;display:flex;align-items:center;justify-content:center;}" +
                ".page{text-align:center;background:rgba(0,0,0,0.35);padding:3rem;border-radius:1rem;max-width:32rem;box-shadow:0 1.5rem 3rem rgba(0,0,0,0.35);}" +
                "h1{margin-bottom:1rem;font-size:2.5rem;}" +
                "p{margin:0.75rem 0;font-size:1.1rem;}" +
                "p.hint{font-size:0.95rem;opacity:0.85;}";
        return ResponseEntity.ok(css);
    }
}
