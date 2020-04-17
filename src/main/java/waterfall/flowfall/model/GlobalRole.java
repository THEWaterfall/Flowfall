package waterfall.flowfall.model;

import waterfall.flowfall.model.enums.UserGlobalRole;

import javax.persistence.*;

@Entity
@Table(name="global_role")
public class GlobalRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private UserGlobalRole name;

    public GlobalRole() {

    }

    public GlobalRole(UserGlobalRole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserGlobalRole getName() {
        return name;
    }

    public void setName(UserGlobalRole name) {
        this.name = name;
    }

}
