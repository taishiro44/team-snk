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
import models.RoomsModel;

/**
 *
 * @author kjaeyun
 */
public class MakeroomView extends HttpServlet{ 
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.getRequestDispatcher("/game/makeroom.jsp").forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
  
        RoomsModel rm = new RoomsModel();
        rm.makeRoom(roomName);
        
        response.sendRedirect("../lobby");
    }
}
