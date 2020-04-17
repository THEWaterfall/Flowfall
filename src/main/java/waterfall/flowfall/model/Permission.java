package waterfall.flowfall.model;

import waterfall.flowfall.model.enums.PermissionType;

import javax.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private PermissionType name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionType getName() {
        return name;
    }

    public void setName(PermissionType name) {
        this.name = name;
    }
}
