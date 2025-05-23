package wf.garnier.dedvoxx;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class RobotAuthentication implements Authentication {

    private boolean authenticated;
    private Object password;
    private Collection<SimpleGrantedAuthority> authorities;

    private RobotAuthentication(Collection<SimpleGrantedAuthority> authorities, Object password) {
        this.authorities = authorities;
        this.authenticated = !authorities.isEmpty();
        this.password = password;

    }

    public static RobotAuthentication token(String password) {
        return new RobotAuthentication(Collections.EMPTY_LIST, password);
    }

    public static RobotAuthentication authenticated() {
        return new RobotAuthentication(Collections.singleton(new SimpleGrantedAuthority("ROLE-robot")), null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "Robot";
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new RuntimeException("Please don't");
    }

    @Override
    public String getName() {
        return "Robot";
    }
}
