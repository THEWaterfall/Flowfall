package waterfall.flowfall.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.enums.EntityType;
import waterfall.flowfall.model.enums.PermissionType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;

@Repository
public class RolePermissionRepository {

    @Autowired
    private EntityManager entityManager;

    public boolean hasAccess(long userId, long entityId, EntityType entityType, PermissionType permissionType) {
        Query query = entityManager.createNativeQuery(
                "SELECT count(*) FROM role_permission" +
                        " JOIN user_role on role_permission.role_id = user_role.role_id" +
                        " JOIN secured_entity on role_permission.secured_entity_id = secured_entity.id" +
                        " JOIN permission on role_permission.permission_id = permission.id" +

                        " WHERE user_id = :userId" +
                        " AND user_role.entity_id = :entityId" +
                        " AND secured_entity.name = :entity" +
                        " AND permission.name = :permission"
        );

        query.setParameter("userId", userId);
        query.setParameter("entityId", entityId);
        query.setParameter("entity", entityType.getLiteral());
        query.setParameter("permission", permissionType.getLiteral());

        return !((BigInteger) query.getSingleResult()).equals(BigInteger.ZERO);
    }

}
