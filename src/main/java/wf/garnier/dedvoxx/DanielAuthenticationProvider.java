package wf.garnier.dedvoxx;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DanielAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var auth = (UsernamePasswordAuthenticationToken) authentication;
        if (auth.getName().equals("daniel")) {
            return new UsernamePasswordAuthenticationToken(
                    "daniel",
                    null,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_admin")));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
