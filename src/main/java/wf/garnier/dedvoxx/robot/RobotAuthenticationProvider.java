package wf.garnier.dedvoxx.robot;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;


@AllArgsConstructor
public class RobotAuthenticationProvider implements AuthenticationProvider {

    private final List<String> passwords;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var auth = (RobotAuthentication) authentication;
        if (passwords.contains(auth.getCredentials())) {
            return RobotAuthentication.authenticated();
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RobotAuthentication.class.isAssignableFrom(authentication);
    }
}
