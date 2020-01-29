package com.yura.repairservice.servlet;

import com.yura.repairservice.command.Command;
import com.yura.repairservice.domain.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class AbstractServlet extends HttpServlet {
    private final Map<String, Command> commandNameToCommand;

    public AbstractServlet(Map<String, Command> commandNameToCommand) {
        this.commandNameToCommand = commandNameToCommand;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        String page = commandNameToCommand.getOrDefault(command, request -> "error.jsp").execute(req);
        System.out.println(command + " " + page);

        if ("/".equals(page) || "login.jsp".equals(page)) {
            resp.sendRedirect(page);
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }

    }
}