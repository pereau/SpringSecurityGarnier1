package wf.garnier.dedvoxx;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.time.Instant.now;

@AllArgsConstructor
public class RateLimitOpenIdProvider implements AuthenticationProvider {

    private final Map<String, Instant> cache = new HashMap<>();
    //Pattern Composition : au lieu d'étendre OidcAuthorizationCodeAuthenticationProvider et d'implémenter ses méthodes
    // on fait de la composition : on a une classe parente à laquelle on délègue la plupart de la logique
    private final OidcAuthorizationCodeAuthenticationProvider delegate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var parentAuthentication = ((OAuth2LoginAuthenticationToken) delegate.authenticate(authentication));
        var user = (OidcUser) parentAuthentication.getPrincipal();
        var email = user.getEmail();

        if (renewCache(email)) {
            return parentAuthentication;
        } else {
            throw new BadCredentialsException("Not so fast ");
        }
    }

    //valable pour l'exemple mais il faudrait en fait utiliser un vrai cache
    private boolean renewCache(String email) {
        var instant = cache.get(email);
        if (instant == null || instant.plusSeconds(60).isBefore(now())) {
            cache.put(email, now());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //On se contente de faire ce que fait le parent (parent par composition)
        return delegate.supports(authentication);
    }
}
