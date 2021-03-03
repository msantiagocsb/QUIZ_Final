package practica1_quiz.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Juego_Imagen extends AppCompatActivity {
    TextView pregunta;
    Button respuesta1,respuesta2,respuesta3,respuesta4;
    private int pregunta_elegida;
    private CountDownTimer reloj;
    private ProgressBar progreso;//variable que va aumentando el tiempo y disminuyendo el tamaño del circulo de tiempo
    private boolean respondida;//variable booleana que vale true cuando se ha respondido la pregunta, y false si se ha pasado el tiempo y no se ha respondido nada
    private Partida partida;
    private ImageView fondo,preguntaImage;
    private TextView preguntaText,numeropreguntaText;
    private ArrayList<Pregunta> Preguntas_preparadas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_imagen);

        partida = (Partida) Comunicador.getObjeto(partida);

        if(partida.getUsuario().getDarkMode()==false){
            cambiarColor();
        }

        for(int i=0;i<partida.getPreguntas_partida().size();i++){
            Preguntas_preparadas.add(partida.getPreguntas_partida().get(i));
        }

        final TextView textView = (TextView)findViewById(R.id.text_numero_pregunta);
        String string_puntuacion=Integer.toString(partida.getNumero_pregunta()+1);
        textView.setText(string_puntuacion);


        pregunta_elegida=(int)(Math.random()*Preguntas_preparadas.size());
        while((Preguntas_preparadas.get(pregunta_elegida).getHayImagen()==false)||(Preguntas_preparadas.get(pregunta_elegida).getPreguntada()==true)){//solo coge las preguntas que sí tengas imagen
            pregunta_elegida=(int)(Math.random()*Preguntas_preparadas.size());
        }

        Preguntas_preparadas.get(pregunta_elegida).setPreguntada(true);//para que no repita la pregunta

        pregunta = (TextView) findViewById(R.id.Pregunta);
        respuesta1 = (Button) findViewById(R.id.respuesta1);
        respuesta2 = (Button) findViewById(R.id.respuesta2);
        respuesta3 = (Button) findViewById(R.id.respuesta3);
        respuesta4 = (Button) findViewById(R.id.respuesta4);
        respondida=false;

        progreso = findViewById(R.id.tiempo2);//le dices donde tiene que aumentar o decrementar el tiempo
        progreso.setIndeterminate(false);
        hacerPregunta();
        reloj = crearReloj();


       final Button boton_respuesta1 = (Button) findViewById(R.id.respuesta1);
        boton_respuesta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(0).getValidacion()==true){
                    boton_respuesta1.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagen.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    boton_respuesta1.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagen.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                    //siguientePregunta();
                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido

            }
        });
       final Button boton_respuesta2 = (Button) findViewById(R.id.respuesta2);
        boton_respuesta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(1).getValidacion()==true){
                    boton_respuesta2.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagen.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    boton_respuesta2.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagen.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                    //siguientePregunta();
                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido

            }
        });
        final Button boton_respuesta3 = (Button) findViewById(R.id.respuesta3);
        boton_respuesta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(2).getValidacion()==true){
                    boton_respuesta3.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagen.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    boton_respuesta3.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagen.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                   // siguientePregunta();
                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido

            }
        });
       final Button boton_respuesta4 = (Button) findViewById(R.id.respuesta4);
        boton_respuesta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(3).getValidacion()==true){
                    boton_respuesta4.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagen.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    boton_respuesta4.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagen.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                    //siguientePregunta();
                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido

            }
        });
    }
    @Override
    public void onBackPressed() {//para que no funcione el boton de atras mientras estas repondiendo

    }

    public void siguientePregunta() {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {//para que tarde unos milisegundos en la siguiente pregunta
            public void run() {

                if ((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas()-1)) {
                    Comunicador.setObjeto(partida);
                    reloj.cancel();
                    Intent intent = new Intent(Juego_Imagen.this, Final.class);
                    startActivity(intent);
                    finish();

                } else {
                    reloj.cancel();
                    partida.incrementarNumPregunta();
                    Comunicador.setObjeto(partida);
                    int i = (int) (Math.random() * 4 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                    switch (i) {
                        case 1:


                            Intent intent = new Intent(Juego_Imagen.this, Juego_Basico.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 2:

                            Intent intent2 = new Intent(Juego_Imagen.this, Juego_Radio.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case 3:
                            Intent intent3 = new Intent(Juego_Imagen.this, Juego_Lista.class);
                            startActivity(intent3);
                            finish();
                            break;
                        case 4:
                            Intent intent4 = new Intent(Juego_Imagen.this, Juego_Imagenes.class);
                            startActivity(intent4);
                            finish();
                            break;

                        default:
                            Toast.makeText(Juego_Imagen.this, "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                            break;
                    }
                    //}
                }
            }
        }, 1000);
        //for(int j=0;j<5;j++){//hay que darle una vuelta conseguir que haga x preguntas una vez terminada cada una

    }


    public void hacerPregunta(){

        pregunta.setText((Preguntas_preparadas.get(pregunta_elegida).getPregunta()));
        ImageView imagen_pregunta=(ImageView) findViewById(R.id.imagen_pregunta);

        switch (Preguntas_preparadas.get(pregunta_elegida).getImagen()){//manda a la imagenView la imagen que corresponda con el string que tiene la pregunta
            case "Paris":
                imagen_pregunta.setImageResource(R.mipmap.paris);//busca en la carpeta mimap ese archivo
                break;

            case "Budapest":
                imagen_pregunta.setImageResource(R.mipmap.budapest);
                break;
            case "StarWars":
                imagen_pregunta.setImageResource(R.mipmap.starwars);
                break;
            case "Dinamarca":
                imagen_pregunta.setImageResource(R.mipmap.dinamarca);
                break;
            case "ONU":
                imagen_pregunta.setImageResource(R.mipmap.onu);
                break;
            case "Capitan_America":
                imagen_pregunta.setImageResource(R.mipmap.capitan_america);
                break;
            case "Cersei":
                imagen_pregunta.setImageResource(R.mipmap.cersei);
                break;
            case "cena":
                imagen_pregunta.setImageResource(R.mipmap.ultima_cena);
                break;
            case "tajMahal":
                imagen_pregunta.setImageResource(R.mipmap.taj_mahal);
                break;
            case  "gernica":
                imagen_pregunta.setImageResource(R.mipmap.gernica);
                break;
            case  "australia":
                imagen_pregunta.setImageResource(R.mipmap.australia);
                break;
            case  "austria":
                imagen_pregunta.setImageResource(R.mipmap.austria);
                break;
            case  "maul":
                imagen_pregunta.setImageResource(R.mipmap.maul);
                break;
            case "thanos":
                imagen_pregunta.setImageResource(R.mipmap.thanos);
                break;

            default:
                break;
        }


        respuesta1.setText(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(0).getRespuesta());
        respuesta2.setText(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(1).getRespuesta());
        respuesta3.setText(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(2).getRespuesta());
        respuesta4.setText(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(3).getRespuesta());

    }
    public void mensajeTiempo(){//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.tiempo,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -80);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        // Set the countdown to display the toast
        CountDownTimer temporizador;
        temporizador = new CountDownTimer(toastDurationInMilliSeconds, 100 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }
            public void onFinish() {
                toast.cancel();
            }
        };
        toast.show();
        temporizador.start();

    }
    public void mensajeAcierto(){//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.acierto,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -80);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        // Set the countdown to display the toast
        CountDownTimer temporizador;
        temporizador = new CountDownTimer(toastDurationInMilliSeconds, 100 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }
            public void onFinish() {
                toast.cancel();
            }
        };
        toast.show();
        temporizador.start();

    }

    public CountDownTimer crearReloj() {
        reserProgreso();
        CountDownTimer reloj = new CountDownTimer(15000, 100) {
            public void onTick(long millisUntilFinished) {
                if(partida.getUsuario().getDarkMode()==false){
                    progreso.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 255, 255  )));}
                if(progreso.getProgress()>=1000){
                    progreso.setProgressTintList(ColorStateList.valueOf(Color.rgb(237, 22, 22  )));
                    // progreso.setBackgroundColor(Color.rgb(237, 22, 22  ));
                }
                //textView.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
                progreso.setProgress(progreso.getProgress() + 10);

            }

            @Override
            public void onFinish() {
                if(respondida==false){
                    mensajeTiempo();
                    partida.setFalladas(partida.getFalladas()+1);
                    partida.setPuntuacion(partida.getPuntuacion()-3);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoTiempo().reproducir(Juego_Imagen.this);
                    }
                    siguientePregunta();}

            }
        }.start();

        return reloj;
    }
    public void reserProgreso() {
        progreso.setMax(1500);
        progreso.setProgress(0);
    }
    public void bloquearBotones(){
        respuesta1.setEnabled(false);
        respuesta2.setEnabled(false);
        respuesta3.setEnabled(false);
        respuesta4.setEnabled(false);

    }
    public void cambiarColor(){

        fondo= (ImageView) findViewById(R.id.fondo);
        preguntaImage=(ImageView) findViewById(R.id.imageView2);

        preguntaText=(TextView) findViewById(R.id.Pregunta);
        respuesta1 = (Button) findViewById(R.id.respuesta1);
        respuesta2 = (Button) findViewById(R.id.respuesta2);
        respuesta3 = (Button) findViewById(R.id.respuesta3);
        respuesta4 = (Button) findViewById(R.id.respuesta4);
        numeropreguntaText=(TextView) findViewById(R.id.text_numero_pregunta);

        preguntaImage.setImageResource((R.drawable.fondoblanco_imagen));
        numeropreguntaText.setTextColor(Color.rgb(255,255,255));


        int i = (int) (Math.random() * 2+ 1);
        switch (i) {
            case 1:
                fondo.setImageResource(R.drawable.fondocolor);
                preguntaImage.setImageResource((R.drawable.fondopreguntaimagen_));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Morado));
                break;
            case 2:
                fondo.setImageResource(R.drawable.fondocolor2);
                preguntaImage.setImageResource((R.drawable.fondopreguntaimagen_2));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Azul));
                break;


            /*case 4:
                fondo.setImageResource(R.drawable.fondocolo);
                break;*/
        }


        preguntaText.setTextColor(Color.rgb(255,255,255));

        respuesta1.setBackgroundColor(Color.rgb(255,255,255));
        respuesta1.setTextColor(preguntaText.getContext().getResources().getColor(R.color.colorPrimary));

        respuesta2.setBackgroundColor(Color.rgb(255,255,255));
        respuesta2.setTextColor(preguntaText.getContext().getResources().getColor(R.color.colorPrimary));

        respuesta3.setBackgroundColor(Color.rgb(255,255,255));
        respuesta3.setTextColor(preguntaText.getContext().getResources().getColor(R.color.colorPrimary));

        respuesta4.setBackgroundColor(Color.rgb(255,255,255));
        respuesta4.setTextColor(preguntaText.getContext().getResources().getColor(R.color.colorPrimary));






    }

}

