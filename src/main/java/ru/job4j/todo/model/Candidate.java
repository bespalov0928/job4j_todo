package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String experience;
    private int salary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baseVacancies_id")
    private BaseVacancies baseVacancies;

    public Candidate(String name, String experience, int salary) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
    }

    public Candidate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBaseVacancies(BaseVacancies baseVacancies) {
        this.baseVacancies = baseVacancies;
    }

    public BaseVacancies getBaseVacancies() {
        return baseVacancies;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", experience='" + experience + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Candidate candidate = (Candidate) obj;
        return id == candidate.id;
    }
}
