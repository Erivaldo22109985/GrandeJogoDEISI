package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    private ArrayList<Programmer> jogadores;
    private int boardSize;
    private int currentPlayer;
    private int nturnos;

    public GameManager(){

    }

    public boolean createInitialBoard(String[][] playerInfo, int boardSize){
        //Verificacao de numero jogadores
        if (playerInfo.length < 2 || playerInfo.length > 4){
            return false;
        }


        //Verificacao do tabuleiro tamanho
        if (boardSize < playerInfo.length*2) {
            return false;
        }

        this.jogadores = new ArrayList<Programmer>();
        this.boardSize = boardSize;
        this.currentPlayer = 0;
        this.nturnos = 1;

        for(int i=0;i<playerInfo.length;i++){
            int id_jogador = Integer.parseInt(playerInfo[i][0]);
            String nome = playerInfo[i][1];
            String lista_linguagens = playerInfo[i][2];
            String cor = playerInfo[i][3];

            //id numero inteiro positivo
            if (id_jogador < 0) {
                return false;
            }

            for(int j=0;j<playerInfo.length;j++){

                //ids programadores repetidos
                if (j != i && Integer.parseInt(playerInfo[j][0]) == id_jogador){
                    return false;
                }

                //Nao podem haver cores iguais
                if (j != i && playerInfo[j][3] == cor) {
                    return false;
                }
            }

            //os nomes dos porgramadaores
            if(nome == null||nome == "") {
                return false;
            }

            //cor dos jogadores
            if (cor != "Purple" && cor != "Green" && cor != "Brown" && cor != "Blue") {
                return false;
            }

            //Adicionar jogador
            this.jogadores.add( new Programmer(id_jogador,nome,cor,lista_linguagens) );
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

        return null;//Integer.toString(position) + ".png";
    }

    public ArrayList<Programmer> getProgrammers(){
        return this.jogadores;
    }

    public ArrayList<Programmer> getProgrammers(int position){
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

        if(nrPositions < 1 || nrPositions > 6){
            return false;
        }

        Programmer x = this.jogadores.get(this.currentPlayer);

        if(x.getPos()+nrPositions < this.boardSize) {
            x.setPos(x.getPos() + nrPositions);
        } else {
            x.setPos(this.boardSize - (nrPositions - (this.boardSize - x.getPos()) ));
        }

        this.currentPlayer++;this.nturnos++;
        if(this.currentPlayer>=this.jogadores.size()){
            this.currentPlayer=0;
            
        }


        return true;
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
