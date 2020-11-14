package com.epam.enrollee.controller;

import com.epam.enrollee.controller.command.ActionFactory;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.model.connector.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


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
        HttpSession session = request.getSession();
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
