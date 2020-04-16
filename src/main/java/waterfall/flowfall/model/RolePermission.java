package waterfall.flowfall.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name="secured_entity_id")
    private SecuredEntity entity;

    @ManyToOne
    @JoinColumn(name="permission_id")
    private Permission permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SecuredEntity getEntity() {
        return entity;
    }

    public void setEntity(SecuredEntity entity) {
        this.entity = entity;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
