/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// draw.js
// オセロ盤、石、カーソル位置の描画を行う
(function (global) {
    "use strict";
    // Class ------------------------------------------------
    function Draw() {}

    // Header -----------------------------------------------
    global.Draw = Draw;
    global.Draw.draw = draw;
    global.Draw.BOARD = BOARD;
    global.Draw.CELL_SIZE = CELL_SIZE;
    //-------------------------------------

    var COLUMN = 8; // オセロ盤の行数(列数)
    var BOARD = {// オセロ盤の表示サイズ
        x: 0,
        y: 0,
        w: 400,
        h: 400
    };

    var CELL_SIZE = BOARD.w / COLUMN |0; // 1マスのサイズ

    var BOARD_COLOR = "#03a803 "; // オセロ盤の色
    var WHITE = "#FFFFFF";
    var BLACK = "#000000";
    
    var prevMoveCnt = -1;
    var canvasElements = { // canvas内の図形
        board: null,
        stones: null,
        effect: null
    };

    /*
     * オセロ盤と石を描画する
     * @param {type} ctx
     * @param {type} state
     * @param {type} point
     * @returns {undefined}
     */
    function draw(ctx, state, point) {
        if (prevMoveCnt < 0) {
            canvasElements.board = drawBoard(state);
            canvasElements.stones = drawStones(state);
            canvasElements.effect = drawEffect(state);
            Draw.BOARD = BOARD;
            Draw.CELL_SIZE = CELL_SIZE;
        } else {
//            if (state.moveCnt !== prevMoveCnt) {
                canvasElements.stones = drawStones(state);
//            }
            canvasElements.effect = drawEffect(state, point);
        }

        ctx.clearRect(0, 0, BOARD.w, BOARD.h); // リフレッシュ
        ctx.drawImage(canvasElements.board, 0, 0, BOARD.w, BOARD.h);
        ctx.drawImage(canvasElements.stones, 0, 0, BOARD.w, BOARD.h);
        ctx.drawImage(canvasElements.effect, 0, 0, BOARD.w, BOARD.h);
        prevMoveCnt = state.moveCnt;
    }

    /*
     * オセロ盤を描画する
     * @param {type} state
     * @returns {drawL#8.canvasElements.board|canvasElements.board}
     */
    function drawBoard(state) {
        if (canvasElements.board === null) {
            canvasElements.board = document.createElement("canvas");
            canvasElements.board.width = BOARD.w;
            canvasElements.board.height = BOARD.h;
        }
        var ctx = canvasElements.board.getContext('2d'); // 描画コンテキスト取得
        ctx.clearRect(0, 0, BOARD.w, BOARD.h); // リフレッシュ
        ctx.fillStyle = BOARD_COLOR;

        // マスの描画
        for (var x = 0; x < COLUMN; x++) {
            for (var y = 0; y < COLUMN; y++) {
                ctx.strokeStyle = BLACK;
                ctx.beginPath();
                ctx.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                ctx.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        return canvasElements.board;
    }

    /*
     * 盤面の描画をする
     * 個々の石の描画はdrawStone()で行う
     * @param {type} state
     * @returns {drawL#8.canvasElements.stones|canvasElements.stones}
     */
    function drawStones(state) {
        if (canvasElements.stones === null) {
            canvasElements.stones = document.createElement("canvas");
            canvasElements.stones.width = BOARD.w;
            canvasElements.stones.height = BOARD.h;
        }
        var ctx = canvasElements.stones.getContext('2d'); // 描画コンテキスト取得
        ctx.clearRect(0, 0, BOARD.w, BOARD.h);

        for (var x = 0; x < COLUMN; x++) {
            for (var y = 0; y < COLUMN; y++) {
                if (state.map[x + y * COLUMN] !== 0) {
                    drawStone(ctx, x * CELL_SIZE, y * CELL_SIZE, state.map[x + y * COLUMN]);
                }
            }
        }
        
        return canvasElements.stones;
    }

    /*
     * 石を描画する
     */
    function drawStone(ctx, x, y, owner) {
        var stoneColor; // 石の色

        if (owner > 0) { // ownerが1 => 白
            stoneColor = WHITE;
        } else if (owner < 0) { // ownerが-1 => 黒
            stoneColor = BLACK;
        }
        
        // 影
        ctx.shadowBlur = 5; // ボカシ
        ctx.shadowColor = BLACK;
        ctx.shadowOffsetX = 5;
        ctx.shadowOffsetY = 5;

        // 円
        ctx.beginPath();
        ctx.arc(x + CELL_SIZE / 2, y + CELL_SIZE / 2, CELL_SIZE / 2 * 0.8, 0, Math.PI * 2, true);
        ctx.fillStyle = stoneColor;
        ctx.fill();
        ctx.stroke();

        return ctx;
    }

    /*
     * カーソルが乗っているマスを強調する
     */
    function drawEffect(state) {
        if (!canvasElements.effect) {
            canvasElements.effect = document.createElement("canvas");
            canvasElements.effect.width = BOARD.w;
            canvasElements.effect.height = BOARD.h;
        }
        
        var column = state.selectedCell.column;
        var row = state.selectedCell.row;
        
        if (state.map[column + row * COLUMN] === 0) { // 石が置いていないマスであれば強調
            var ctx = canvasElements.effect.getContext('2d'); // 描画コンテキストの取得　
            var x = column * CELL_SIZE;
            var y = row * CELL_SIZE;

            // マスの強調
            ctx.clearRect(0, 0, BOARD.w, BOARD.h); // リフレッシュ
            ctx.globalAlpha = 0.5; // 透明度
            ctx.fillStyle = WHITE;
            ctx.lineWidth = 1;
            ctx.beginPath();
            ctx.fillRect(x, y, CELL_SIZE, CELL_SIZE);

            // 石のプレビュー
            var stoneColor;
            if (state.turn > 0) { // 先手(1) => 白
                stoneColor = WHITE;
            } else if (state.turn < 0) { // 後手(-1) => 黒
                stoneColor = BLACK;
            }
            ctx.beginPath();
            ctx.globalAlpha = 0.5; // 透明度
            ctx.arc(x + CELL_SIZE / 2, y + CELL_SIZE / 2, CELL_SIZE / 2 * 0.8, 0, Math.PI * 2, true);
            ctx.fillStyle = stoneColor;
            ctx.fill();
            ctx.stroke();
        }

        return canvasElements.effect;
    }

})((this || 0).self || global);
