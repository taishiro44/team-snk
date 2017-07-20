/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.mongodb.util.JSON;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AccountsModel;
import models.LobbysModel;
import models.Room;
import models.RoomsModel;

/**
 *
 * @author kjaeyun
 */
public class LobbyView extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.print("GET lobby");
        LobbysModel lm = new LobbysModel();
        ArrayList<Room> rooms = lm.getRoomList();
        request.setAttribute("rooms", rooms); // Store products in request scope.
        request.getRequestDispatcher("/game/lobby.jsp").forward(request, response);
    }    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        System.out.print("post lobby");
        doGet(request, response);  
    }
}
