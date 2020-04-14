package waterfall.flowfall.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@Transactional
public class UserRoleRepository {

    @Autowired
    private EntityManager entityManager;

    public void addRole(long entityId, long userId, long roleId) {
        Query query = entityManager.createNativeQuery(
                "INSERT INTO user_role(entity_id, user_id, role_id)" +
                         "VALUES(:entityId, :userId, :roleId)");
        query.setParameter("entityId", entityId);
        query.setParameter("userId", userId);
        query.setParameter("roleId", roleId);

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
