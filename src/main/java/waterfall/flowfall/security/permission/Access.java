package waterfall.flowfall.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.enums.EntityType;
import waterfall.flowfall.model.enums.PermissionType;
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

    /**
     * Checks if the authenticated user has the specified permission to operate the specified entity
     * using the id of the board. <br/>
     * <br/>
     * For instance, if a user has the B.OWNER role on the specific board then that means that the user has
     * the permissions to CREATE, READ, UPDATE, DELETE such entities as BOARD, COLUMN, ROW, MESSAGE <b>that are
     * related to the specific board</b>.
     *
     * @param entityType Entity - the entity that is checked if a user has a permission to operate it
     * @param permissionType Permission - the permission that is checked if user has got such
     * @param entityId Long - the id of the entity the is used to check if a user has a permission to operate
     *               the related to the entity entities
     * @return boolean - true if a user is allowed to operate an entity, otherwise false
     */
    public boolean require(EntityType entityType, PermissionType permissionType, Long entityId) {
        if (isAdmin()) {
            return true;
        }

        User user = SecurityContextUtils.getAuthenticatedUser();

        return rolePermissionRepository.hasAccess(user.getId(), entityId, entityType, permissionType);
    }

    public boolean isAdmin() {
        User user = SecurityContextUtils.getAuthenticatedUser();
        return user.getGlobalRoles().stream()
                .anyMatch(globalRole -> globalRole.getName().equals(UserGlobalRole.ADMIN));
    }

}
