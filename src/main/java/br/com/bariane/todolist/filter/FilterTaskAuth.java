package br.com.bariane.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.bariane.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/task/")) {

            // Pegar a Autenticacao
            var authorization = request.getHeader("Authorization");

            // Remover o trecho Basic do decode
            var authEnconde = authorization.substring("Basic".length()).trim();

            // Decodificar o array
            byte[] authDecode = Base64.getDecoder().decode(authEnconde);

            // Converter em String
            var authString = new String(authDecode);
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String pass = credentials[1];

            // Validar Usuario
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuario nao pode seguir com os trambique!");
            } else {
                var passVerify = BCrypt.verifyer().verify(pass.toCharArray(), user.getPass());
                if (passVerify.verified) {

                    request.setAttribute("idUser", user.getId());

                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Usuario nao pode seguir com os trambique!");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
