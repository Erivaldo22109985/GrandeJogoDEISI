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
        this.abyssesTools[i][1] = Integer.toString(Abysses.syntax.ordinal());
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
        assertTrue(this.gm.reactToAbyssOrTool() == null);

        assertTrue(this.gm.moveCurrentPlayer(3));
        assertTrue(this.gm.reactToAbyssOrTool() == null);

        assertEquals(this.gm.getCurrentPlayerID(),10);
    }

    @org.junit.Test
    public void testPosicao() {
        String[][] playerInfo = new String[2][4];

        playerInfo[0][0] = "1";
        playerInfo[0][1] = "Anabela";
        playerInfo[0][2] = "Java;Kotlin";
        playerInfo[0][3] = "Purple";
        playerInfo[1][0] = "2";
        playerInfo[1][1] = "Carlos";
        playerInfo[1][2] = "C;Kotlin";
        playerInfo[1][3] = "Blue";

        String[][] abyssesAndTools = new String[2][3];

        abyssesAndTools[0][0] = "1";
        abyssesAndTools[0][1] = "5";
        abyssesAndTools[0][2] = "2";

        abyssesAndTools[1][0] = "0";
        abyssesAndTools[1][1] = "0";
        abyssesAndTools[1][2] = "5";

        GameManager game = new GameManager();

        game.createInitialBoard(playerInfo, 90, abyssesAndTools);
        System.out.println("Id jogador : " + game.getCurrentPlayerID());
        System.out.println(game.moveCurrentPlayer(2));
        System.out.println(game.reactToAbyssOrTool());
        System.out.println(game.moveCurrentPlayer(1));
        System.out.println(game.reactToAbyssOrTool());
        System.out.println(game.moveCurrentPlayer(2));
        System.out.println(game.reactToAbyssOrTool());

        System.out.println(game.getProgrammers().toString());
        System.out.println("-------------------------------------------------------------");

        System.out.println("Id jogador : " + game.getCurrentPlayerID());
        System.out.println(game.moveCurrentPlayer(3));
        System.out.println(game.reactToAbyssOrTool());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Id jogador : " + game.getCurrentPlayerID());
        System.out.println(game.moveCurrentPlayer(2));
        System.out.println(game.reactToAbyssOrTool());


        System.out.println("-------------------------------------------------------------");
        System.out.println("Id jogador (se for 1 não deve jogar) : " + game.getCurrentPlayerID());
        System.out.println(game.moveCurrentPlayer(3));
        System.out.println(game.reactToAbyssOrTool());
        ;

        System.out.println("-------------------------------------------------------------");
        System.out.println("Id jogador (deve jogar o id2) : " + game.getCurrentPlayerID());
        System.out.println(game.moveCurrentPlayer(3));
        System.out.println(game.reactToAbyssOrTool());

    }


}
