package pt.ulusofona.lp2.deisiGreatGame

import java.util.*
import java.util.regex.Pattern

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
        CommandType.POST -> return ::post;
    }

    return null;
}

fun post(g: GameManager, l: List<String>): String?{

    if (l.size < 1) return null;

    var arg = l.get(0);
    when(arg){
        "MOVE" -> return post_moveplayer(g,l);
        "ABYSS" -> return post_addAbyss(g,l);
    }
    return null;
}

fun get(g: GameManager, l: List<String>): String?{
    if (l.size < 1) return null;

    var arg = l.get(0);
    when(arg){
        "PLAYER" -> return get_player(g,l);
        "PLAYERS_BY_LANGUAGE" -> return get_player_by_language(g,l);
        "POLYGLOTS" -> return get_polyglots(g,l);
        "MOST_USED_POSITIONS" -> return get_mostUsedPositions(g,l);
        "MOST_USED_ABYSSES" -> return get_mostUsedAbysses(g,l);
    }
    return null;
}



fun polyglots(p: MutableList<Programmer>): String{
    var res:String = "";
    when(p.size){
        0 -> return "";
        1 -> return p.get(0).name + ":" + p.get(0).linguagens.size + "\n";
        else -> {
            var tmp:Programmer = p.get(0);
            p.removeAt(0);
            if(tmp.linguagens.size > p.get(0).linguagens.size){
                res = polyglots(p)  + tmp.name + ":" + tmp.linguagens.size + "\n";
            }else{
                res = tmp.name + ":" + tmp.linguagens.size + "\n" + polyglots(p);
            }

            p.add(tmp);
        }
    }

    return res;
}

fun post_moveplayer(g: GameManager, l: List<String>): String?{
    var ret:String? = "FAIL";
    if(l.size != 2) return null;

    var k:Int = Integer.parseInt(l.get(1));

    if( g.moveCurrentPlayer(k) == true ){
        ret = g.reactToAbyssOrTool();
        if(ret == null){
            ret = "OK";
        }
    }


    return ret;
}

fun post_addAbyss(g: GameManager, l: List<String>): String?{
    var ret:String? = "FAIL";
    if(l.size != 3) return null;

    var abyssTypeID:Int = Integer.parseInt(l.get(1));
    var position:Int = Integer.parseInt(l.get(2));

    if(g.at.addAbyss(position,Abysses.values()[abyssTypeID]) == true) {
        ret = "OK";
    }
    else{
        ret = "Position is occupied";
    }

    return ret;
}

fun get_mostUsedAbysses(g: GameManager, l: List<String>): String?{

    if(l.size != 2) return null;

    var k:Int = Integer.parseInt(l.get(1));
    var ret:String = "";

    g.abyssesCountHistory.forEach{
        if(k==0) return@forEach

        ret += it.key.toString() + ":" + it.value.toString() + "\n";
        k -= 1;
    }


    return ret;
}

fun get_mostUsedPositions(g: GameManager, l: List<String>): String?{
    if(l.size != 2) return null;

    var k:Int = Integer.parseInt(l.get(1));
    var ret:String = "";

    g.boardCountHistory.forEach{
        if(k==0) return@forEach

        ret += it.key.toString() + ":" + it.value.toString() + "\n";
        k -= 1;
    }


    return ret;
}

fun get_polyglots(g: GameManager, l: List<String>): String?{
    var ret:String? = null;
    val ob:MutableList<Programmer> = ArrayList<Programmer>();

    g.getProgrammers(true).forEach {
        val p:Programmer = it;
        if(p.linguagens.size > 1){
            ob.add(p);
        }
    }

    ret = polyglots(ob);
    ret = g.replaceEmptyLines(ret);

    return ret;
}

fun get_player_by_language(g: GameManager, l: List<String>): String?{
    if(l.size != 2) return null;

    val x = l.get(1);
    var ret:String? = null;

    g.getProgrammers(true).forEach {
        val p:Programmer = it;
        Pattern.compile(";").split(x).iterator().forEachRemaining {
            val c:String = it;
            p.linguagens.iterator().forEachRemaining {
                if(c == it ){
                    if(ret == null){
                        ret = p.name;
                    }else{
                        ret = ret + "," + p.name;
                    }
                }
            }
        }
    }

    return ret;
}
fun get_player(g: GameManager, l: List<String>): String?{
    if(l.size != 2) return null;

    var ret:String = "Inexistent player";
    g.getProgrammers(true).forEach {
        if(it.firstName == l.get(1)){
            ret = it.toString();
            return@forEach
        }
    }

    return ret;
}
