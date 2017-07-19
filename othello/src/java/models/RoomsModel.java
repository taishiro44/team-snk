/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;

/**
 *
 * @author kjaeyun
 */
public class RoomsModel {
    private static final String DB_ACCOUNT_COLLECTION = "game";
    private static final String dbName = "othello";
    
    private static Mongo m;
    private DB db;
    private DBCollection coll;
    
    
    public RoomsModel() {
        try {
            m = new Mongo("localhost", 27017);
            db = m.getDB(dbName);
        } catch (UnknownHostException | MongoException e) {
            // TODO 自動生成された catch ブロック
            // どうすんだろこれ
            e.printStackTrace();
        }
        coll = db.getCollection(DB_ACCOUNT_COLLECTION);
    }
    
    public Room makeRoom(String roomName){
        Room room = new Room();
        room.setRoomName(roomName);
        System.out.print("Room ready");
        DBObject room_query = new BasicDBObject("roomName",roomName);
        System.out.print("51");
        coll.find(room_query);
        System.out.print("51.5");
        if(coll.find(room_query) != null ){
            System.out.print("RoomNameあるぞ");
        }
        System.out.print("52");
        room_query.put("state","0");
        coll.insert(room_query);
        
        System.out.print("Room OK");
        
        return room;
        /*
        try{
            coll.insert(user);
            return accounts;
        } catch(MongoException e){
            throw new TEMFatalException(e);
        }*/    
    }
}
