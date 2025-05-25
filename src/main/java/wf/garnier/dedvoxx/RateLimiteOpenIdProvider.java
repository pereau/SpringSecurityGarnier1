package wf.garnier.dedvoxx;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;

@AllArgsConstructor
public class RateLimiteOpenIdProvider implements AuthenticationProvider {

    //Pattern Composition : au lieu d'étendre OidcAuthorizationCodeAuthenticationProvider et d'implémenter ses méthodes
    // on fait de la composition : on a une classe parente à laquelle on délègue la plupart de la logique
    private final OidcAuthorizationCodeAuthenticationProvider delegate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //On se contente de faire ce que fait le parent (parent par composition)
        var parentAuthentication = delegate.authenticate(authentication);
        //my behavior
        return parentAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //On se contente de faire ce que fait le parent (parent par composition)
        return delegate.supports(authentication);
    }
}
