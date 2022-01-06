package hellojpa.jpql;

import javax.persistence.*;

@Entity
public class JpqlOrder {
    @Id
    @GeneratedValue
    private Long id;

    private int orderAmount;

    @Embedded
    private JpqlAddress address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private JpqlProduct product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public JpqlAddress getAddress() {
        return address;
    }

    public void setAddress(JpqlAddress address) {
        this.address = address;
    }

    public JpqlProduct getProduct() {
        return product;
    }

    public void setProduct(JpqlProduct product) {
        this.product = product;
    }
}
