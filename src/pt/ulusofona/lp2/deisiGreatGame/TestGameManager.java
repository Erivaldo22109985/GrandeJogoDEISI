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

    private String[][] abyssesTools = new String[2][3];
    @org.junit.Before
    public void setUp() throws Exception {
        this.gm = new GameManager();

        this.list_player_1[0] = createPlayer("10", "Player1", "D;Common Lisp;Clojure", "Purple");

        this.list_player_1[1] = createPlayer("2", "Player2", "c2;c++2;java3", "Brown");
        this.list_player_1[2] = createPlayer("3", "Player3", "c3;c++3;java3", "Green");

        int i = 0;

        //Ferramenta
        this.abyssesTools[i][0] = "1";
        this.abyssesTools[i][1] = Integer.toString(Tools.functional.ordinal());
        this.abyssesTools[i][2] = "2";

        i++;

        //abismo
        this.abyssesTools[i][0] = "0";
        this.abyssesTools[i][1] = Integer.toString(Abysses.syntax.ordinal());
        this.abyssesTools[i][2] = "3";




    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void createInitialBoard() {
        assertTrue(this.gm.createInitialBoard(this.list_player_1,16, this.abyssesTools));


    }

    @org.junit.Test
    public void moveCurrentPlayer() {
        assertTrue(this.gm.createInitialBoard(this.list_player_1,13));

        assertTrue(this.gm.moveCurrentPlayer(6));
        assertTrue(this.gm.reactToAbyssOrTool() == null);

        assertTrue(this.gm.moveCurrentPlayer(1));
        assertTrue(this.gm.reactToAbyssOrTool() == null);

        assertFalse(this.gm.gameIsOver());

        assertTrue(this.gm.moveCurrentPlayer(1));
        assertTrue(this.gm.reactToAbyssOrTool() == null);

        assertTrue(this.gm.moveCurrentPlayer(6));
        assertTrue(this.gm.reactToAbyssOrTool() == null);

        assertTrue(this.gm.gameIsOver());

    }

    public void helper_testAbismo(Abysses a, Tools t, boolean should_pass){

        this.abyssesTools[1][1] = Integer.toString(a.ordinal());
        this.abyssesTools[0][1] = Integer.toString(t.ordinal());

        assertTrue(this.gm.createInitialBoard(this.list_player_1,16, this.abyssesTools));

        assertTrue(this.gm.moveCurrentPlayer(2));
        assertTrue(this.gm.reactToAbyssOrTool() != null);

        if(a != Abysses.bsod && a != Abysses.infinite_loop && a != Abysses.core_dumped){
            assertTrue(this.gm.getProgrammers(3).size() == 0);
        }
        assertTrue(this.gm.createInitialBoard(this.list_player_1,16, this.abyssesTools));

        assertTrue(this.gm.moveCurrentPlayer(1));
        assertTrue(this.gm.reactToAbyssOrTool() != null);

        assertTrue(this.gm.moveCurrentPlayer(1));
        assertTrue(this.gm.reactToAbyssOrTool() != null);

        assertTrue(this.gm.moveCurrentPlayer(1));
        assertTrue(this.gm.reactToAbyssOrTool() != null);

        assertTrue(this.gm.moveCurrentPlayer(1));
        assertTrue(this.gm.reactToAbyssOrTool() != null);


        assertEquals(this.gm.getProgrammers(3).size() != 0, should_pass);


    }

    @org.junit.Test
    public void testAbismo_sintax(){

        helper_testAbismo(Abysses.syntax,Tools.inheritance,false);
        helper_testAbismo(Abysses.syntax,Tools.functional,false);
        helper_testAbismo(Abysses.syntax,Tools.unit_tests,false);
        helper_testAbismo(Abysses.syntax,Tools.catch0,false);
        helper_testAbismo(Abysses.syntax,Tools.IDE,true);
        helper_testAbismo(Abysses.syntax,Tools.ajuda_professor,true);

    }

    @org.junit.Test
    public void testAbismo_logic(){
        Abysses i = Abysses.logic;
        helper_testAbismo(i,Tools.inheritance,false);
        helper_testAbismo(i,Tools.functional,false);
        helper_testAbismo(i,Tools.unit_tests,true);
        helper_testAbismo(i,Tools.catch0,false);
        helper_testAbismo(i,Tools.IDE,false);
        helper_testAbismo(i,Tools.ajuda_professor,true);

    }

    @org.junit.Test
    public void testAbismo_exception(){
        Abysses i = Abysses.exception;
        helper_testAbismo(i,Tools.inheritance,false);
        helper_testAbismo(i,Tools.functional,false);
        helper_testAbismo(i,Tools.unit_tests,false);
        helper_testAbismo(i,Tools.catch0,true);
        helper_testAbismo(i,Tools.IDE,false);
        helper_testAbismo(i,Tools.ajuda_professor,true);

    }

    @org.junit.Test
    public void testAbismo_fnf(){
        Abysses i = Abysses.file_not_found_exception;
        helper_testAbismo(i,Tools.inheritance,false);
        helper_testAbismo(i,Tools.functional,false);
        helper_testAbismo(i,Tools.unit_tests,false);
        helper_testAbismo(i,Tools.catch0,true);
        helper_testAbismo(i,Tools.IDE,false);
        helper_testAbismo(i,Tools.ajuda_professor,true);

    }

    @org.junit.Test
    public void testAbismo_crash(){
        Abysses i = Abysses.crash;
        helper_testAbismo(i,Tools.inheritance,false);
        helper_testAbismo(i,Tools.functional,true);
        helper_testAbismo(i,Tools.unit_tests,false);
        helper_testAbismo(i,Tools.catch0,false);
        helper_testAbismo(i,Tools.IDE,false);
        helper_testAbismo(i,Tools.ajuda_professor,false);

    }

    @org.junit.Test
    public void testAbismo_duplicated(){
        Abysses i = Abysses.duplicated_code;
        helper_testAbismo(i,Tools.inheritance,true);
        helper_testAbismo(i,Tools.functional,false);
        helper_testAbismo(i,Tools.unit_tests,false);
        helper_testAbismo(i,Tools.catch0,false);
        helper_testAbismo(i,Tools.IDE,false);
        helper_testAbismo(i,Tools.ajuda_professor,false);

    }

    @org.junit.Test
    public void testAbismo_secondary(){
        Abysses i = Abysses.secondary_effects;
        helper_testAbismo(i,Tools.inheritance,false);
        helper_testAbismo(i,Tools.functional,true);
        helper_testAbismo(i,Tools.unit_tests,false);
        helper_testAbismo(i,Tools.catch0,false);
        helper_testAbismo(i,Tools.IDE,false);
        helper_testAbismo(i,Tools.ajuda_professor,false);
    }

    @org.junit.Test
    public void testAbismo_bsod(){
        Abysses i = Abysses.bsod;
        helper_testAbismo(i,Tools.inheritance,true);
        helper_testAbismo(i,Tools.functional,true);
        helper_testAbismo(i,Tools.unit_tests,true);
        helper_testAbismo(i,Tools.catch0,true);
        helper_testAbismo(i,Tools.IDE,true);
        helper_testAbismo(i,Tools.ajuda_professor,true);

        }
    @org.junit.Test
    public void testAbismo_infinite(){
        Abysses i = Abysses.infinite_loop;
        helper_testAbismo(i,Tools.inheritance,true);
        helper_testAbismo(i,Tools.functional,true);
        helper_testAbismo(i,Tools.unit_tests,true);
        helper_testAbismo(i,Tools.catch0,true);
        helper_testAbismo(i,Tools.IDE,true);
        helper_testAbismo(i,Tools.ajuda_professor,true);

    }

    @org.junit.Test
    public void testAbismo_dump(){
        Abysses i = Abysses.core_dumped;
        helper_testAbismo(i,Tools.inheritance,true);
        helper_testAbismo(i,Tools.functional,true);
        helper_testAbismo(i,Tools.unit_tests,true);
        helper_testAbismo(i,Tools.catch0,true);
        helper_testAbismo(i,Tools.IDE,true);
        helper_testAbismo(i,Tools.ajuda_professor,true);

    }




}
