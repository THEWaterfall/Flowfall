package waterfall.flowfall.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.User;
import waterfall.flowfall.service.UserService;

@Component
public class UserPermissions {

    private UserService userService;

    @Autowired
    public UserPermissions(UserService userService) {
        this.userService = userService;
    }

    public boolean isOwner(Long targetId) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName()).get();

        return user.getId().equals(targetId);
    }
}
