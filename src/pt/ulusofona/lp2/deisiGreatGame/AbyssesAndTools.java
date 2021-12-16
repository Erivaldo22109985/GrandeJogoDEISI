package pt.ulusofona.lp2.deisiGreatGame;

public class AbyssesAndTools {

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

    public boolean init(String [][] abyssesAndTools, int boardsize){

        if(abyssesAndTools == null) {
            return true;
        }

        at = new int[abyssesAndTools.length][3];

        //validar abysses and Tools
        for(int i=0;i<abyssesAndTools.length;i++){

            if(abyssesAndTools[i].length != 3){
                return false;
            }

            try{
                at[i][0] = Integer.parseInt(abyssesAndTools[i][0]); // abismo, ferramenta
                at[i][1] = Integer.parseInt(abyssesAndTools[i][1]); // tipo
                at[i][2] = Integer.parseInt(abyssesAndTools[i][2]); // Posicao
            }catch(Exception e){
                return false;
            }

            //validacao do tipo
            if (!( at[i][0] == 0 || at[i][0] == 1)
            ) {
                return false;
            }

            //validacao das ferramentas e tipos
            switch(at[i][0]){
                case 0:
                    if(at[i][1] < 0 || at[i][1] > Abysses.values().length){
                        return false;
                    }
                    break;
                case 1:
                    if(at[i][1] < 0 || at[i][1] > Tools.values().length){
                        return false;
                    }
                    break;
            }

            //Verificar se esta dentro dos limites do board size
            if(at[i][2] < 0 && at[i][2] >= boardsize - 1){
                return false;
            }

        }

        return true;
    }
}
