package com.epam.enrollee.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CustomTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ENTRIES = 5;
    private static final String CONTENT_PAGE = "/prop/contentpage";
    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }
}
