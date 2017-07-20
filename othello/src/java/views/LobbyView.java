/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.LobbysModel;
import models.Room;

/**
 *
 * @author kjaeyun
 */
public class LobbyView extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LobbysModel lm = new LobbysModel();
        ArrayList<Room> rooms = lm.getRoomList();
        request.setAttribute("rooms", rooms); // Store products in request scope.
        request.getRequestDispatcher("/game/lobby.jsp").forward(request, response);
    }    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);  
    }
}
