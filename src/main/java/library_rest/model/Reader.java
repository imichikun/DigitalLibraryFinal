package library_rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reader2dto")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Reader's name cannot be empty")
    private String name;

    @Column(name = "age")
    @Range(min = 14, max = 65, message = "Reader's age should be in the span from 15 to 65")
    private int age;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)   // для того чтобы сохранять дату+время одновременно в postgre надо отдельно
    private LocalDateTime createdAt;    // отметить аннотацией Temporal, в самой БД надо выбрать timestamp without timezone

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "reader")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    public Reader(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Reader() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}