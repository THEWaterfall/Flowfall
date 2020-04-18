package waterfall.flowfall.repository;

import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.enums.RoleType;

@Repository
public interface UserRoleRepository {
    void addRole(long entityId, long userId, long roleId);
    void addRole(long entityId, long userId, RoleType roleType);

    void deleteRole(long entityId, long userId, long roleId);
    void deleteRole(long entityId, long userId);
    void deleteRole(Long entityId);
}
