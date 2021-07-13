package ru.job4j.todo.controller;

import org.json.JSONObject;
import ru.job4j.todo.model.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class EditServelet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader br = req.getReader();
        String in = br.readLine();
        JSONObject jsonObject = new JSONObject(in);
        Map<String, Object> map = jsonObject.toMap();

        String arrKey = (String) map.get("arr");
        String arrVal = arrKey.split("=")[1];
        String[] arr = arrVal.split(",");
        for (int index = 0; index<arr.length; index++){
            String[] arrItem = arr[index].split("/");
            Integer id = Integer.valueOf(arrItem[0]);
            String desc = arrItem[1];
            boolean done = Boolean.parseBoolean(arrItem[2]);
            boolean rsl = HbmTodo.instOf().edit(id, new Item(desc, true, done));
        }
        resp.sendRedirect(req.getContextPath()+"/createItem.do");

    }
}
