package ru.job4j.todo.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public CarModel(String name) {
        this.name = name;
    }

    public CarModel() {
    }
}
