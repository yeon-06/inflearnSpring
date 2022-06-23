package hellojpa.shopping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String account;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String name;

    protected User() {
    }

    public User(String account, String name, Team team) {
        this.account = account;
        this.name = name;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", team=" + team +
                ", name='" + name + '\'' +
                '}';
    }
}
