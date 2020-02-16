package waterfall.flowfall.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="role_id")
    private GlobalRole role;

    @Id
    @ManyToOne
    @JoinColumn(name="secured_entity_id")
    private SecuredEntity entity;

    @Id
    @ManyToOne
    @JoinColumn(name="permission_id")
    private Permission permission;
}
