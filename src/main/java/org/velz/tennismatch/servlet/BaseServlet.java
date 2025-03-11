package org.velz.tennismatch.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.exception.BadRequestException;
import org.velz.tennismatch.exception.DatabaseException;
import org.velz.tennismatch.exception.ResourceNotFoundException;

import java.io.IOException;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (DatabaseException | IOException exception) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (BadRequestException exception) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
        } catch (ResourceNotFoundException exception) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
        }
    }
}
