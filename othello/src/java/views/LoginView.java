/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Account;
import models.AccountsModel;

/**
 *
 * @author kjaeyun
 */
public class LoginView extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.getRequestDispatcher("/accounts/login.jsp").forward(request, response);
    }
            
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountsModel am = new AccountsModel();
        String userid = request.getParameter("userid");
        String userpw = request.getParameter("userpw");
        Account account = am.auth(userid, userpw);
        if(account != null)
            response.sendRedirect("./lobby");
        else{
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User or password incorrect');");
            out.println("location='login';");
            out.println("</script>");
        }
    }
}
