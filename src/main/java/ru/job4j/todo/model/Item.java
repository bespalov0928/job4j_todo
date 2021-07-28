package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private boolean created;
    private boolean done;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Acaunt acaunt;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories = new ArrayList<>();

    public Item() {
    }

   public Item(String desc, boolean created, boolean done, Date timeCreat, Acaunt acaunt) {
        this.description = desc;
        this.created = created;
        this.done = done;
        this.timeCreat = timeCreat;
        this.acaunt = acaunt;
    }


    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    @Override

    public String toString() {
        return String.format("id:'%s', desc: '%s'", id, description);
    }
}
