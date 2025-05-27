package wf.garnier.dedvoxx;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.ObjectPostProcessor;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;

public class RateLimitPostProcessor implements ObjectPostProcessor<AuthenticationProvider> {

    @Override
    public <O extends AuthenticationProvider> O postProcess(O object) {

        if (// Si le post Process est appliqué sur un OidcAuthorizationCodeAuthenticationProvider
                OidcAuthorizationCodeAuthenticationProvider.class.isAssignableFrom(object.getClass())) {
            //créer et renvoie un RateLimiteOpenIdProviderqui contient tout ce qui a déjà été configuré par Spring Open Id
            return (O) new RateLimitOpenIdProvider(
                    ((OidcAuthorizationCodeAuthenticationProvider) object)
            );
        } else {
            //Sinon on renvoie l'objet sans le modifier
            return object;
        }
    }
}
