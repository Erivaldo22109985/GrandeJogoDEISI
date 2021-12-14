package pt.ulusofona.lp2.deisiGreatGame;

import static org.junit.Assert.*;

public class TestGameManager {

    private GameManager gm;

    private String[] createPlayer(
            String id,
            String name,
            String listProgramming,
            String color

    ){
        String[] r = new String[4];
        r[0] = id;
        r[1] = name;
        r[2] = listProgramming;
        r[3] = color;

        return r;
    }
    private String[][] list_player_1 = new String[3][];
    private String[][] abyssesTools = new String[5][3];
    @org.junit.Before
    public void setUp() throws Exception {
        this.gm = new GameManager();

        this.list_player_1[0] = createPlayer("10", "Player1", "D;Common Lisp;Clojure", "Purple");
        this.list_player_1[1] = createPlayer("2", "Player2", "c2;c++2;java3", "Brown");
        this.list_player_1[2] = createPlayer("3", "Player3", "c3;c++3;java3", "Green");

        int i = 0;
        //abismo
        this.abyssesTools[i][0] = "0";
        this.abyssesTools[i][1] = Integer.toString(Abysses.sintax.ordinal());
        this.abyssesTools[i][2] = "3";

        i++;

        //abismo
        this.abyssesTools[i][0] = "0";
        this.abyssesTools[i][1] = Integer.toString(Abysses.bsod.ordinal());
        this.abyssesTools[i][2] = "6";
        i++;

        //Ferramenta
        this.abyssesTools[i][0] = "1";
        this.abyssesTools[i][1] = Integer.toString(Tools.ajuda_professor.ordinal());
        this.abyssesTools[i][2] = "6";
        i++;

        //Ferramenta
        this.abyssesTools[i][0] = "1";
        this.abyssesTools[i][1] = Integer.toString(Tools.ajuda_professor.ordinal());
        this.abyssesTools[i][2] = "10";

        i++;

        //Ferramenta
        this.abyssesTools[i][0] = "1";
        this.abyssesTools[i][1] = Integer.toString(Tools.functional.ordinal());
        this.abyssesTools[i][2] = "11";
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void createInitialBoard() {
        assertTrue(this.gm.createInitialBoard(this.list_player_1,12, this.abyssesTools));


    }

    @org.junit.Test
    public void moveCurrentPlayer() {
        assertTrue(this.gm.createInitialBoard(this.list_player_1,12));

        assertTrue(this.gm.moveCurrentPlayer(1));

        assertTrue(this.gm.moveCurrentPlayer(3));

        assertEquals(this.gm.getCurrentPlayerID(),10);
    }
}