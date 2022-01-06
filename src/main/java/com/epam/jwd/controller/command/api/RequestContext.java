package com.epam.jwd.controller.command.api;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface RequestContext {
    void addAttributeToJsp(String name, Object attribute);

    void removeAttributeFromJsp(String name);

    Optional<HttpSession> getCurrentSession();

    String getParameterByName(String paramName);

    void invalidateCurrentSession();

    HttpSession createSession();

    String getContextPath();

    String getHeader();
}
