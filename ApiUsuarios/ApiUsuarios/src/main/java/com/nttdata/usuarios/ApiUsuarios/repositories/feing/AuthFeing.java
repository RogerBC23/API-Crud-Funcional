package com.nttdata.usuarios.ApiUsuarios.repositories.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "authFeing", url ="http://localhost:9001" )
public interface AuthFeing {

    @GetMapping(value = "/auth/validatetoken")
    boolean validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION)String token);

    @GetMapping(value = "/auth/typeuser")
    String getTypeUser(@RequestHeader(HttpHeaders.AUTHORIZATION)String token);
}
