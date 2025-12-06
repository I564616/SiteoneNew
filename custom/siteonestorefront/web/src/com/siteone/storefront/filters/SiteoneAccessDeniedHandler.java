package com.siteone.storefront.filters;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SiteoneAccessDeniedHandler extends AccessDeniedHandlerImpl {
    private String errorPage = "/login";

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException exception) throws IOException, ServletException {
        if (exception instanceof MissingCsrfTokenException || exception instanceof InvalidCsrfTokenException) {
            if (this.errorPage != null) {
                req.getSession().setAttribute("csrfTokenExpiredDuringLogin","true");
                res.sendRedirect(req.getContextPath() + this.errorPage);
            } else {
                res.sendError(403, exception.getMessage());
            }
        }
        super.handle(req, res, exception);
    }

}