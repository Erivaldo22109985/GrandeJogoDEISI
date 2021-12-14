package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Arrays;

public class Programmer implements Comparable{

    private int id;
    private String name;
    private ProgrammerColor color;
    private String[] linguagens;
    private int pos, pos_ant;
    private String estado;
    private Tools activeTool;

    public Programmer(int id, String name, String color, String linguagens){
        this.id = id;
        this.name = name;
        this.activeTool = null;

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
        Arrays.sort(this.linguagens);

        this.pos = 1;
        this.pos_ant = 1;
        this.estado = "Em Jogo";
    }

    public int getId(){
        return this.id;
    }

    public Tools getActiveTool(){
        return this.activeTool;
    }

    public void setActiveTool(Tools t){
        this.activeTool = t;
    }
    public int getPos(){

        return this.pos;
    }

    public int getPos_ant(){
        return this.pos_ant;
    }

    public String getEstado(){
        return this.estado;
    }
    public void setEstado(String n){
        this.estado = n;
    }
    public void setPos(int pos){
        this.pos_ant = this.pos;

        if(pos <= 0)
            pos = 1;

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


    @Override
    public String toString(){
        return Integer.toString(this.id) + " | "
                + this.name + " | " + Integer.toString(this.pos) +
                " | " + this.linguagensToString() + " | " +
                this.estado;

    }


    @Override
    public int compareTo(Object o) {
        return  this.id - ((Programmer) o).getId() ;
    }
}
