package com.epam.enrollee.controller;

import com.epam.enrollee.controller.command.ActionFactory;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.model.connector.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The type Project servlet.
 * Servlet accepts requests from users, defines the command to be executed and the way to go
 * to the next page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
@WebServlet("/projectServlet")
public class ProjectServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Router router;
        RequestDispatcher dispatcher;
        Command command = ActionFactory.defineCommand(request);
        router = command.execute(request);
        if (router.getType().equals(Router.Type.FORWARD)) {
            dispatcher = getServletContext().getRequestDispatcher(router.getPage());
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(router.getPage());
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.INSTANCE.destroyPool();
        super.destroy();
    }
}