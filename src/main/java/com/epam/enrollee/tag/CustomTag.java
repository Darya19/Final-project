package com.epam.enrollee.tag;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.type.ApplicationStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

/**
 * The type Custom tag.
 * Tag determines which part of the application list to display based on the page number which
 * comes from pagination command.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class CustomTag extends TagSupport {

    private static final String CONTENT_PAGE = "/prop/pagecontent";
    private static final String ACCEPT_BUTTON = "applications.acceptbutton";
    private static final String REJECT_BUTTON = "applications.rejectbutton";
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ROWS = 3;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        Locale locale = new Locale((String) session.getAttribute(AttributeName.LOCALE));
        ResourceBundle bundle = ResourceBundle.getBundle(CONTENT_PAGE, locale);
        Map<EnrolleeMarkRegister, Enrollee> enrolleeMap =
                (Map<EnrolleeMarkRegister, Enrollee>) session.getAttribute(AttributeName.ENROLLEES);
        List<List<Object>> enrolleeObjects = new ArrayList<>();
        int pageNumber = (int) session.getAttribute(AttributeName.PAGE_NUMBER);
        int fromIndex = pageNumber * PAGE_ROWS - PAGE_ROWS;
        int toIndex = fromIndex + PAGE_ROWS;
        int count = 0;
        try {
            JspWriter out = pageContext.getOut();
            for (Map.Entry<EnrolleeMarkRegister, Enrollee> pair : enrolleeMap.entrySet()) {
                Enrollee enr = pair.getValue();
                EnrolleeMarkRegister markRegister = pair.getKey();
                List<Object> list = new ArrayList<>();
                list.add(enr);
                list.add(markRegister);
                enrolleeObjects.add(list);
            }
            if (toIndex > enrolleeObjects.size()) {
                toIndex = enrolleeObjects.size();
            }
            for (int i = fromIndex; i < toIndex; i++) {
                List<Object> objects = enrolleeObjects.get(i);
                Enrollee enrollee = (Enrollee) objects.get(0);
                EnrolleeMarkRegister register = (EnrolleeMarkRegister) objects.get(1);
                out.write("<tr>");
                out.write("<td>");
                out.write(enrollee.getFirstName() + " " + enrollee.getLastName() + "</td>");
                out.write("<td>");
                out.write(register.getAverageMark() + "</td>");
                if (enrollee.getApplicationStatus().equals(ApplicationStatus.CONSIDERED)) {
                    out.write("<td>");
                    out.write("<button class=\"btn btn-outline-primary btn-sm\"");
                    out.write("type=\"button\"");
                    out.write("onclick='location.href = \"projectServlet?command=change_application_status" +
                            "&enrollee_id=" + enrollee.getUserId() + "&status=accepted\"'>");
                    out.write(bundle.getString(ACCEPT_BUTTON));
                    out.write("</button></td>");
                    out.write("<td>");
                    out.write(" <button class=\"btn btn-outline-primary btn-sm\"");
                    out.write("type=\"button\"");
                    out.write("onclick='location.href = \"projectServlet?command=change_application_status" +
                            "&enrollee_id=" + enrollee.getUserId() + "&status=rejected\"'>");
                    out.write(bundle.getString(REJECT_BUTTON));
                    out.write("</button></td>");
                    out.write("</tr>");
                } else {
                    out.write("<td>" + enrollee.getApplicationStatus() + "</td>");
                }
            }
            out.write("</tbody>");
            out.write("<a href=\"projectServlet?command=pagination&page_number=\"" + (pageNumber - 1) + "\">");
            if (pageNumber > 1) {
                out.write(String.valueOf(pageNumber - 1));
            }
            out.write("</a>");
            out.write(String.valueOf(pageNumber));
            out.write("<a href=\"projectServlet?command=pagination&page_number=\"" + (pageNumber + 1) + "\">");
            if (toIndex < enrolleeObjects.size()) {
                out.write(String.valueOf(pageNumber + 1));
            }
            out.write("</a>");
        } catch (IOException e) {
            logger.log(Level.ERROR, "Impossible write data on jsp page", e);
        }
        return SKIP_BODY;
    }
}