package wf.garnier.dedvoxx;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String publicPage() {
        return "Hello world !" ;
    }

    @GetMapping("/private")
    public String privatePage(Authentication authentication)
    {
        var auth2 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("injected " + authentication);
        System.out.println("auth " + auth2);
        System.out.println(authentication.equals(auth2));
        new Thread(() -> {
            System.out.println("in thread " +
                    SecurityContextHolder.getContext().getAuthentication());
        }).start();
        return "Welcome to le Carré VIP " + authentication;
    }
}
