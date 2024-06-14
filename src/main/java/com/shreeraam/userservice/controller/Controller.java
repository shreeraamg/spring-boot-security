package com.shreeraam.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
public class Controller {

    @GetMapping
    public ResponseEntity<String> GET() {
        return new ResponseEntity<>("All Users", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> POST() {
        return new ResponseEntity<>("Only Admin", HttpStatus.OK);
    }

}
