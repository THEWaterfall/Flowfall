package waterfall.flowfall.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.User;
import waterfall.flowfall.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class SecurityContextUtils {

    private static UserService userService;

    @Autowired
    private UserService autowiredUserService;

    @PostConstruct
    private void init() {
        userService = this.autowiredUserService;
    }

    public static User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName()).get();
    }
}
