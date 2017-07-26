/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import java.util.Map;

/**
 * server -> client に送信するメッセージ
 * @author tanakataishiro
 */
public class Message {
    private String state;
    private int[] map;
    private int turn;
    private int moveCnt;
    private Map<String, String> selectedCell;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

    public int[] getMap() {
        return map;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setMoveCnt(int moveCnt) {
        this.moveCnt = moveCnt;
    }

    public int getMoveCnt() {
        return moveCnt;
    }

    public void setSelectedCell(Map<String, String> selectedCell) {
        this.selectedCell = selectedCell;
    }

    public Map<String, String> getSelectedCell() {
        return selectedCell;
    }
    
}
