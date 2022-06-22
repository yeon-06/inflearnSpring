package hellojpa.shopping;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ShoppingMain {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static void main(String[] args) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Team team = createTeam();
            User user = createUser(team);
            Item item = createItem();
            Order order = createOrder(user);
            createOrderItem(item, order);

            clear();

            Order findOrder = entityManager.find(Order.class, order.getId());
            printList(findOrder.getOrderItems());

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private static Team createTeam() {
        Team team = new Team("플랫폼팀");
        entityManager.persist(team);
        return team;
    }

    private static User createUser(Team team) {
        User user = new User("yeonlog", "연로그", team);
        entityManager.persist(user);
        return user;
    }

    private static Item createItem() {
        Item item = new Item("상품", 1_000, 10);
        entityManager.persist(item);
        return item;
    }

    private static Order createOrder(User user) {
        Order order = new Order(user, Status.ORDER);
        entityManager.persist(order);
        return order;
    }

    private static void createOrderItem(Item item, Order order) {
        OrderItem orderItem = new OrderItem(order, item, 2_000, 2);
        entityManager.persist(orderItem);
    }

    private static void clear() {
        entityManager.flush();
        entityManager.clear();
    }

    private static <T> void printList(List<T> list) {
        list.forEach(System.out::println);
    }
}
