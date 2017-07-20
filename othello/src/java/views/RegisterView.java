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
import models.AccountsModel;

/**
 *
 * @author kjaeyun
 */
public class RegisterView extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/accounts/register.jsp").forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String userid = request.getParameter("userid");
        String userpw = request.getParameter("userpw");
        String userpw2 = request.getParameter("userpw2");
        AccountsModel am = new AccountsModel();
        if(am.checkUserid(userid)){ 
            if (userpw == null ? userpw2 != null : !userpw.equals(userpw2)){
                out.println("<script type=\"text/javascript\">");
                out.println("alert('password is not match');");
                out.println("location='register';");
                out.println("</script>");
            }
            else{   
                am.register(userid, userpw);
                response.sendRedirect("./lobby");
            }
        }else{
                out.println("<script type=\"text/javascript\">");
                out.println("alert('UserId exists already');");
                out.println("location='register';");
                out.println("</script>");
        }
    }
}
