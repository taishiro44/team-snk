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
 *tt
 * @author kjaeyun
 */
public class AccountsModel {
    private static final String DB_ACCOUNT_COLLECTION = "account";
    private static final String dbName = "othello";
    
    private static Mongo m;
    private DB db;
    private DBCollection coll;
    
    
    public AccountsModel() {
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
    public boolean checkUserid(String userId){
        DBObject account1 = new BasicDBObject();
        account1.put("userId", userId);
        
        DBObject result = coll.findOne(account1);
        return result == null;
    }
    public Account register(String userId, String userPw){
        Account account = new Account();
        account.setUserId(userId);
        account.setUserPw(userPw);

        // 指定パラメータから MongoDB 格納用オブジェクトを生成する．
        DBObject account1 = new BasicDBObject("userId",userId);
        
        if(coll.findOne(account1) != null ){
            return null;
        }
        account1.put("userPw",userPw);
        coll.insert(account1);
        
        return account;
        /*
        try{
            coll.insert(user);
            return accounts;
        } catch(MongoException e){
            throw new TEMFatalException(e);
        }*/    
    }
    
    public Account auth(String userId, String userPw){
        DBObject account1 = new BasicDBObject();
        account1.put("userId", userId);
        account1.put("userPw", userPw);
        
        DBObject result = coll.findOne(account1);
        
        if (result == null){
            return null;
        }else{
            Account account = new Account();
            account.setUserId(userId);
            account.setUserPw(userPw);
            return account;
        }
    }
}
