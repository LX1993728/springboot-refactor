package com.refactor.spring.boot.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuxun
 * @apiNote  方式二：通过定义spring bean ServletRegistrationBean 方式
 */
public class RegisterBeanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();
        writer.write("[RegisterBeanServlet] welcome " + name);
        writer.flush();
        writer.close();
    }

}
