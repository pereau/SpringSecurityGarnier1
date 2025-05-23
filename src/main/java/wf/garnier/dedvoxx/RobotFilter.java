package wf.garnier.dedvoxx;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public RobotFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!Collections.list(request.getHeaderNames()).contains("x-robot-password")) {
            filterChain.doFilter(request, response);
            return;
        }
        String password = request.getHeader("x-robot-password");
        var token = RobotAuthentication.token(password);
        var authentication = authenticationManager.authenticate(token)  ;
        if (authentication != null) {

        }
        if(password.equals("beep-boop")) {

            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println("You are not Robot");
            return;
        }

        System.out.println("Hello Robot");
        filterChain.doFilter(request, response);

    }
}
