package ru.job4j.todo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import ru.job4j.todo.model.Acaunt;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.HbmTodo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class EditServelet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        Boolean rsl = false;

        BufferedReader br = req.getReader();
        String in = br.readLine();
        JSONObject jsonObject = new JSONObject(in);
        Map<String, Object> map = jsonObject.toMap();

        Acaunt ac = (Acaunt) req.getSession().getAttribute("user");

        String arrKey = (String) map.get("arr");
        String arrVal = arrKey.split("=")[1];
        String[] arr = arrVal.split(",");
        for (int index = 0; index<arr.length; index++){
            String[] arrItem = arr[index].split("/");
            Integer id = Integer.valueOf(arrItem[0]);
            String desc = arrItem[1];
            boolean done = Boolean.parseBoolean(arrItem[2]);
            Date date = null;

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");
            try {
                date = format.parse(arrItem[3]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rsl = HbmTodo.instOf().edit(id, new Item(desc, true, done, date, ac));
        }
        String json = GSON.toJson(rsl);
        writer.println(json);
        writer.flush();

        //resp.sendRedirect(req.getContextPath()+"/createItem.do");

    }
}
