package ru.job4j.todo.model;

import javax.persistence.*;

@Entity
@Table(name="acaunts")
public class Acaunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;
    private String password;


    public Acaunt(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Acaunt() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return String.format("login: '%s', pas: '%s'", login, password);
    }
}
