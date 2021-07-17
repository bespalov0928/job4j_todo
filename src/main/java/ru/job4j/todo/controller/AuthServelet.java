package ru.job4j.todo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

public class AuthServelet extends HttpServlet {
    public static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        Boolean rsl = false;
        Acaunt acaunt = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
        String in = br.readLine();
        JSONObject jsonObject = new JSONObject(in);

        Acaunt ac = (Acaunt) req.getSession().getAttribute("user");
        if (ac != null) {
            String json = GSON.toJson(ac.getLogin());
            out.println(json);
            out.flush();
            return;
        }

        Map<String, Object> map = jsonObject.toMap();

        String email = (String) map.get("email");
        String emailVolue = email.split("=")[1];

        String pas = (String) map.get("pas");
        String pasVolue = pas.split("=")[1];


        try {
            acaunt = HbmTodo.instOf().findAcaunt(emailVolue);
            if (acaunt != null) {
                rsl = true;
                HttpSession sc = req.getSession();
                sc.setAttribute("user", acaunt);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Ошибка авторизации");
            out.println(e.getMessage());
            out.flush();
            return;
        }

        String gson = GSON.toJson(rsl);
        out.println(gson);
        out.flush();
    }
}
