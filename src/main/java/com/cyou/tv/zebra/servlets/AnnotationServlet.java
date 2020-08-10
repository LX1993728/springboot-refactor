package com.cyou.tv.zebra.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuxun
 * @apiNote 方式一: 使用注解的方式来注册一个servlet
 * 注意：为了使Servlet的注解生效，需要在启动类上添加 @ServletComponentScan注解
 */
@WebServlet(urlPatterns = "/annotation", loadOnStartup = 1)
public class AnnotationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();
        writer.write("[AnnotationServlet] welcome " + name);
        writer.flush();
        writer.close();

    }
}
