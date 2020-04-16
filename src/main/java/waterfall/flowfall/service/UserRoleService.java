package waterfall.flowfall.service;

import waterfall.flowfall.model.enums.RoleType;

public interface UserRoleService {
    void addRole(long entityId, long userId, long roleId);
    void addRole(long entityId, long userId, RoleType roleType);
    void deleteRole(long entityId, long userId, long roleId);
    void deleteRole(long entityId, long userId);
    void deleteRole(long entityId);
}
