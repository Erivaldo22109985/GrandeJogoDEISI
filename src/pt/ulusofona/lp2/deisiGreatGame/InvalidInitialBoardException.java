package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception{

    private String message;
    private String tipo = "";
    private String id = "";
    InvalidInitialBoardException(String message) {
        this.message = message;
    }

    InvalidInitialBoardException(String message,String tipo, String id) {
        this.message = message;
        this.tipo = tipo;
        this.id = id;
    }

    InvalidInitialBoardException(String message,int ntipo, String id) {
        this.message = message;
        this.tipo = ntipo == 0 ? "Abyss" : "Tool";
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInvalidAbyss(){
        return this.tipo == "Abyss";
    }

    public boolean isInvalidTool(){
        return this.tipo == "Tool";
    }

    public String getTypeId(){
        return this.id;

    }
}
