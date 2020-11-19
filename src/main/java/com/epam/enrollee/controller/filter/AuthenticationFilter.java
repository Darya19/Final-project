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
 * Redirects the user when trying to re-register or log in.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    private static final String TO_LOGIN_PAGE = "to_login_page";
    private static final String TO_REGISTER_PAGE = "to_register_page";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        boolean isLoggedIn;
        boolean isRegisterCommand;
        boolean isLogInCommand;
        if (request.getParameter(RequestParameter.COMMAND) != null) {
            isLoggedIn = (session != null && session.getAttribute(AttributeName.ROLE) != null
                    && session.getAttribute(AttributeName.ROLE) != RoleType.GUEST);
            isLogInCommand = request.getParameter(RequestParameter.COMMAND).equals(TO_LOGIN_PAGE);
            isRegisterCommand = request.getParameter(RequestParameter.COMMAND).equals(TO_REGISTER_PAGE);
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