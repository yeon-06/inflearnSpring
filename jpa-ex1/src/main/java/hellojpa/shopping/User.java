package hellojpa.shopping;

import java.time.LocalDateTime;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String account;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String name;

    private LocalDateTime createDate;

    protected User() {
    }

    public User(String account, String name, Team team) {
        this.account = account;
        this.name = name;
        this.createDate = LocalDateTime.now();
        changeTeam(team);
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.addUser(this);
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", team=" + team +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
