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
    return "OK!";
}
