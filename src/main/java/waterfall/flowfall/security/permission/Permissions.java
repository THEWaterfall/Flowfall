package waterfall.flowfall.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

// TODO: refactor permissions
@Component
public abstract class Permissions<T> {

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public boolean hasRights(T targetObject) {
        return isAdmin() || isOwner(targetObject) || isCollaborator(targetObject);
    }

    public boolean hasRights(List<T> targetObjects) {
        return isAdmin() || isOwner(targetObjects);
    }

    public abstract boolean isOwner(Long targetId);
    public abstract boolean isOwner(T targetObject);
    public abstract boolean isOwner(List<T> targetObjects);
    public abstract boolean isCollaborator(Long targetId);
    public abstract boolean isCollaborator(T targetObject);
}
