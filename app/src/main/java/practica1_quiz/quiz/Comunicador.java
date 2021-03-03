package practica1_quiz.quiz;

import java.util.ArrayList;

public class Comunicador {
    private static Object objeto = null;
    public static void setObjeto(Object newObjeto) { objeto = newObjeto;
    }
    public static Object getObjeto(Partida partida) {
        return objeto;
    } }


