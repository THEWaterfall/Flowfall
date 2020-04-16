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

    /**
     * Checks if the authenticated user has the specified permission to operate the specified entity
     * using the id of the board. <br/>
     * <br/>
     * For instance, if a user has the B.ONWER role on the specific board then that means that the user has
     * the permissions to CREATE, READ, UPDATE, DELETE such entities as BOARD, COLUMN, ROW, MESSAGE <b>that are
     * related to the specific board</b>.
     *
     * @param entity Entity - the entity that is checked if a user has a permission to operate it
     * @param permission Permission - the permission that is checked if user has got such
     * @param boardId Long - the id of the board the is used to check if a user has a permission to operate
     *               the related to the board entities
     * @return boolean - true if a user is allowed to operate an entity, otherwise false
     */
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
