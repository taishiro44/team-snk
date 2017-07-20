/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author kjaeyun
 */
public class LobbysModel {
    private static final String DB_ACCOUNT_COLLECTION = "game";
    private static final String dbName = "othello";
    
    private static Mongo m;
    private DB db;
    private DBCollection coll;
    
    public LobbysModel() {
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
    

    public ArrayList getRoomList(){
        ArrayList<Room> rooms =new ArrayList<Room>();
        DBObject query = new BasicDBObject();
        
        try{
            DBCursor cursor = coll.find();
            while(cursor.hasNext()){
                DBObject object = cursor.next();
                Room room = new Room();
                room.setRoomName((String) object.get("roomName"));
                room.setState((String) object.get("state"));
                rooms.add(room);
            }
        } catch(MongoException e){
           // throw new TEMFatalException(e);
        } 
        return rooms;
    }
    
}
