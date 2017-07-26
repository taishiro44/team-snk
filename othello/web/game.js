/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Draw */

(function (global) {
    "use strict";
    // Class ------------------------------------------------
    function Game() {}

    // Header -----------------------------------------------
    global.Game = Game;
    global.Game.init = init;
    global.Game.getState = getState;
    global.Game.hoge01 = hoge01;
    global.Game.hoge02 = hoge02;
    global.Game.wait = wait;
    global.Game.connect =connect;

    // ------------------------------------------------------
    var COLUMN = 8; // オセロ盤の行数(列数)
    
    var ctx; // 描画コンテキスト
    var state = {}; // 盤面の状態
    var point = { // カーソル位置
        x: 0,
        y: 0
    };
    var initState = { // 盤面の初期状態(1:白, -1:黒, 0:空)
        map: [0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, -1, 1, 0, 0, 0,
              0, 0, 0, 1, -1, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0],
        turn: -1, // ターン(1:先手, -1:後手)
        moveCnt: 0, // 総手数
        selectedCell: { // 選択されたセル
            name: "", // 選択オブジェクト
            column: 0,
            row: 0
        },
        str:""
    };
    var winner = null;
    var passCnt = 0;
    

    /*
     * ゲームの初期化
     */
    function init(context) {
        ctx = context;
        state = copyObject(initState);

        ctx.canvas.addEventListener('mousemove', onMouseMove);
        ctx.canvas.addEventListener('mouseup', onMouseClick);
        window.addEventListener('keydown', onKeyDown);
        Draw.draw(ctx, initState, point);
    }

    /*
     * キーボード入力に対する処理
     * @param {type} e
     * @returns {undefined}
     */
    function onKeyDown(e) {
        var code = e.keyCode;
        
        if (code === 80 && state.str !== "wait") { // Pが押された場合パス
            state.turn *= -1;
            console.log("pass");
        }
    }
    
    
    
    /*
     * 盤上でのカーソルイベント処理
     */
    function onMouseMove(e) {
        getMousePosition(e);
        state.selectedCell = fetchCell(point.x, point.y);
        Draw.draw(ctx, state, point);
    }

    /*
     * 盤上でのクリックイベント処理
     */
    function onMouseClick(e) {
        var column, row;
        
        //自分のターンじゃなければ処理しない
        if(state.str === "wait"){
            return;
        }
            
        state.selectedCell = fetchCell(point.x, point.y);
        column = state.selectedCell.column;
        row = state.selectedCell.row;
        
        if (canPut(column, row, state)){
            flip(column, row, state); // 裏返す
            Draw.draw(ctx, state, point); // 盤面の更新
            state.turn *= -1; // ターンの交代
            state.moveCnt += 1; // 
            state.str = "wait";
            
            if (judge()){ // 勝敗がついた場合
                dispResult(); // 結果を表示する
            }
            
            send();
        }
    }

    /*
     * 引数で与えられた位置に石が置けるかを判定する
     * @param {type} x
     * @param {type} y
     * @param {type} state
     * @returns {Boolean}
     */
    function canPut(x, y, state) {
        if (state.map[x + y*COLUMN] !== 0){ // すでに石が置いてある場合
            return false;
        }
        
        // 8近傍を探索
        for (var dx = -1; dx <= 1; dx++) {
            for (var dy = -1; dy <= 1; dy++) {
                if (dx === 0 && dy === 0) {
                    continue;
                }
                
                var existTgt = false; // 裏返す対象があるか
                // ある1方向について探索
                for (var c = x+dx, r = y+dy; (0<=c && c<COLUMN) && (0<=r && r<COLUMN); c += dx, r += dy){
                    if (state.map[c + r*COLUMN] === 0){ // 何も置かれていない場合false
                        break; // その方向の探索を終了
                    }
                    if (state.map[c + r*COLUMN] === state.turn * -1) { // 自分の石でない場合
                       existTgt = true; // 裏返す対象あり
                       continue; // その方向の探索を続行
                    }
                    else if(state.map[c + r*COLUMN] === state.turn) { // 自分の石の場合
                        if (existTgt){ // 裏返す対象がある場合
                            return true;
                        }
                        else { // 裏返す対象がない場合 
                            break; // その方向の探索を終了する
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    /*
     * 裏返す処理
     * @param {type} x
     * @param {type} y
     * @param {type} state
     * @returns {Boolean}
     */
    function flip(x, y, state) {
        state.map[x + y*COLUMN] = state.turn;
        
        // 8近傍を探索
        for (var dx = -1; dx <= 1; dx++) {
            for (var dy = -1; dy <= 1; dy++) {
                if (dx === 0 && dy === 0) {
                    continue;
                }
                
                var existTgt = false; // 裏返す対象があるか
                // ある1方向について探索
                for (var c = x+dx, r = y+dy; (0<=c && c<COLUMN) && (0<=r && r<COLUMN); c += dx, r += dy){
                    if (state.map[c + r*COLUMN] === 0){ // 何も置かれていない場合false
                        break; // その方向の探索を終了
                    }
                    if (state.map[c + r*COLUMN] === state.turn *-1) { // 自分の石でない場合
                       existTgt = true; // 裏返す対象あり
                       continue; // その方向の探索を続行
                    }
                    else if(state.map[c + r*COLUMN] === state.turn) { // 自分の石の場合
                        if (existTgt){ // 裏返す対象がある場合
                            while(true) { // 逆方向に探索して裏返す
                                c -= dx;
                                r -= dy;
                                
                                if (state.map[c + r*COLUMN] === state.turn *-1) { // 相手の石なら裏返す
                                    state.map[c + r*COLUMN] = state.turn;
                                    continue;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    /*
     * 勝敗の判定を行う
     * @returns {gameL#9.judge}
     */
    function judge() {
        var result = false; // 勝敗がついているか
        var white = 0, black = 0; // 白と黒の数
        
        // 各石の個数集計
        for (var i = 0; i < COLUMN*COLUMN; i++) {
            if (state.map[i] === 1) {
                white++;
            }
            else if (state.map[i] === -1) {
                black++;
            }
        }
        
        //勝敗判定 
        if ((black + white) === COLUMN*COLUMN //全部埋まった時
                || white === 0 //全部黒
                || black === 0 //全部白
                || passCnt >= 2) { //２回連続でパス
            if (black > white) {
                winner = "BLACK";
            }
            else if (white > black) {
                winner = "WHITE";
            }
            else if (white === black) {
                winner = "DRAW";
            }
            result = true;
        } else {
            result = false;
        }
        
        return result;
    }
    
    /*
     * ゲーム結果を表示する 
     * @returns {undefined}
     */
    function dispResult() {
        window.alert("Winner is " + winner + "!");
    }
    
    /*
     * カーソル位置を取得する
     */
    function getMousePosition(e) {
        var rect = e.target.getBoundingClientRect();
        point.x = e.clientX - rect.left;
        point.y = e.clientY - rect.top;
    }

    /*
     * 引数で与えた座標のセルを取得する
     */
    function fetchCell(x, y) {
        var selectedCell = {
            name: "",
            column: 0,
            row: 0
        };
        
        if (Draw.BOARD.x <= x && x <= Draw.BOARD.w && Draw.BOARD.y <= y && y <= Draw.BOARD.h) {
            selectedCell.name = "BOARD";
            selectedCell.column = Math.floor(x / Draw.CELL_SIZE);
            selectedCell.row = Math.floor(y / Draw.CELL_SIZE);
        }
            
        return selectedCell;
    }

    /*
     * オブジェクトをコピーする
     */
    function copyObject(obj) {
        return JSON.parse(JSON.stringify(obj));
    }
    
    /*
     * JSオブジェクトをJSON文字列に変換する
     */
    function jsonTest(obj) {
        return JSON.stringify(obj);
    }
    
    /**
     * JSONをオブジェクトに
     * @returns {undefined}
     */
    function parse(obj){
        return JSON.parse(obj);
    }
    
    /**
     * サーバーに送るメッセージを
     */
    function getState(){
        var result = JSON.stringify(state);
        return result;
    }
    
    /**
     *  対戦相手のターン
     * @returns {undefined}
     */
    function wait(){
        state.str = "wait";
    }
    
    /**
     * 名前思いつかん
     * プレイできる時
     * @returns {undefined}
     */
    function hoge01(){
        alert("your_turn");
        state.str = "canPlay";
    }
    
    /**
     * 名前思いつかんかった
     * 対戦相手がコマを置いたメッセージを受信した時実行される。
     * @param {type} s 
     */
    function hoge02(s){
        state = copyObject(s);
        Draw.draw(ctx, state, point); // 盤面の更新
    }
    
    //ここからwebSocketのための関数
    var webSocket;
    function connect(){
        var ws = new WebSocket("ws://localhost:8080/othello/ws");
        ws.onmessage = function(message) {
            var json = JSON.parse(message.data);
            if(json.state === "wait"){
//                printMessage("Server : wait");
                //待機時の処理
                wait();
            }else if(json.state === "canPlay"){
//                printMessage("Server : canPlay");
                //プレイできる時
                hoge01();
            }else if(json.state === "update"){
//                printMessage("Server : update");
                //盤面更新する時
//                printMessage(json.map);
//                printMessage(json);
                hoge02(json);
            }
        };
        webSocket = ws;
//        $("#connect").disabled = true;
//        $("#send").disabled = false;
    }
    function send() {
        //画面に出力
//        printMessage("Client : send to Server");
        //送信の処理
        var msg = getState();
        webSocket.send(msg);
    }
    
    
    
    
})((this || 0).self || global);

