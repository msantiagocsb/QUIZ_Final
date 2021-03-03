package practica1_quiz.quiz;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;

public class Musica {
        MediaPlayer musica;
        private int tipo;
        private boolean reproduciendo;



    Musica(int i, Context context){
            if(i==0){
                musica = MediaPlayer.create(context,R.raw.acierto);
                tipo=0;
                this.reproduciendo=false;
            }
            if(i==1){
                musica = MediaPlayer.create(context,R.raw.fallo);
                tipo=1;
                this.reproduciendo=false;
            }
            if(i==2){
                musica = MediaPlayer.create(context,R.raw.findetiempo);
                tipo=2;
                this.reproduciendo=false;
            }
            if(i==3){
                musica = MediaPlayer.create(context,R.raw.musicawalk);
                tipo=3;
                this.reproduciendo=false;

            }
            if(i==4){
                musica = MediaPlayer.create(context,R.raw.mejora);
                tipo=4;
                this.reproduciendo=false;

            }
            if(i==5){
                musica = MediaPlayer.create(context,R.raw.sinmejora);
                tipo=5;
                this.reproduciendo=false;

            }
        if(i==6){
            musica = MediaPlayer.create(context,R.raw.misionimposible);
            tipo=6;
            this.reproduciendo=false;

        }
           /* if(i==4){
                musica = MediaPlayer.create(context,R.raw.comer);
               // tipo=3;
            }
            if(i==5){
                musica = MediaPlayer.create(context,R.raw.perder_vida);
               // tipo=3;
            }*/
        }
        void reproducirLoop(){
            musica.start();
            musica.setLooping(true);
            this.reproduciendo=true;
        }
        void reproducir(Context context){

            if(this.tipo==0){
                musica = MediaPlayer.create(context,R.raw.acierto);
                tipo=0;
                this.reproduciendo=true;
            }
            if(this.tipo==1){
                musica = MediaPlayer.create(context,R.raw.fallo);
                tipo=1;
                this.reproduciendo=true;
            }
            if(this.tipo==2){
                musica = MediaPlayer.create(context,R.raw.findetiempo);
                tipo=2;
                this.reproduciendo=true;
            }
            if(this.tipo==3){
                musica = MediaPlayer.create(context,R.raw.musicawalk);
                tipo=3;
                this.reproduciendo=true;
            }
            if(this.tipo==4){
                musica = MediaPlayer.create(context,R.raw.mejora);
                tipo=4;
                this.reproduciendo=true;
            }
            if(this.tipo==5){

                musica = MediaPlayer.create(context,R.raw.sinmejora);
                tipo=5;
                this.reproduciendo=true;
            }
            musica.start();


        }
        void parar(){
            musica.stop();
            this.reproduciendo=false;
        }
       /* public int getMusica(){
            return tipo;
        }*/
       public boolean getReproduciendo() {
           return reproduciendo;
       }

    public void setReproduciendo(boolean reproduciendo) {
        this.reproduciendo = reproduciendo;
    }

    public void setVolume(float i, float i1) {
           musica.setVolume(i,i1);
    }
}


