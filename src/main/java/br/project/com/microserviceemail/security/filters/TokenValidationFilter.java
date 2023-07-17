package br.project.com.microserviceemail.security.filters;

import br.project.com.microserviceemail.utils.BusinessException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class TokenValidationFilter extends OncePerRequestFilter {
    private static final String HEADER_ATTRIBUTE = "Authorization";
    private static final String ATTRIBUTE_PREFIX = "Bearer ";
    public static final String TOKEN_PASSWORD = "463408a1-54c9-4307-bb1c-6cced559f5a7";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String attribute = request.getHeader(HEADER_ATTRIBUTE);
            validateHeaders(attribute);

            String token = attribute.replace(ATTRIBUTE_PREFIX, "");
            validateToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (BusinessException err) {
            writerResponseError(response, err);
            return;
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void validateHeaders(String attribute) throws BusinessException {
        if (attribute == null || !attribute.startsWith(ATTRIBUTE_PREFIX)) {
            throw new BusinessException("token not found");
        }
    }

    private DecodedJWT validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(TOKEN_PASSWORD))
                    .build()
                    .verify(token);
        } catch (Exception ex) {
            throw new BusinessException("invalid token");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String user = decodedJWT.getSubject();

        verifyUser(user);

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

    private String verifyUser(String user) {
        if (user == null) {
            throw new BusinessException("invalid token");
        }

        return user;
    }

    private void writerResponseError(HttpServletResponse response, BusinessException err) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(err.getMessage());
        response.getWriter().flush();
    }
}