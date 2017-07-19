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
public class Register extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userid = request.getParameter("userid");
        String userpw = request.getParameter("userpw");
        String userpw2 = request.getParameter("userpw2");
  
        AccountsModel am = new AccountsModel();
        am.register(userid, userpw);
        
    }
}
