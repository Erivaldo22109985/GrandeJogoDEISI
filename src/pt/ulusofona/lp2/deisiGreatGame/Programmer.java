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
    private ArrayList<Tools> activeTools;

    public Programmer(int id, String name, String color, String linguagens){
        this.id = id;
        this.name = name;
        this.activeTools = new ArrayList<Tools>();

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

    public ArrayList<Tools> getActiveTools(){

        return this.activeTools;
    }

    public boolean hasAtLeastOneTool(Tools t[]){

        for (int i= 0; i < t.length; i++){
            if(this.activeTools.contains(t[i]) == true){
                return true;
            }
        }
        return false;
    }

    public void setActiveTool(Tools t){
        if(this.activeTools.contains(t) == false)
            this.activeTools.add(t);
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

    private String ferramentasToString(){
        String ret = "";

        if(this.linguagens.length == 0)
            return "No tools";

        for(int i = 0; i < this.linguagens.length; i++){

            if(i==0){
                ret = this.linguagens[i];
            }else{
                ret = ';' + this.linguagens[i];
            }
        }

        return ret;

    }
    @Override
    public String toString(){
        return Integer.toString(this.id) + " | "
                + this.name + " | " + Integer.toString(this.pos) +
                " | " + this.ferramentasToString() +
                " | " + this.linguagensToString() + " | " +
                this.estado;

    }


    @Override
    public int compareTo(Object o) {
        return  this.id - ((Programmer) o).getId() ;
    }
}
