package wf.garnier.dedvoxx;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import wf.garnier.dedvoxx.robot.RobotConfigurer;

import static java.util.Collections.EMPTY_LIST;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEventPublisher publisher
    ) throws Exception {
//        http.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationEventPublisher(publisher);
        DefaultSecurityFilterChain securityFilterChain =
                http
                .authenticationProvider(new DanielAuthenticationProvider())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/private").authenticated();
                    auth.anyRequest().permitAll();
                })
//                .csrf(withDefaults())
                .formLogin(withDefaults())
//                .formLogin(configurer-> {
//                    configurer
//                            .loginPage("foo/bar")
//                            .usernameParameter("utilisateur")
//                            .passwordParameter("mot-de-passe");
//                })
                .httpBasic(withDefaults())
                .oauth2Login(withDefaults())
                .with(new RobotConfigurer()
                        .password("beep-boop")
                        .password("boop-boop"),
                        withDefaults()
                )
//                              .headerName("x-robot-password")
                .build();
        return securityFilterChain;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new User("user", "{noop}password", EMPTY_LIST)
        );
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> onSuccess() {
        return (event -> {
            String authClassName = event.getAuthentication().getClass().getName();
            String userName = event.getAuthentication().getName();
            System.out.println("Login Successful "
                    + authClassName
                    +" - "
                    + userName);
        });
    }

}
