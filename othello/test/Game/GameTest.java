/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tanakataishiro
 */
public class GameTest {

    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class Game.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        Game instance = new Game();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printBoard method, of class Game.
     */
    @Test
    public void testPrintBoard() {
        System.out.println("printBoard");
        Game instance = new Game();
        instance.init();
        instance.printBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Game.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int row = 0;
        int col = 0;
        Game instance = new Game();
        instance.init();
        int[][] expResult = null;
        int[][] result = instance.update(row, col);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * 置ける場所にランダムに置く。
     *
     * Test of update method, of class Game.
     */
    @Test
    public void testUpdate02() {
        System.out.println("update");
        Game instance = new Game();
        instance.init();
        int[][] board;
        int turn;
        List<int[]> canInutList;
        int row, col;
        int index;
        while (true) {
            instance.printBoard();
            if (instance.getEnd()) {
                int victory = instance.getVictory();
                if (victory == 1) {
                    System.out.println("黒の勝ち！");
                }
                if (victory == -1) {
                    System.out.println("白の勝ち！");
                }
                if (victory == 2) {
                    System.out.println("引き分け");
                }
                break;
            }
            turn = instance.getTurn();
            System.out.println((turn == 1) ? "黒の番" : "白の番");
            canInutList = instance.getInputCandidates();
            index = (int) (Math.random() * canInutList.size());
            row = canInutList.get(index)[0];
            col = canInutList.get(index)[1];
            System.out.println("row, col ; " + row + ", " + col);
            board = instance.update(row, col);

        }
    }

    /**
     * Test of getInputCandidates method, of class Game.
     */
    @Test
    public void testGetInputCandidates() {
        System.out.println("getInputCandidates");
        Game instance = new Game();
        instance.init();
        List expResult = null;
        List result = instance.getInputCandidates();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTurn method, of class Game.
     */
    @Test
    public void testGetTurn() {
        System.out.println("getTurn");
        Game instance = new Game();
        int expResult = 0;
        int result = instance.getTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd method, of class Game.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.getEnd();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVictory method, of class Game.
     */
    @Test
    public void testGetVictory() {
        System.out.println("getVictory");
        Game instance = new Game();
        int expResult = 0;
        int result = instance.getVictory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
