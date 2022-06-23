package hellojpa.shopping;

import javax.persistence.Entity;

@Entity
public class Book extends Item {

    String author;
    String isbn;

    protected Book() {
    }

    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
}
