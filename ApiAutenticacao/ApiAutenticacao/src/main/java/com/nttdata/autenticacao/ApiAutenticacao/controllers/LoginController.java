package com.nttdata.autenticacao.ApiAutenticacao.controllers;

import com.nttdata.autenticacao.ApiAutenticacao.domain.Login;
import com.nttdata.autenticacao.ApiAutenticacao.services.UserService;
import com.nttdata.autenticacao.ApiAutenticacao.services.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping(value = "login")
    public ResponseEntity<TokenDto> login(@RequestBody Login loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginDto));
    }
}
