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
import java.util.Map;

import org.json.JSONObject;

public class CreateServelet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(),"utf-8"));
       String in = br.readLine();
        JSONObject jsonObject = new JSONObject(in);
        Map<String, Object> map = jsonObject.toMap();

        String desc = (String) map.get("desc");
        String descValue = desc.split("=")[1];

        Item rsl = HbmTodo.instOf().add(new Item(
                descValue,
                true,
                false
        ));
        resp.sendRedirect(req.getContextPath()+"/createItem.do");
    }
}
