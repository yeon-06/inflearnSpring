package hellojpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Visitor {

    @Id
    private Long id;
    private String name;

    public Visitor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
