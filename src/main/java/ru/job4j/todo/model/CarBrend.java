package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brends")
public class CarBrend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "carBrend")
    private List<CarModel> models = new ArrayList<>();

    public CarBrend(String name) {
        this.name = name;
    }

    public CarBrend() {
    }

    public void addModel(CarModel carModel){
        this.models.add(carModel);
    }

    public List<CarModel> getModels() {
        return models;
    }
}
