package com.epam.jwd.controller.tag;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

/**
 * Personal jsp tag
 */
public class GreetingTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(GreetingTag.class);
    private static final String USER_WELCOME_MESSAGE = "Hello, %s";
    private static final String DEFAULT_WELCOME_MESSAGE = "Hello!";
    private static final String USER_NAME_SESSION_ATTRIB = "userName";

    @Override
    public int doStartTag() throws JspException {
        final String tagResultText = buildWelcomeMessage();
        printMessage(tagResultText);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    /**
     * Method for building welcome message for current user
     *
     * @return greeting string
     */
    private String buildWelcomeMessage() {
        return Optional.ofNullable(pageContext.getSession())
                .map(session -> session.getAttribute(USER_NAME_SESSION_ATTRIB))
                .map(name -> String.format(USER_WELCOME_MESSAGE, name))
                .orElse(DEFAULT_WELCOME_MESSAGE);
    }

    /**
     * Method for printing generated greeting text
     *
     * @param tagResultText - greeting text
     * @throws JspException - if JspException were thrown
     */
    private void printMessage(String tagResultText) throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            out.write(tagResultText);
        } catch (IOException e) {
            logger.error(e.getMessage() + e);
            throw new JspException(e);
        }
    }
}
