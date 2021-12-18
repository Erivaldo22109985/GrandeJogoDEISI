package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Arrays;

public class Programmer implements Comparable{

    private int id;
    private String name;
    private ProgrammerColor color;
    private String[] linguagens;
    private int pos, posAnt, posNew;
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
        this.posAnt = 1;
        this.posNew = 1;
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
        if(this.activeTools.contains(t) == false) {
            this.activeTools.add(t);
        }
    }
    public int getPos(){

        return this.pos;
    }

    public int getPosAnt(){
        return this.posAnt;
    }

    public String getEstado(){

        return this.estado;
    }
    public void setEstado(String n){
        this.estado = n;
    }
    public void setPos(int pos, boolean updateAnt){

        if(updateAnt == true){
            this.posAnt = this.pos;
        }


        if(pos <= 0) {
            pos = 1;
        }

        this.pos = pos;

    }

    public void setNewPos(int pos){
        this.posNew = pos;
    }

    public int getNewPos(){
        return this.posNew;
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

    public String ferramentasToString(){
        String ret = "";

        if(this.activeTools.size() == 0) {
            return "No tools";
        }

        for(int i = 0; i < this.activeTools.size(); i++){
            String mm = "";
            switch(this.activeTools.get(i)){
                case inheritance:
                    mm = "Herança";
                    break;
                case functional:
                    mm = "Programação Funcional";
                    break;
                case unit_tests:
                    mm = "Testes unitários";
                    break;
                case catch0:
                    mm = "Tratamento de Excepções";
                    break;
                case IDE:
                    mm = "IDE";
                    break;
                case ajuda_professor:
                    mm = "Ajuda Do Professor";
                    break;
            }

            if(i==0){
                ret = mm;
            }else{
                ret = ';' + mm;
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
