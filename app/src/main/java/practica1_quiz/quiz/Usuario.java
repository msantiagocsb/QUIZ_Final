package practica1_quiz.quiz;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Usuario {

    private String Nick;
    private String Contraseña;
    private boolean darkMode;
    private Bitmap fotoPerfil;
    //private boolean recienRegistrado;

    public boolean isDarkMode() {
        return darkMode;
    }

   /* public boolean getRecienRegistrado() {
        return recienRegistrado;
    }

    public void setRecienRegistrado(boolean recienRegistrado) {
        this.recienRegistrado = recienRegistrado;
    }*/

    public Usuario(String Nick, String Contraseña){
        this.Contraseña=Contraseña;
        this.Nick=Nick;
        darkMode=true;
        //recienRegistrado=true;
        this.fotoPerfil=null;


    }
    public Usuario(Usuario  u){
        this.Contraseña=u.getContraseña();
        this.Nick=u.getNick();
        darkMode=u.getDarkMode();
        this.fotoPerfil=u.getFotoPerfil();
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public boolean getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        this.Contraseña = contraseña;
    }

    public Bitmap getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Bitmap fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
