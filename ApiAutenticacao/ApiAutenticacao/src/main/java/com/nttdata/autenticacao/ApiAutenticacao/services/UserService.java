package com.nttdata.autenticacao.ApiAutenticacao.services;

import com.nttdata.autenticacao.ApiAutenticacao.clients.UserClient;
import com.nttdata.autenticacao.ApiAutenticacao.domain.Login;
import com.nttdata.autenticacao.ApiAutenticacao.domain.User;
import com.nttdata.autenticacao.ApiAutenticacao.services.dto.TokenDto;
import com.nttdata.autenticacao.ApiAutenticacao.services.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    JwtToken jwtToken;

    @Autowired
    UserClient userClient;

    public TokenDto loginUser(Login loginDto) {
       User user = userClient.loginUser(loginDto);
       if (user == null) {
           throw new RuntimeException("Usuário inválido");
       }
       String token = jwtToken.generateToken(user);

       return TokenDto.builder().type("Bearer").token(token).build();
    }

    public boolean validateToken(String token) {
      return jwtToken.tokenValid(token);
    }

    public String getTypeUser(String token) {
        return jwtToken.typeUser(token);
    }
}
