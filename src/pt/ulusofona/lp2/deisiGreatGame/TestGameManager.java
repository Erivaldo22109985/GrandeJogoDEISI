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

    @org.junit.Before
    public void setUp() throws Exception {
        this.gm = new GameManager();

        this.list_player_1[0] = createPlayer("10", "Player1", "c;c++;java", "Purple");
        this.list_player_1[1] = createPlayer("2", "Player2", "c2;c++2;java3", "Brown");
        this.list_player_1[2] = createPlayer("3", "Player3", "c3;c++3;java3", "Green");


    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void createInitialBoard() {
        assertTrue(this.gm.createInitialBoard(this.list_player_1,12));

    }

    @org.junit.Test
    public void moveCurrentPlayer() {
        System.out.println("moveCurrentPlayer");
    }
}