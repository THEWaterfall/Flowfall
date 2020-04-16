package waterfall.flowfall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.enums.RoleType;
import waterfall.flowfall.repository.UserRoleRepository;
import waterfall.flowfall.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void addRole(long entityId, long userId, long roleId) {
        userRoleRepository.addRole(entityId, userId, roleId);
    }

    @Override
    public void addRole(long entityId, long userId, RoleType roleType) {
        userRoleRepository.addRole(entityId, userId, roleType);
    }

    @Override
    public void deleteRole(long entityId, long userId, long roleId) {
        userRoleRepository.deleteRole(entityId, userId, roleId);
    }

    @Override
    public void deleteRole(long entityId, long userId) {
        userRoleRepository.deleteRole(entityId, userId);
    }

    @Override
    public void deleteRole(long entityId) {
        userRoleRepository.deleteRole(entityId);
    }
}
