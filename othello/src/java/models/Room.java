/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author kjaeyun
 */
public class Room {
    private String roomName;
    private String state;
    private String num_player;
    /*private Account player1;//maker
    private Account player2;*/
    //private Map map;
    
    
    
    
    public String getRoomName(){
        return roomName;
    }
    public void setRoomName(String roomName){
        this.roomName = roomName;
    }
    
    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }
}
