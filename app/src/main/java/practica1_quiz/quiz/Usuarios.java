package practica1_quiz.quiz;

import java.util.ArrayList;

public class Usuarios {

    private static ArrayList<Usuario> usuarios=new ArrayList<>();





    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(ArrayList<Usuario> usuarios) {
        Usuarios.usuarios = usuarios;
    }

    public static void añadirUsuarios(Usuario usuario){
            if(usuario!=null) {
                usuarios.add(usuario);
            }
        }






}