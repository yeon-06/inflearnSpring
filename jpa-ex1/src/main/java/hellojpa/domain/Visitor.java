package hellojpa.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Visitor {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    // unique = true 옵션을 통해 특별하게 만들 수 있지만 제약 조건의 이름이 너무 복잡해서 실무에서 쓰기 부적합
    private String account;

    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Visitor(String account, String name) {
        this.account = account;
        this.name = name;
        this.createDate = new Date();
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

    public String getAccount() {
        return account;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
