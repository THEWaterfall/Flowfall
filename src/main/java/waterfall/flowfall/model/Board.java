package waterfall.flowfall.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="board")
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="board_collaborator",
            joinColumns = {@JoinColumn(name = "board_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> collaborators;

    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardColumn> boardColumns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getCollaborators() {
        return collaborators;
    }

    @JsonIgnore
    public Set<String> getCollaboratorsNames() {
        Set<String> names = new HashSet<>();
        for(User user: collaborators) {
            names.add(user.getEmail());
        }

        return names;
    }

    public void setCollaborators(Set<User> collaborators) {
        this.collaborators = collaborators;
    }

    public void addCollaborator(User collaborator) {
        if(this.collaborators == null) {
            this.collaborators = new HashSet<>();
        }

        this.collaborators.add(collaborator);
    }

    public List<BoardColumn> getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(List<BoardColumn> boardColumns) {
        this.boardColumns = boardColumns;
    }

}
