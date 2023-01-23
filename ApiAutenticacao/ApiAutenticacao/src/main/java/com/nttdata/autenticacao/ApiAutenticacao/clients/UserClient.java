package com.nttdata.autenticacao.ApiAutenticacao.clients;

import com.nttdata.autenticacao.ApiAutenticacao.domain.Login;
import com.nttdata.autenticacao.ApiAutenticacao.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignUsers", url = "http://localhost:9002/usuarios")
public interface UserClient {

    @PostMapping(value = "/login")
    User loginUser(@RequestBody Login loginDto);

}
