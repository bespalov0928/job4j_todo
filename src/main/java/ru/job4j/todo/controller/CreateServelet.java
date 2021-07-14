package ru.job4j.todo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import ru.job4j.todo.store.HbmTodo;

import java.time.Instant;

public class CreateServelet extends HttpServlet {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        Date date = new Date(System.currentTimeMillis());

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
        String in = br.readLine();
        JSONObject jsonObject = new JSONObject(in);
        Map<String, Object> map = jsonObject.toMap();

        String desc = (String) map.get("desc");
        String descValue = desc.split("=")[1];

        Item rsl = HbmTodo.instOf().add(new Item(
                descValue,
                true,
                false,
                new Date(System.currentTimeMillis())
        ));
        String json = GSON.toJson(rsl);
        writer.println(json);
        writer.flush();
    }
}
