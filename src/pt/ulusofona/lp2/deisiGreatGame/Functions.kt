package pt.ulusofona.lp2.deisiGreatGame

import sun.security.util.Debug.println


enum class CommandType{
    GET,
    POST
}


//dwdw
fun router():  (CommandType) -> ((GameManager, List<String>) -> String?)? {
    return ::lercomando;
}

fun lercomando(type: CommandType) : ((GameManager, List<String>) -> String?)? {
    when(type){
        CommandType.GET -> return ::get;
    }

    return null;
}


fun get(g: GameManager, l: List<String>): String?{
    var arg = l.get(0);
    when(arg){
        "PLAYER" -> return get_player(g,l);
        "PLAYERS_BY_LANGUAGE" -> get_player_by_language(g,l);
    }
    return null;
}
fun get_player_by_language(g: GameManager, l: List<String>): String?{
    val x = l.get(1);
    val k = "dwq";
    var ret:String = "";

    k.split("," , ignorecase= true, limit=0);

    g.getProgrammers(true).forEach {

    }

    return ret;
}
fun get_player(g: GameManager, l: List<String>): String?{
    var ret:String = "Inexistent player";
    g.getProgrammers(true).forEach {
        if(it.firstName == l.get(1)){
            ret = it.toString();
            return@forEach
        }
    }

    return ret;
}
