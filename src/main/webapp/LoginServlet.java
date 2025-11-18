package com.example.webapp;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(user.equals("admin") && pass.equals("admin123")) {
            out.println("<h2>Login Successful!</h2>");
            out.println("<p>Welcome, " + user + "</p>");
        } else {
            out.println("<h2>Invalid Login!</h2>");
            out.println("<a href='login.jsp'>Try Again</a>");
        }
    }
}
