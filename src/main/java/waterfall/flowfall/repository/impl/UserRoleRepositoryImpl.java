package waterfall.flowfall.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import waterfall.flowfall.model.Role;
import waterfall.flowfall.model.enums.RoleType;
import waterfall.flowfall.repository.RoleRepository;
import waterfall.flowfall.repository.UserRoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@Transactional
public class UserRoleRepositoryImpl implements UserRoleRepository {
    private EntityManager entityManager;
    private RoleRepository roleRepository;

    @Autowired
    public UserRoleRepositoryImpl(EntityManager entityManager, RoleRepository roleRepository) {
        this.entityManager = entityManager;
        this.roleRepository = roleRepository;
    }

    public void addRole(long entityId, long userId, long roleId) {
        Query query = entityManager.createNativeQuery(
                "INSERT INTO user_role(entity_id, user_id, role_id)" +
                        "VALUES(:entityId, :userId, :roleId)");
        query.setParameter("entityId", entityId);
        query.setParameter("userId", userId);
        query.setParameter("roleId", roleId);

        query.executeUpdate();
    }

    public void addRole(long entityId, long userId, RoleType roleType) {
        Role role = roleRepository.findByName(roleType.getLiteral())
                .orElseThrow(() -> new IllegalArgumentException(roleType.getLiteral() + " not found"));

        Query query = entityManager.createNativeQuery(
                "INSERT INTO user_role(entity_id, user_id, role_id)" +
                        "VALUES(:entityId, :userId, :roleId)");
        query.setParameter("entityId", entityId);
        query.setParameter("userId", userId);
        query.setParameter("roleId", role.getId());

        query.executeUpdate();
    }

    public void deleteRole(long entityId, long userId, long roleId)  {
        Query query = entityManager.createNativeQuery(
                "DELETE FROM user_role " +
                        "WHERE entity_id = :entityId " +
                        "AND user_id = :userId " +
                        "AND role_id = :roleId");
        query.setParameter("entityId", entityId);
        query.setParameter("userId", userId);
        query.setParameter("roleId", roleId);

        query.executeUpdate();
    }

    public void deleteRole(long entityId, long userId)  {
        Query query = entityManager.createNativeQuery(
                "DELETE FROM user_role " +
                        "WHERE entity_id = :entityId " +
                        "AND user_id = :userId");
        query.setParameter("entityId", entityId);
        query.setParameter("userId", userId);

        query.executeUpdate();
    }

    public void deleteRole(Long entityId)  {
        Query query = entityManager.createNativeQuery(
                "DELETE FROM user_role " +
                        "WHERE entity_id = :entityId");
        query.setParameter("entityId", entityId);

        query.executeUpdate();
    }
}
