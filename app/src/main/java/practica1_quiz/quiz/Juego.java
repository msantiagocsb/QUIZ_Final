package practica1_quiz.quiz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Juego extends AppCompatActivity {
    private ArrayList<Pregunta> Preguntas_preparadas=new ArrayList<>();
    int puntuacion=0;
    private Partida partida;
    private CountDownTimer countDownTimer;
    private TextView juegoNuevo,empieza,tiempo;
    private ImageView fondo;
    private int maxVolume=50;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        partida = (Partida) Comunicador.getObjeto(partida);
        partida.setNumero_pregunta(0);
        partida.setPuntuacion(0);
        partida.setAcertadas(0);
        partida.setFalladas(0);
        ponerPreguntasCero();
        if(partida.getUsuario().getDarkMode()==false){
            cambiarColor();
        }

        if(partida.getMusicaPreguntas()==true) {


            if (partida.getMusicaDePartida().getReproduciendo() == true) {
                float log1=(float)(Math.log(maxVolume-45)/Math.log(maxVolume));
                    partida.getMusicaDePartida().setVolume((log1),(log1));
            }


        }else{
            partida.getMusicaDePartida().parar();
        }

        for(int i=0;i<partida.getPreguntas_partida().size();i++){
        partida.getPreguntas_partida().get(i).setPreguntada(false);}//pone de nuevo todas la preguntas sin repetir para que puedan volver a salir cualquiera



        final TextView textView = (TextView)findViewById(R.id.tiempo);

         countDownTimer = new CountDownTimer(4000, 100) {
            public void onTick(long millisUntilFinished) {
                textView.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                //for(int j=0;j<5;j++){//hay que darle una vuelta conseguir que haga x preguntas una vez terminada cada una
                    int i = (int) (Math.random() * 5 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                    switch (i) {
                        case 1:
                            if(partida.getMusicaPreguntas()==false){
                            partida.getMusicaDePartida().parar();}

                            Comunicador.setObjeto(partida);



                            Intent intent = new Intent(Juego.this, Juego_Basico.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 2:
                            if(partida.getMusicaPreguntas()==false){
                                partida.getMusicaDePartida().parar();}
                            Comunicador.setObjeto(partida);
                            Intent intent2 = new Intent(Juego.this, Juego_Radio.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case 3:
                            if(partida.getMusicaPreguntas()==false){
                                partida.getMusicaDePartida().parar();}
                            Comunicador.setObjeto(partida);
                            Intent intent3 = new Intent(Juego.this, Juego_Imagen.class);
                            startActivity(intent3);
                            finish();
                            break;
                        case 4:
                            if(partida.getMusicaPreguntas()==false){
                                partida.getMusicaDePartida().parar();}
                            Intent intent4 = new Intent(Juego.this,Juego_Basico.class);
                            startActivity(intent4);
                            finish();
                            break;
                        case 5:
                            if(partida.getMusicaPreguntas()==false){
                                partida.getMusicaDePartida().parar();}
                            Intent intent5 = new Intent(Juego.this, Juego_Imagenes.class);
                            startActivity(intent5);
                            finish();
                            break;
                        default:
                            Toast.makeText(Juego.this, "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                            break;
                    }
                //}
            }
        }.start();

    }

    public void onBackPressed() {//si presionas atras vuelve al menú principal
        partida.getMusicaDePartida().parar();
        countDownTimer.cancel();//para el tiempo de la activity
        Intent intent = new Intent(Juego.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
    public void cambiarColor(){
        fondo= (ImageView) findViewById(R.id.fondo);
        juegoNuevo=(TextView) findViewById(R.id.JuegoNuevo);
        empieza=(TextView) findViewById(R.id.Empieza);
        tiempo=(TextView) findViewById(R.id.tiempo);







         fondo.setImageResource(R.drawable.fondocolorjuego);
        juegoNuevo.setTextColor(Color.WHITE);
        empieza.setTextColor(Color.WHITE);
        tiempo.setTextColor(Color.WHITE);









    }
    public void ponerPreguntasCero(){

        PreguntasDbHelper preguntasAdmin = new PreguntasDbHelper(this, "preguntas", null, 1);
        SQLiteDatabase BaseDeDatos = preguntasAdmin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        for(int i=0;i<partida.getNumerodePreguntasConText();i++){

            String[] posicion=new String[]{Integer.toString(i)+"Pr"};
            registro.put("preguntada",0);

            Cursor fila = BaseDeDatos.rawQuery("select preguntada from preguntas where numeroPregunta=?",posicion);

            if (fila.moveToFirst()) {
                BaseDeDatos.update("preguntas", registro, "numeroPregunta=?", posicion);
            }
            fila.close();
        }
        BaseDeDatos.close();




    }






    }

