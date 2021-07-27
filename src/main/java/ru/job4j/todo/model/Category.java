package ru.job4j.todo.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

}
