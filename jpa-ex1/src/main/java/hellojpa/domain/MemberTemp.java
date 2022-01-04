package hellojpa.domain;

import hellojpa.domain.enums.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MemberTemp {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate createDate;

    private LocalDate modifiedDate;

    public MemberTemp() {
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}