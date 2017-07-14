/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tanakataishiro
 */
public class Game {

    private int turn; //true : black, false : white
    private int[][] board;
    private int victory;
    boolean isEnd;
    boolean isPass;
    int passCount;
    private static final byte[][] direction = { //探索方向
        {-1, -1},
        {0, -1},
        {1, -1},
        {-1, 0},
        {1, 0},
        {-1, 1},
        {0, 1},
        {1, 1}
    };
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int EMPTY = 0;
    private static final int BLACK = 1;
    private static final int WHITE = -1;
    private static final int WALL = 9; //番兵
    private static final int DRAW = 2;

    public Game() {

    }

    /**
     * ゲームを初期化する。
     */
    public void init() {
        //黒が先手
        this.turn = BLACK;
        this.isPass = false;
        this.passCount = 0;
        //勝敗情報
        this.isEnd = false;
        this.victory = DRAW;
        //オセロ盤を初期状態にする
        //@@@@@@@@@@
        //@________@
        //@________@
        //@________@
        //@___ox___@
        //@___xo___@
        //@________@
        //@________@
        //@________@
        //@@@@@@@@@@
        this.board = new int[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                this.board[row][col] = EMPTY;
            }
        }
        this.board[HEIGHT / 2 - 1][WIDTH / 2 - 1] = WHITE;
        this.board[HEIGHT / 2][WIDTH / 2] = WHITE;
        this.board[HEIGHT / 2][WIDTH / 2 - 1] = BLACK;
        this.board[HEIGHT / 2 - 1][WIDTH / 2] = BLACK;
        for (int i = 0; i < WIDTH; i++) {
            this.board[0][i] = this.board[HEIGHT - 1][i] = WALL;
        }
        for (int i = 1; i < HEIGHT - 1; i++) {
            this.board[i][0] = this.board[i][WIDTH - 1] = WALL;
        }
    }

    /**
     * オセロ盤面を更新する。<br>
     *
     * @param row 行
     * @param col 列
     * @return 10 x 10 のオセロ盤面(盤面は8x8、その周囲に番兵があるため10x10)
     */
    public int[][] update(int row, int col) {
        //パスの判定
        this.isPass = this.checkPass(row, col);
        if (this.isPass) { //パスの場合
            //勝敗の判定
            this.judge();
            if (this.isEnd) {
                //終了の場合何かを返す。
                return this.board;
            }
            //手番の交代
            this.turn *= -1;
            return this.board;
        }
        //入力が有効かの判定
        boolean isInputEnabled;
        isInputEnabled = this.checkInput(row, col);
        if (isInputEnabled) {
            //裏返しの処理
            this.flip(row, col);
            //勝敗の判定
            this.judge();
            if (this.isEnd) {
                //終了の場合何かを返す。
                return this.board;
            }
            //手番の交代
            this.turn *= -1;
            return this.board;
        } else {
            //入力が有効でない場合
            return this.board;
        }
    }

    /**
     * パスかどうかの判定を行う。
     *
     * @param row
     * @param col
     * @return true:パス, false:パスじゃない
     */
    private boolean checkPass(int row, int col) {
        //passの判定
        return (row == -1 && col == -1);
    }

    /**
     * 入力の判定を行う。
     *
     * @param row
     * @param col
     * @return false:置けない, true:置ける
     */
    private boolean checkInput(int row, int col) {
        boolean result = false;
        //何も置いていない場所か判定
        if (!(this.board[row][col] == EMPTY)) {
            return result;
        }
        int X = 0; //index
        int Y = 1; //index
        int j, k;
        int opponent = this.turn * -1; //相手の色
        //8近傍を探索。
        for (int i = 0; i < direction.length; i++) {
            j = direction[i][Y] + row;
            k = direction[i][X] + col;
            if (this.board[j][k] == opponent) {
                //相手の色があればその方向に探索し続ける。
                while (true) {
                    j += direction[i][Y];
                    k += direction[i][X];
                    //相手の色なら探索し続ける
                    if (this.board[j][k] == opponent) {
                        continue;
                    }
                    //自分の色なら裏返す。
                    if (this.board[j][k] == this.turn) {
                        result = true;
                        i = direction.length; //forループを抜ける
                    }
                    //自分の色でも相手の色でもなければ終了
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 裏返す処理を行う。
     *
     * @param row
     * @param col
     */
    private void flip(int row, int col) {
        int X = 0;
        int Y = 1;
        int j, k;
        int opponent = this.turn * -1; //相手の色
        //入力の場所に置く
        this.board[row][col] = this.turn;
        //8近傍を探索。
        for (int i = 0; i < direction.length; i++) {
            j = direction[i][Y] + row;
            k = direction[i][X] + col;
            if (this.board[j][k] == opponent) {
                //相手の色があればその方向に探索し続ける。
                while (true) {
                    j += direction[i][Y];
                    k += direction[i][X];
                    //相手の色なら探索し続ける
                    if (this.board[j][k] == opponent) {
                        continue;
                    }
                    //自分の色なら裏返す。
                    if (this.board[j][k] == this.turn) {
                        while (true) { //裏返しながら逆方向に探索
                            j -= direction[i][Y];
                            k -= direction[i][X];
                            //裏返す
                            if (this.board[j][k] == opponent) {
                                this.board[j][k] = this.turn;
                                continue;
                            }
                            break;
                        }
                    }
                    //自分の色でも相手の色でもなければ終了
                    break;
                }
            }
        }
    }

    /**
     * 勝敗の判定を行う。
     *
     * @return
     */
    private void judge() {
        boolean result;
        int num = (WIDTH - 2) * (HEIGHT - 2);
        int white = 0;
        int black = 0;
        //コマが置かれたマスの数を数える
        for (int row = 1; row < HEIGHT - 1; row++) {
            for (int col = 1; col < WIDTH - 1; col++) {
                if (this.board[row][col] == BLACK) {
                    black++;
                }
                if (this.board[row][col] == WHITE) {
                    white++;
                }
            }
        }
        //パスの回数をカウント
        if (this.isPass) {
            this.passCount++;
        } else {
            this.passCount = 0;
        }
        //勝敗判定 
        if ((black + white) == num
                || //全部埋まった時
                white == 0
                || //全部黒
                black == 0
                || //全部白
                this.passCount >= 2) { //２回連続でパス
            if (black > white) {
                this.victory = BLACK;
            }
            if (white > black) {
                this.victory = WHITE;
            }
            if (white == black) {
                this.victory = DRAW;
            }
            result = true;
        } else {
            result = false;
        }
        this.isEnd = result;
    }

    /**
     * 入力可能な行と列を取得する。<br>
     * もし、入力可能な行と列が無い場合、{-1, -1}をaddしたListを返す。
     *
     * @return
     */
    public List<int[]> getInputCandidates() {
        List<int[]> resultList = new ArrayList<>();
        boolean isInputEnabled;
        for (int row = 1; row < HEIGHT - 1; row++) {
            for (int col = 1; col < WIDTH - 1; col++) {
                if (this.board[row][col] == EMPTY) {
                    isInputEnabled = this.checkInput(row, col);
                    //入力可能ならばリストに格納
                    if (isInputEnabled) {
                        int[] point = new int[2]; //行と列を格納する。
                        point[0] = row;
                        point[1] = col;
                        resultList.add(point);
                    }
                }
            }
        }
        if (resultList.isEmpty()) {
            int[] point = new int[2]; //行と列を格納する。
            point[0] = -1;
            point[1] = -1;
            resultList.add(point);
        }
        return resultList;
    }

    /**
     * どちらのターンかを取得する。<br>
     * 1 : 黒 <br>
     * -1 : 白
     *
     * @return
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * ゲーム終了かどうかを取得する。
     *
     * @return
     */
    public boolean getEnd() {
        return this.isEnd;
    }

    /**
     * 勝敗の結果を返す。
     *
     *
     * @return 1 : 黒, -1 : 白, 2 : 引き分け
     */
    public int getVictory() {
        return this.victory;
    }

    /**
     * \
     * 盤面を標準出力する。
     */
    public void printBoard() {
        String str;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                switch (this.board[row][col]) {
                    case EMPTY:
                        str = "_";
                        break;
                    case BLACK:
                        str = "◯";
                        break;
                    case WHITE:
                        str = "●";
                        break;
                    case WALL:
                        str = "@";
                        break;
                    default:
                        str = "";
                }
                System.out.print(str);
            }
            System.out.println();
        }
    }

}
