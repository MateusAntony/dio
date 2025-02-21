package com.dio.config.jwt;


/**
 * Classe modelo para representar a requisição de autenticação JWT.
 * Esta classe armazena o nome de usuário e a senha fornecidos pelo cliente.
 */

public class JwtRequest {
    private String username;
    private String senha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}