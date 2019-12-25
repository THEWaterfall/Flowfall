package waterfall.flowfall.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.Board;

import java.util.List;

@Component
public class BoardPermissions {

    public boolean isOwner(Board targetObject) {
        boolean hasPermission = false;
        if(targetObject != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            hasPermission = authentication.getPrincipal().equals(targetObject.getUser().getEmail());
        }

        return hasPermission;
    }

    public boolean isOwner(List<Board> targetObjects) {
        boolean hasPermission = false;
        if(targetObjects != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            for(Board board: targetObjects) {
                hasPermission = authentication.getPrincipal().equals(board.getUser().getEmail());
            }
        }

        return hasPermission;
    }
}
