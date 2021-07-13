package ru.job4j.todo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class IndexServelet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        ArrayList<Item> list = (ArrayList<Item>) HbmTodo.instOf().findAll();
        String json = GSON.toJson(list.toArray());
        writer.println(json);
        writer.flush();

    }
}
