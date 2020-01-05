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

    public boolean hasRights(T targetObject, Long userId) {
        return isAdmin() || isOwner(targetObject) || isCollaborator(targetObject);
    }

    public boolean hasRights(List<T> targetObjects) {
        // TODO: Do not return true if list is empty.
        // TODO: Even if empty, it should be checked if the user has rights to the objects
        if(targetObjects.isEmpty()) {
            return true;
        }

        return isAdmin() || isOwner(targetObjects) || isCollaborator(targetObjects);
    }

    public abstract boolean isOwner(Long targetId);
    public abstract boolean isOwner(T targetObject);
    public abstract boolean isOwner(List<T> targetObjects);
    public abstract boolean isCollaborator(Long targetId);
    public abstract boolean isCollaborator(T targetObject);
    public abstract boolean isCollaborator(List<T> targetObjects);
}
