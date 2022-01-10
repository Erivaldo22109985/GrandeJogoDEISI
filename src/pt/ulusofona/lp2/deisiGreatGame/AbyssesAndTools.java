package pt.ulusofona.lp2.deisiGreatGame;

import java.io.Serializable;

public class AbyssesAndTools implements Serializable {

    int at[][];

    public AbyssesAndTools(){
        this.at = null;
    }



    public String getImagePng(int pos){
        int[] x = this.getATPosition(pos);
        String name = null;

        if(x != null){
            switch(x[0]){
                case 0:
                    name = Abysses.values()[x[1]].toString().replace("_","-").replace("0","");
                    break;
                case 1:
                    name = Tools.values()[x[1]].toString().replace("_","-").replace("0","");
                    break;
            }

            return name != null? name + ".png": null;
        }else{
            return null;
        }
    }

    public String getTitle(int position){
        int at[] = this.getATPosition(position);

        if(at == null) {
            return null;
        }
        switch(at[0]){
            case 0:
                String titles[] = {
                    "Erro de sintaxe",
                    "Erro de lógica",
                    "Exception",
                    "File Not Found Exception",
                    "Crash (aka Rebentanço)",
                    "Duplicated Code",
                    "Efeitos secundários",
                    "Blue Screen of Death",
                    "Ciclo infinito",
                    "Segmentation Fault"
                };

                return titles[at[1]];

            case 1:
                String titles2[] = {
                        "Herança",
                        "Programação Funcional",
                        "Testes unitários",
                        "Tratamento de Excepções",
                        "IDE",
                        "Ajuda Do Professor"
                };

                return titles2[at[1]];

        }

        return null;
    }

    private boolean isSomething(int pos, int type){
        int [] x;
        if((x = this.getATPosition(pos)) == null || x[0] != type) {
            return false;
        }
        return true;
    }

    public Abysses getAbysse(int pos){
        return Abysses.values()[this.getATPosition(pos)[1]];
    }

    public boolean isAbysse(int pos){
        return this.isSomething(pos,0);
    }

    public boolean isTool(int pos){
        return this.isSomething(pos,1);
    }

    public int[] getATPosition(int pos){

        if(this.at == null) {
            return null;
        }
        for(int i = 0; i<this.at.length; i++){
            if(at[i][2] == pos) {
                return at[i];
            }
        }

        return null;
    }

    public void init(String [][] abyssesAndTools, int boardsize) throws InvalidInitialBoardException
    {

        if(abyssesAndTools == null) {
            throw new InvalidInitialBoardException("Abysses and tools null");
        }

        at = new int[abyssesAndTools.length][3];

        //validar abysses and Tools
        for(int i=0;i<abyssesAndTools.length;i++){

            if(abyssesAndTools[i].length != 3){
                throw new InvalidInitialBoardException("Abysses and tools length");
            }

            try{
                at[i][0] = Integer.parseInt(abyssesAndTools[i][0]); // abismo, ferramenta
                at[i][1] = Integer.parseInt(abyssesAndTools[i][1]); // tipo
                at[i][2] = Integer.parseInt(abyssesAndTools[i][2]); // Posicao
            }catch(Exception e){
                throw new InvalidInitialBoardException("Abysses and tools parsing");
            }

            //validacao do tipo
            if (!( at[i][0] == 0 || at[i][0] == 1)
            ) {
                throw new InvalidInitialBoardException("Abysses and tools type");
            }

            //validacao das ferramentas e tipos
            switch(at[i][0]){
                case 0:
                    if(at[i][1] < 0 || at[i][1] >= Abysses.values().length){

                        throw new InvalidInitialBoardException(
                                "Abyss type out of boundaries","Abyss",
                                Integer.toString(at[i][1])
                        );
                    }
                    break;
                case 1:
                    if(at[i][1] < 0 || at[i][1] >= Tools.values().length){
                        throw new InvalidInitialBoardException(
                                "Tools type out of boundaries",
                                "Tool",
                                Integer.toString(at[i][1])
                        );
                    }
                    break;
            }

            //Verificar se esta dentro dos limites do board size
            if(at[i][2] < 0 && at[i][2] >= boardsize - 1){
                throw new InvalidInitialBoardException(
                        "Tools type out of boundaries",
                        at[i][0],
                        Integer.toString(at[i][1])
                );
            }

        }

    }

}
