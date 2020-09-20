package com.epam.task1.controller;

import com.epam.task1.command.ActionFactory;
import com.epam.task1.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.task1.command.PagePath.PROJECT_ERROR;

@WebServlet("/projectServlet")
public class ProjectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        RequestDispatcher dispatcher;
        ActionFactory client = new ActionFactory();
        Command command = client.defineCommand(request);
        page = command.execute(request);
        if (page != null) {
            dispatcher = getServletContext().getRequestDispatcher(page);
        } else {
            dispatcher = getServletContext().getRequestDispatcher(PROJECT_ERROR);
        }
        dispatcher.forward(request, response);
    }
}
