package com.nttdata.usuarios.ApiUsuarios.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetuserDto {
    private String email;
    private String password;

}
