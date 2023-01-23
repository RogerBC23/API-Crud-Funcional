package com.nttdata.usuarios.ApiUsuarios.services;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;
import com.nttdata.usuarios.ApiUsuarios.models.UserModel;
import com.nttdata.usuarios.ApiUsuarios.repositories.UserRepository;
import com.nttdata.usuarios.ApiUsuarios.repositories.feing.AuthFeing;
import com.nttdata.usuarios.ApiUsuarios.services.dtos.GetuserDto;
import com.nttdata.usuarios.ApiUsuarios.services.dtos.UserDto;
import com.nttdata.usuarios.ApiUsuarios.services.execptionhandler.UserNotFoundException;
import com.nttdata.usuarios.ApiUsuarios.services.validation.CpfValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Autowired
    AuthFeing authFeing;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserModel save(UserModel user) {
        var encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public List<UserModel> fidAll() {
        return userRepository.findAll();
    }

    public Boolean exitsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public boolean exitsBycpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }
    public boolean cpfValidator(String cpf) {
        boolean cpfIsValid = CpfValidate.isValidCpf(cpf);
        return cpfIsValid;
    }

    public String formatCpf(String cpf) {
        if (cpf.length() == 14) {
            Formatter formatrer = new CPFFormatter();
            String unformatedValue = formatrer.unformat(cpf);
            return unformatedValue;
        }else {
            return  cpf;
        }
    }

    public UserModel fingById(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public void delete(UserModel userModel) {
        fingById(userModel.getId());
        userRepository.delete(userModel);
    }

    public UserDto loginUser(GetuserDto loginDto) {
        UserModel user = userRepository.findByEmail(loginDto.getEmail());

        if (user == null) {
            return null;
        }

        boolean isValidPassword = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if (!isValidPassword) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setTypeUser(user.getTypeUser());
        userDto.setName(user.getName());
        userDto.setCpf(user.getCpf());

        return userDto;
    }

    public boolean validateToken(String token) {
        return authFeing.validateToken(token);
    }

    public String getTypeUser(String token) {
        return authFeing.getTypeUser(token);
    }
}
