package com.dio.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
/**
 * Classe responsável por tratar erros de autenticação no Spring Security.
 * Quando um usuário não autenticado tenta acessar um recurso protegido,
 * esta classe intercepta a requisição e retorna um erro 401 (Unauthorized).
 */


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8651187579837792070L;


    /**
     * Método chamado automaticamente quando um usuário não autenticado tenta acessar uma API protegida.
     *
     * @param request       A requisição HTTP recebida.
     * @param response      A resposta HTTP que será enviada.
     * @param authException Exceção gerada pela tentativa de autenticação falha.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}