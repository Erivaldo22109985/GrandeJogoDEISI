package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;

public class Programmer {

    private int id;
    private String name;
    private ProgrammerColor color;
    private String[] linguagens;
    private int pos;
    private String estado;

    public Programmer(int id, String name, String color, String linguagens){
        this.id = id;
        this.name = name;
        switch(color){

            case "Purple":
                this.color = ProgrammerColor.PURPLE;
                break;
            case "Green":
                this.color = ProgrammerColor.GREEN;
                break;
            case "Brown":
                this.color = ProgrammerColor.BROWN;
                break;
            case "Blue":
                this.color = ProgrammerColor.BLUE;
                break;
        }

        this.linguagens = linguagens.split(";");

        this.pos = 0;
        this.estado = "Em Jogo";
    }

    public int getId(){
        return this.id;
    }


    public int getPos(){

        return this.pos;
    }

    public void setPos(int pos){
        this.pos = pos;
    }

    public String getName(){
        return this.name;
    }

    public ProgrammerColor getColor(){
        return this.color;
    }
    private String linguagensToString(){
        String ret = "";
        for(String k : this.linguagens){

            if (ret.equals("")) {
                ret = k;
            } else {
                ret = ret + "; " + k;
            }
        }

        return ret;
    }
    public String toString(){
        return Integer.toString(this.id) + " | "
                + this.name + " | " + Integer.toString(this.pos) +
                " | " + this.linguagensToString() + " | " +
                this.estado;

    }


}
