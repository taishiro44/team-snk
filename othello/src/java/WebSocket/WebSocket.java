/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import models.Room;
import net.arnx.jsonic.JSON;

/**
 * WebSocketデモ。
 *
 * @author dukelab
 */
@ServerEndpoint(value = "/ws")
public class WebSocket {

    private static Set<Session> ses = new CopyOnWriteArraySet<>();
    private static Room room = new Room();

    //staticイニシャライザ
    static {
        System.out.println("staticイニシャライザで初期化");
        room.setRoomName("testRoom");
        room.setState("0");
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen : " + session);
        ses.add(session);
        if(room.getState().equals("0")){
            room.setPlayer1(session.getId());
            room.setState("1");
        } else if(room.getState().equals("1")){
            room.setPlayer2(session.getId());
            room.setState("2");
        }
        
        if(room.getState().equals("2")){
            this.sendMessage();
        }
        //DEBUG
        if(room.getPlayer1() != null && room.getPlayer2() != null){
            System.out.println("roomName : " + room.getRoomName());
            System.out.println("player1 : " + room.getPlayer1());
            System.out.println("player2 : " + room.getPlayer2());
        }
        
    }
   
    @OnMessage
    public void onMessage(String text, Session session) {
        System.out.println("onMessage : " + text);

        if(room.getState().equals("2")){ //プレイヤーが揃っていたら
            String destId = "";
            //roomのplyer1, 2から送信先のセッションidを取得
            if(room.getPlayer1().equals(session.getId())){
                destId = room.getPlayer2();
            }else if(room.getPlayer2().equals(session.getId())){
                destId = room.getPlayer1();
            }
            //取得したセッションにメッセージを送信する。
            for(Session s : ses){
                if(s.getId().equals(destId)){
                    Message update = JSON.decode(text, Message.class);
                    update.setState("update");
                    String json = JSON.encode(update);
                    s.getAsyncRemote().sendText(json);
                } else {
                    //他のプレイヤーには待機してもらう
                    Message wait = new Message();
                    wait.setState("wait");
                    String json = JSON.encode(wait);
                    s.getAsyncRemote().sendText(json);
                }
            }
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose : " + session);
        if(session.getId().equals(room.getPlayer1()) ||
                session.getId().equals(room.getPlayer2())){
            room.setState("0");
        }
        ses.remove(session);
    }

    /**
     * ゲーム開始時に実行する。
     * どっちが先手かをクライアントに送信する。
     * 先手じゃない方には待機するよう、メッセージを送信する。
     */    
    public  void sendMessage() {
        if(room.getState().equals("2")){ //プレイヤーが揃っていたら
            boolean hoge = true;
            Message message = new Message();
            String json;
            for(Session s : ses){
                if(hoge){
                    //先手
                    message.setState("canPlay");
                    hoge = !hoge;
                }else {
                    //後手
                    message.setState("wait");
                }
                json = JSON.encode(message);
                System.out.println(json);
                s.getAsyncRemote().sendText(json);
            }
            System.out.println("player1 : " + room.getPlayer1());
            System.out.println("player2 : " + room.getPlayer2());
        }
    }
    
}