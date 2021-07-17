package ru.job4j.todo.controller;

import com.google.gson.*;
import org.json.JSONObject;
import ru.job4j.todo.model.Acaunt;
import ru.job4j.todo.store.HbmTodo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

public class RegServelet extends HttpServlet {

    public static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        Acaunt rsl = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
        String in = br.readLine();

        JSONObject jsonObject = new JSONObject(in);
        Map<String, Object> map = jsonObject.toMap();

        String email = (String) map.get("email");
        String emailValue = email.split("=")[1];

        String pas = (String) map.get("pas");
        String pasValue = pas.split("=")[1];

        try {
            rsl = HbmTodo.instOf().create(new Acaunt(emailValue, pasValue));
            HttpSession sc = req.getSession();
            sc.setAttribute("user", rsl);
        }catch (Exception e){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            out.println(e.getMessage());
            out.flush();
            return;
        }

        String json = GSON.toJson(rsl);
        out.println(json);
        out.flush();
    }
}
