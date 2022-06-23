package hellojpa.shopping;

import javax.persistence.Entity;

@Entity
public class Computer extends Item {

    private int core;

    protected Computer() {
    }

    public Computer(String name, int price, int stockQuantity, int core) {
        super(name, price, stockQuantity);
        this.core = core;
    }

    public int getCore() {
        return core;
    }
}
