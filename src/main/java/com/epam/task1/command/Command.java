package com.epam.task1.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    public String execute(HttpServletRequest request);

}
