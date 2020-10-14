package com.epam.enrollee.controller;

import com.epam.enrollee.controller.command.ActionFactory;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.exception.CommandException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.enrollee.controller.command.PagePath.ERROR_404;


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
        try {
            Command command = ActionFactory.defineCommand(request);
            page = command.execute(request);
            if (page != null) {
                dispatcher = getServletContext().getRequestDispatcher(page);
            } else {
                dispatcher = getServletContext().getRequestDispatcher(ERROR_404);
            }
            dispatcher.forward(request, response);
        }catch (CommandException e){
            //TODO log можно не ловить
        }
    }
}
