package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baseVacancies")
public class BaseVacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void setVacance(Vacancy vacancy){
        this.vacancies.add(vacancy);
    }

    public List<Vacancy> getVacancies() {
        return this.vacancies = vacancies;
    }
}
