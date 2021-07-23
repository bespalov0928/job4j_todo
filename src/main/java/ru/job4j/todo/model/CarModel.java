package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "models")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "carBrend_id")
    private CarBrend carBrend;

    //private List<CarBrend> brends = new ArrayList<>();

    public CarModel(String name, CarBrend carBrend) {
        this.name = name;
        this.carBrend = carBrend;
    }

    public CarModel() {
    }
}
