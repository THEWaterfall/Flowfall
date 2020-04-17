package waterfall.flowfall.model;

import waterfall.flowfall.model.enums.EntityType;

import javax.persistence.*;

@Entity
@Table(name = "secured_entity")
public class SecuredEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private EntityType name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityType getName() {
        return name;
    }

    public void setName(EntityType name) {
        this.name = name;
    }
}
