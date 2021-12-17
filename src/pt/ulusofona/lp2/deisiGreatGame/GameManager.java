package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {

    private ArrayList<Programmer> jogadores;
    private int boardSize;
    private int currentPlayer;
    private int nturnos;
    private AbyssesAndTools at;
    private String atMsg = null;

    public GameManager(){
    }

    public boolean createInitialBoard(String[][] playerInfo,
                                      int boardSize){
        return this.createInitialBoard(playerInfo,boardSize,null);
    }
    public boolean createInitialBoard(String[][] playerInfo,
                                      int boardSize,
                                      String[][] abyssesAndTools
                                      ) {
        //Verificacao de numero jogadores
        if (playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }


        //Verificacao do tabuleiro tamanho
        if (boardSize < playerInfo.length * 2) {
            return false;
        }

        this.jogadores = new ArrayList<Programmer>();
        this.boardSize = boardSize;
        this.currentPlayer = 0;
        this.nturnos = 1;

        for (int i = 0; i < playerInfo.length; i++) {
            int id_jogador = Integer.parseInt(playerInfo[i][0]);
            String nome = playerInfo[i][1];
            String lista_linguagens = playerInfo[i][2];
            String cor = playerInfo[i][3];

            //id numero inteiro positivo
            if (id_jogador < 0) {
                return false;
            }

            for (int j = 0; j < playerInfo.length; j++) {

                //ids programadores repetidos
                if (j != i && Integer.parseInt(playerInfo[j][0]) == id_jogador) {
                    return false;
                }

                //Nao podem haver cores iguais
                if (j != i && playerInfo[j][3] == cor) {
                    return false;
                }
            }

            //os nomes dos porgramadaores
            if (nome == null || nome == "") {
                return false;
            }

            //cor dos jogadores
            if (cor != "Purple" && cor != "Green" && cor != "Brown" && cor != "Blue") {
                return false;
            }

            //Adicionar jogador
            this.jogadores.add(new Programmer(id_jogador, nome, cor, lista_linguagens));
        }


        this.at = new AbyssesAndTools();
        if (this.at.init(abyssesAndTools, boardSize) == false){
            return false;
    }
        Collections.sort(this.jogadores);

        return true;
    }


    public String getImagePng(int position){
        if(position >= this.boardSize || position < 0){
            return null;
        }

        if(position == this.boardSize - 1){
            return "glory.png";
        }

        return this.at.getImagePng(position);
    }

    public String getTitle(int position){
        return this.at.getTitle(position);
    }

    public List<Programmer> getProgrammers(){
        return this.jogadores;
    }

    public String getProgrammersInfo(){
        String ret = "";
        for(Programmer x: this.getProgrammers()){
            ret += x.getName() + " : ";

            if(x.getActiveTools().size() == 0){
                ret += "No tools";
            }else{
                int i = 0;
                for(Tools k: x.getActiveTools()){
                    if(i == 0){
                        ret += k.name();
                    }else{
                        ret += ";" + k.name();
                    }
                    i++;
                }
            }

            ret += " | ";
        }

        return ret;
    }
    public List<Programmer> getProgrammers(boolean includeDefeated){
        ArrayList<Programmer> ret = new ArrayList<Programmer>();

        if(includeDefeated == true) {
            return this.getProgrammers();
        }
        for(Programmer x : this.jogadores){
            if(x.getEstado() != "Derrotado" && x.getEstado() != "BSOD") {
                ret.add(x);
            }
        }

        return ret;
    }

    public List<Programmer> getProgrammers(int position){
        ArrayList<Programmer> k = new ArrayList<Programmer>();

        for(Programmer x : this.jogadores){
            if(x.getPos() == position) {
                k.add(x);
            }
        }

        return k;
    }

    public int getCurrentPlayerID(){
        return this.jogadores.get(this.currentPlayer).getId();
    }


    public boolean moveCurrentPlayer(int nrPositions){
        int prox_casa = 0;

        if(nrPositions < 1 || nrPositions > 6){
            return false;
        }

        Programmer x = this.jogadores.get(this.currentPlayer);

        if(x.getEstado() == "Blocked" || x.getEstado() == "BSOD"){
            this.atMsg = "Bloqueado!!";
            return false;
        }


        if(x.getPos()+nrPositions < this.boardSize) {
            prox_casa = x.getPos() + nrPositions;
        } else {
            prox_casa = this.boardSize - (nrPositions - (this.boardSize - x.getPos()) );
        }

        x.setNewPos(prox_casa);

        return true;
    }

    public String reactToAbyssOrTool(){
        Programmer x = this.jogadores.get(this.currentPlayer);
        int casa_atual = x.getPos();
        int prox_casa = x.getNewPos();

        this.atMsg = null;

        if(this.at.isAbysse(prox_casa) == true){
             prox_casa = this.playAbysse(prox_casa,x);
        }else if(this.at.isTool(prox_casa) == true){
            x.setActiveTool(Tools.values()[at.getATPosition(prox_casa)[1]]);
            this.atMsg = "Apanhada ferramenta: " + Tools.values()[at.getATPosition(prox_casa)[1]];
        }
        x.setPos(prox_casa);

        this.currentPlayer++;this.nturnos++;
        if(this.currentPlayer>=this.jogadores.size()){
            for(int i=0; i<this.jogadores.size();i++){
                if(this.jogadores.get(i).getEstado() != "BSOD"){
                    this.currentPlayer = i;
                }
            }
            this.currentPlayer=0;
        }

        return this.atMsg;
    }

    private int countPlayersSamePlace(int pos){
        int n = 0;
        for(Programmer x: this.getProgrammers()){
            if(x.getPos() == pos) {
                n++;
            }
        }

        return n;
    }

    private void updatePlayersState(String estado, int pos){
        int n = 0;
        for(Programmer x: this.getProgrammers()){
            if(x.getPos() == pos) {
                x.setEstado(estado);
            }
        }

    }

    private void updatePlayersPlace(int pos, int n_casas){

        for(Programmer x: this.getProgrammers()){
            if(x.getPos() == pos)
            {
                x.setPos(pos + n_casas);
            }
        }

    }

    public int playAbysse(int new_pos, Programmer x){
        int at_pos = x.getPos();
        int ant_pos = x.getPosAnt();
        int numDado = new_pos - at_pos;
        int count_players_same_place = this.countPlayersSamePlace(at_pos);
        int count_players_new_place = this.countPlayersSamePlace(new_pos);

        //no caso de nao estar em jogo
        if(at_pos <= 0 || x.getEstado().equals("Blocked") == true) {
            return at_pos;
        }

        //posicao nova
        switch(Abysses.values()[at.getATPosition(new_pos)[1]]){
            case syntax:

                this.atMsg = "Abismo de Sintaxe. Anda uma posicao para traz!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.ajuda_professor,
                                Tools.functional
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta!";
                        return new_pos;
                }

                return new_pos - 1;
            case logic:
                this.atMsg = "Abismo de logica. Anda " + numDado/2 + " para traz!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.ajuda_professor
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta!";
                        return new_pos;
                }

                return new_pos - numDado/2;
            case exception:
                this.atMsg = "Abismo de excecao. Anda para traz 2 posicoes!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.ajuda_professor,
                                Tools.IDE
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }

                return new_pos - 2;
            case file_not_found_exception:
                this.atMsg = "Abismo de File Not Found. Anda para traz 3 posicoes!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.ajuda_professor
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }


                return new_pos - 3;
            case crash:
                this.atMsg = "Abismo de Crash. Volta para casa inicial!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.functional
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }

                return 1;
            case duplicated_code:
                this.atMsg = "Abismo de Duplicated code. Volta para casa de onde jogou!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.IDE
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }

                return at_pos;
            case secondary_effects:
                this.atMsg = "Abismo de Efeitos Secundarios. Volta para a casa de duas jogadas atras!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.unit_tests
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }

                return ant_pos;
            case bsod:

                this.atMsg = "Abismo de BSOD. Perdeu o Jogo!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.catch0
                        }) == true){
                    this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }

                x.setEstado("BSOD");

                return 0;
            case infinite_loop:
                this.atMsg = "Abismo de Loop infinito. Bloqueado ate outro programador jogar!";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.inheritance,
                                Tools.unit_tests
                        }) == true){
                        this.atMsg = "\nSalvo por ferramenta";
                        return new_pos;
                }

                if(count_players_new_place >= 1){
                    this.updatePlayersState("Em Jogo", new_pos);
                }

                x.setEstado("Blocked");

                return new_pos;
            case core_dumped:
                this.atMsg = "Abismo Core Dumped.";
                if(x.hasAtLeastOneTool(
                        new Tools[]{
                                Tools.functional
                        }) == true){
                        this.atMsg += "\nSalvo por ferramenta";
                        return new_pos;
                }

                if(count_players_new_place >= 1){
                    this.atMsg += "\nTodos os programadores nesta casa perdem 3 casas!";
                    this.updatePlayersPlace(new_pos,-3);
                    return new_pos - 3;
                }else{
                    this.atMsg += "\nNada acontece";
                }

                return new_pos;


        }

        return new_pos;
    }

    public boolean gameIsOver(){
        if(this.getProgrammers(this.boardSize).size() >= 1){
            for(Programmer k : this.getProgrammers()){
                if (k.getPos() != this.boardSize){
                    k.setEstado("Derrotado");
                }
                else{
                    k.setEstado("Ganhou");
                }
            }
            return true;
        }

        return false;
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> ret = new ArrayList<String>();

        if(this.gameIsOver() == false){
            return ret;
        }

        ret.add("O GRANDE JOGO DO DEISI");
        ret.add("");
        ret.add("NR. DE TURNOS");
        ret.add(Integer.toString(this.nturnos));
        ret.add("");
        ret.add("VENCEDOR");
        ret.add(this.getProgrammers(this.boardSize).get(0).getName());
        ret.add("");
        ret.add("RESTANTES");

        int pos = 2;
        for(int i = this.boardSize-1; i>= 0; i--){
            for(Programmer x: this.getProgrammers(i)){
                ret.add(x.getName() + " " + Integer.toString(pos));
                pos++;
            }
        }


        return ret;
    }

    public JPanel getAuthorsPanel(){
        return null;
    }

}
