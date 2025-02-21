package com.dio;

import com.dio.auth.entity.Role;
import com.dio.auth.entity.User;
import com.dio.auth.service.RoleService;
import com.dio.auth.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Initializer {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void criaUsuariosEPermissoes() {
        Role roleAdmin = new Role();

        roleAdmin.setName("USER");

        roleService.save(roleAdmin);

        User user = new User();
        user.setAtivo(true);
        user.setEmail("teste@teste.com");
        user.setNome("Kaique Arantes");
        user.setSenha(new BCryptPasswordEncoder().encode("123456"));
        user.setUsername("karantes");
        user.setRoles(Arrays.asList(roleAdmin));

        userService.save(user);

    }
}