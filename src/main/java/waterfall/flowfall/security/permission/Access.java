package waterfall.flowfall.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.enums.Entity;
import waterfall.flowfall.model.enums.Permission;
import waterfall.flowfall.model.enums.UserGlobalRole;
import waterfall.flowfall.model.User;
import waterfall.flowfall.repository.RolePermissionRepository;
import waterfall.flowfall.util.SecurityContextUtils;

@Component
public class Access {

    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    public Access(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public boolean require(Entity entity, Permission permission, Long boardId) {
        if (isAdmin()) {
            return true;
        }

        User user = SecurityContextUtils.getAuthenticatedUser();

        return rolePermissionRepository.hasAccess(user.getId(), boardId, entity, permission);
    }

    public boolean isAdmin() {
        User user = SecurityContextUtils.getAuthenticatedUser();
        return user.getGlobalRoles().stream()
                .anyMatch(globalRole -> globalRole.getName().equals(UserGlobalRole.ADMIN.getLiteral()));
    }

}
