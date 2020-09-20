package com.epam.task1.command.impl;

import com.epam.task1.command.Command;

import javax.servlet.http.HttpServletRequest;

import static com.epam.task1.command.PagePath.LOGIN;

public class EmptyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = LOGIN;
        return page;
    }
}
