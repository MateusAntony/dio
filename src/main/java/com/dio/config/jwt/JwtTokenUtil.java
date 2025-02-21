package com.dio.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // Validade do token JWT (5 horas)
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // Gere uma chave segura para HS512 (garante pelo menos 512 bits)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Obtém o nome de usuário a partir do token JWT.
     *
     * @param token Token JWT
     * @return Nome de usuário contido no token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Obtém a data de expiração do token JWT.
     *
     * @param token Token JWT
     * @return Data de expiração do token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Obtém um dado específico do token JWT usando um resolver de claims.
     *
     * @param token Token JWT
     * @param claimsResolver Função que extrai o dado desejado dos claims
     * @return Valor extraído do token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtém todas as informações contidas no token JWT.
     *
     * @param token Token JWT
     * @return Objeto Claims contendo os dados do token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica se o token está expirado.
     *
     * @param token Token JWT
     * @return true se o token estiver expirado, false caso contrário
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Gera um novo token JWT para um usuário.
     *
     * @param userDetails Detalhes do usuário autenticado
     * @return Token JWT gerado
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Constrói o token JWT com os dados necessários.
     *
     * @param claims Informações adicionais a serem inseridas no token
     * @param subject Identificação do usuário (geralmente o nome de usuário)
     * @return Token JWT gerado
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * Valida o token JWT verificando se pertence ao usuário e se não está expirado.
     *
     * @param token Token JWT
     * @param userDetails Detalhes do usuário autenticado
     * @return true se o token for válido, false caso contrário
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
