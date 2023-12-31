package com.funcoding.shoptemplate.controller;

import com.funcoding.shoptemplate.entity.JwtRequest;
import com.funcoding.shoptemplate.entity.JwtResponse;
import com.funcoding.shoptemplate.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        JwtResponse jwtResponse =  jwtService.createJwtToken(jwtRequest);
        return jwtResponse;
    }
}
