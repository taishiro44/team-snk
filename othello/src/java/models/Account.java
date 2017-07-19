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
public class Account {
    private String userId;
    private String userPw;
    
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    
    public String getUserPw(){
        return userPw;
    }
    public void setUserPw(String userPw){
        this.userPw = userPw;
    }
}
