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
    global.Draw.BOARD_SIZE = BOARD_SIZE;
    global.Draw.CELL_SIZE = CELL_SIZE;
    //-------------------------------------

    var COLUMN = 8; // 行数(列数)
    var BOARD_SIZE = {// オセロ盤の表示サイズ
        x: 100,
        y: 100,
        w: 500,
        h: 500
    };

    var CELL_SIZE = BOARD_SIZE.w / COLUMN; // 1マスのサイズ

    var BOARD_COLOR = "#03a803 "; // オセロ盤の色
    var LINE_COLOR = "#000000"; // 罫線の色

    var COLOR_WHITE = "#FFFFFF";
    var COLOR_BLACK = "#000000";
    var COLOR_SELECT = "#FFFFFF";

    var COLOR_PANEL_4 = "#006400 ";
    var COLOR_PANEL_5 = "#03a803 ";
    var COLOR_PANEL_6 = "#04cb04";

    var state_cache = null;
    var prev_revision = -1;
    var canvasElements = {
        board: null,
        stones: null,
        effect: null
    };

    function draw(ctx, state, point) {
        if (prev_revision < 0) {
            canvasElements.board = drawBoard(state);
            canvasElements.stones = drawStones(state);
            canvasElements.effect = drawEffect(state);
            Draw.BOARD_SIZE = BOARD_SIZE;
            Draw.CELL_SIZE = CELL_SIZE;
        } else {
            if (state.revision !== prev_revision) {
                canvasElements.stones = drawStones(state);
            }
            canvasElements.effect = drawEffect(state, point);
        }

        ctx.clearRect(0, 0, BOARD_SIZE.w, BOARD_SIZE.h);
        ctx.drawImage(canvasElements.board, 0, 0, BOARD_SIZE.w, BOARD_SIZE.h);
        ctx.drawImage(canvasElements.stones, 0, 0, BOARD_SIZE.w, BOARD_SIZE.h);
        ctx.drawImage(canvasElements.effect, 0, 0, BOARD_SIZE.w, BOARD_SIZE.h);
        prev_revision = state.revision;
    }

    // オセロ盤の描画
    function drawBoard(state) {
        if (canvasElements.board === null) {
            canvasElements.board = document.createElement("canvas");
            canvasElements.board.width = BOARD_SIZE.w;
            canvasElements.board.height = BOARD_SIZE.h;
        }
        var ctx = canvasElements.board.getContext('2d'); // 描画コンテキスト取得
        ctx.clearRect(0, 0, BOARD_SIZE.w, BOARD_SIZE.h);

        ctx.fillStyle = BOARD_COLOR;

        // マスの描画
        for (var x = 0; x < COLUMN; x++) {
            for (var y = 0; y < COLUMN; y++) {
                ctx.strokeStyle = LINE_COLOR;
                ctx.beginPath();
                ctx.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                ctx.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        return canvasElements.board;
    }

    // 盤面の描画
    function drawStones(state) {
        if (canvasElements.stones === null) {
            canvasElements.stones = document.createElement("canvas");
            canvasElements.stones.width = BOARD_SIZE.w;
            canvasElements.stones.height = BOARD_SIZE.h;
        }
        var ctx = canvasElements.stones.getContext('2d'); // 描画コンテキスト取得
        ctx.clearRect(0, 0, BOARD_SIZE.w, BOARD_SIZE.h);

        for (var x = 0; x < COLUMN; x++) {
            for (var y = 0; y < COLUMN; y++) {
                if (state.map[x + y * COLUMN] !== 0) {
                    drawStone(ctx, x * CELL_SIZE, y * CELL_SIZE, state.map[x + y * COLUMN]);
                }
            }
        }
        return canvasElements.stones;
    }

    // 石の描画
    function drawStone(ctx, x, y, number) {
        var fillColor;

        if (number > 0) {
            fillColor = COLOR_WHITE;
        } else if (number < 0) {
            fillColor = COLOR_BLACK;
        }

        ctx.shadowBlur = 20;
        ctx.shadowColor = "rgba(0, 0, 0, 1)";
        ctx.shadowOffsetX = 5;
        ctx.shadowOffsetY = 5;

        // 円の描画
        ctx.beginPath();
        ctx.arc(x + CELL_SIZE / 2, y + CELL_SIZE / 2, CELL_SIZE / 2 * 0.8, 0, Math.PI * 2, true);
        ctx.fillStyle = fillColor;
        ctx.fill();
        ctx.stroke();

        ctx.shadowColor = "#000000";
        ctx.shadowBlur = 0;
        ctx.shadowOffsetX = 0;
        ctx.shadowOffsetY = 0;

        return ctx;
    }

    function drawEffect(state) {
        if (!canvasElements.effect) {
            canvasElements.effect = document.createElement("canvas");
            canvasElements.effect.width = BOARD_SIZE.w;
            canvasElements.effect.height = BOARD_SIZE.h;
        }
        var ctx = canvasElements.effect.getContext('2d');
        var x = (state.selected.value % COLUMN | 0) * CELL_SIZE;
        var y = (state.selected.value / COLUMN | 0) * CELL_SIZE;

        ctx.clearRect(0, 0, BOARD_SIZE.w, BOARD_SIZE.h);
        ctx.globalAlpha = 0.5;
        ctx.fillStyle = COLOR_SELECT;
        ctx.lineWidth = 1;
        ctx.beginPath();
        ctx.fillRect(x, y, CELL_SIZE, CELL_SIZE);

        return canvasElements.effect;
    }

    function objCopy(obj) {
        return JSON.parse(JSON.stringify(obj));
    }

})((this || 0).self || global);
