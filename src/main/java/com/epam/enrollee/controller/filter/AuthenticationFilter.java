package com.epam.enrollee.controller.filter;


import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.model.type.RoleType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Authentication filter.
 */
@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        boolean isLoggedIn;
        boolean isRegisterCommand;
        boolean isLogInCommand;
        if (request.getParameter(RequestParameter.COMMAND) != null){
        isLoggedIn = (session != null && session.getAttribute(AttributeName.ROLE) != null
                && session.getAttribute(AttributeName.ROLE) != RoleType.GUEST);
        isLogInCommand = request.getParameter(RequestParameter.COMMAND).equals("to_login_page");
            isRegisterCommand = request.getParameter(RequestParameter.COMMAND).equals("to_register_page");
            if (isLoggedIn && (isLogInCommand || isRegisterCommand)) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.MAIN);
                    dispatcher.forward(servletRequest, servletResponse);
            } else
                filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
