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
    private String state; //0 : 終了　1 : player待ち 2 : 入場不可能  3 : ゲーム中
    private String num_player;
    private String player1; // user eig id
    private String player2; // user eig id
    
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
    public String getPlayer1(){
        return player1;
    }
    public void setPlayer1(String player1){
        this.player1 = player1;
    }
    public String getPlayer2(){
        return player2;
    }
    public void setPlayer2(String player2){
        this.player2 = player2;
    }
    
    public int whoIsFirst(){
        return 1;
    }
}
