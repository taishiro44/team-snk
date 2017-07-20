/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/accounts/register.jsp").forward(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userid = request.getParameter("userid");
        String userpw = request.getParameter("userpw");
        String userpw2 = request.getParameter("userpw2");
  
        if (userpw != userpw2)
            System.out.print("no match pw");
        
        AccountsModel am = new AccountsModel();
        am.register(userid, userpw);
        response.sendRedirect("./lobby");
    }
}
