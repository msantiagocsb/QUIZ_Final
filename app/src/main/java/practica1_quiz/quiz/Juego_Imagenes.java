package practica1_quiz.quiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import static practica1_quiz.quiz.Juego.Preguntas_preparadas;

public class Juego_Imagenes extends AppCompatActivity {

    TextView pregunta;
    ImageButton respuesta1,respuesta2,respuesta3,respuesta4;
    private int pregunta_elegida;
    private Partida partida;
    private ImageView fondo,preguntaImage;
    private TextView preguntaText,numeropreguntaText;
    private CountDownTimer reloj;
    private boolean respondida;//variable booleana que vale true cuando se ha respondido la pregunta, y false si se ha pasado el tiempo y no se ha respondido nada

    private ProgressBar progreso;//variable que va aumentando el tiempo y disminuyendo el tamaño del circulo de tiempo
    private ArrayList<Pregunta> Preguntas_preparadas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_imagenes);

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
        while((Preguntas_preparadas.get(pregunta_elegida).getRespuestaImagenes()==false)||(Preguntas_preparadas.get(pregunta_elegida).getPreguntada()==true)){//solo coge las preguntas que no tenga imagen
            pregunta_elegida=(int)(Math.random()*Preguntas_preparadas.size());
        }
        Preguntas_preparadas.get(pregunta_elegida).setPreguntada(true); //para que no se vuelva a repetir la pregunta

        pregunta = (TextView) findViewById(R.id.Pregunta);
        respuesta1 = (ImageButton) findViewById(R.id.respuesta1);
        respuesta2 = (ImageButton) findViewById(R.id.respuesta2);
        respuesta3 = (ImageButton) findViewById(R.id.respuesta3);
        respuesta4 = (ImageButton) findViewById(R.id.respuesta4);

        respondida=false;

        progreso = findViewById(R.id.tiempo);//le dices donde tiene que aumentar o decrementar el tiempo
        progreso.setIndeterminate(false);
        hacerPregunta();
        reloj = crearReloj();
       // reloj.start();
       // reserProgreso();


       //falta hacer cuando toques cada imagen
        //final ImageButton boton_respuesta1 = (ImageButtonButton) findViewById(R.id.respuesta1);
        respuesta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(0).getValidacion()==true){
                    respuesta1.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagenes.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    respuesta1.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagenes.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido


            }
        });
        respuesta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(1).getValidacion()==true){
                    respuesta2.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagenes.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    respuesta2.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagenes.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido


            }
        });
        respuesta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(2).getValidacion()==true){
                    respuesta3.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagenes.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    respuesta3.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagenes.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido


            }
        });
        respuesta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Preguntas_preparadas.get(pregunta_elegida).getRespuesta(3).getValidacion()==true){
                    respuesta4.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Imagenes.this);//reproduce el sonido de acierto
                    }
                    mensajeAcierto();
                    siguientePregunta();
                }else{
                    respuesta4.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Imagenes.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

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
    handler.postDelayed(new Runnable() {
        public void run() {//para demorar el tiempo x millisegundos

            if ((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas()-1)) {
                Comunicador.setObjeto(partida);
                reloj.cancel();
                Intent intent = new Intent(Juego_Imagenes.this, Final.class);
                startActivity(intent);
                finish();

            } else {
                reloj.cancel();
                partida.incrementarNumPregunta();
                Comunicador.setObjeto(partida);

                int i = (int) (Math.random() * 4 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                switch (i) {
                    case 1:


                        Intent intent = new Intent(Juego_Imagenes.this, Juego_Basico.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:

                        Intent intent2 = new Intent(Juego_Imagenes.this, Juego_Radio.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case 3:
                        Intent intent3 = new Intent(Juego_Imagenes.this, Juego_Imagen.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case 4:
                        Intent intent4 = new Intent(Juego_Imagenes.this, Juego_Lista.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case 5:
                        Intent intent5 = new Intent(Juego_Imagenes.this, Juego_Imagenes.class);
                        startActivity(intent5);
                        finish();
                        break;
                    default:
                        Toast.makeText(Juego_Imagenes.this, "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                        break;
                }
                //}
            }
        }
    }, 1000);//lo demoro 300 millisegundos para que se vea mejor el cambio de color de los botones


}


    public void hacerPregunta(){

        pregunta.setText((Preguntas_preparadas.get(pregunta_elegida).getPregunta()));

        buscarImagen(respuesta1,0);//para hacer cada pregunta llamo al metodo buscar imagen y va creando cada ImageButton
        buscarImagen(respuesta2,1);
        buscarImagen(respuesta3,2);
        buscarImagen(respuesta4,3);


        //poner imagen de cada

    }
    public void buscarImagen(ImageButton respuestaN, int numeroPrregunta){
        pregunta.setText((Preguntas_preparadas.get(pregunta_elegida).getPregunta()));


        switch (Preguntas_preparadas.get(pregunta_elegida).getRespuesta(numeroPrregunta).getRespuesta()){
            case "Paris":
                respuestaN.setImageResource(R.mipmap.paris);
                break;

            case "Budapest":
                respuestaN.setImageResource(R.mipmap.budapest);
                break;
            case "StarWars":
                respuestaN.setImageResource(R.mipmap.starwars);
                break;
            case "Dinamarca":
                respuestaN.setImageResource(R.mipmap.dinamarca);
                break;
            case "ONU":
                respuestaN.setImageResource(R.mipmap.onu);
                break;
            case "Capitan_America":
                respuestaN.setImageResource(R.mipmap.capitan_america);
                break;
            case "Cersei":
                respuestaN.setImageResource(R.mipmap.cersei);
                break;
            case "cena":
                respuestaN.setImageResource(R.mipmap.ultima_cena);
                break;
            case "tajMahal":
                respuestaN.setImageResource(R.mipmap.taj_mahal);
                break;
            case  "gernica":
                respuestaN.setImageResource(R.mipmap.gernica);
                break;
            case  "australia":
                respuestaN.setImageResource(R.mipmap.australia);
                break;
            case  "austria":
                respuestaN.setImageResource(R.mipmap.austria);
                break;
            case  "maul":

                respuestaN.setImageResource(R.mipmap.maul);
                break;
            case "thanos":
                respuestaN.setImageResource(R.mipmap.thanos);
                break;
            case "alemania":
                respuestaN.setImageResource(R.mipmap.alemania);
                break;
            case "belgica":
                respuestaN.setImageResource(R.mipmap.belgica);
                break;
            case "francia":
                respuestaN.setImageResource(R.mipmap.francia);
                break;
            case "dinamarca":
                respuestaN.setImageResource(R.mipmap.dinamarca1);
                break;
            case "yoda":
                respuestaN.setImageResource(R.mipmap.yoda);
                break;
            case "chewbaca":
                respuestaN.setImageResource(R.mipmap.chewbaca);
                break;
            case "jarjar":
                respuestaN.setImageResource(R.mipmap.jar);
                break;
            case "jaba":
                respuestaN.setImageResource(R.mipmap.jaba);
                break;
            case "tiburon":
                respuestaN.setImageResource(R.mipmap.tiburon);
                break;
            case "orca":
                respuestaN.setImageResource(R.mipmap.orca);
                break;
            case "beluga":
                respuestaN.setImageResource(R.mipmap.beluga);
                break;
            case "ballena":
                respuestaN.setImageResource(R.mipmap.ballena);
                break;
            case "oviwan":
                respuestaN.setImageResource(R.mipmap.oviwan);
                break;
            case "hansolo":
                respuestaN.setImageResource(R.mipmap.hansolo);
                break;
            case "lando":
                respuestaN.setImageResource(R.mipmap.lando);
                break;
            case "luke":
                respuestaN.setImageResource(R.mipmap.luke);
                break;
            case "rcheca":
                respuestaN.setImageResource(R.mipmap.rcheca );
                break;
            case "gryffindor":
                respuestaN.setImageResource(R.mipmap.gryffindor );
                break;
            case "slytherin":
                respuestaN.setImageResource(R.mipmap.slytherin );
                break;
            case "ravenclaw":
                respuestaN.setImageResource(R.mipmap.ravewclaw );
                break;
            case "hufflepuff":
                respuestaN.setImageResource(R.mipmap.hufflepuff );
                break;
            case "nu":
                respuestaN.setImageResource(R.mipmap.nu );
                break;
            case "macaco":
                respuestaN.setImageResource(R.mipmap.macaco );
                break;
            case "facoquero":
                respuestaN.setImageResource(R.mipmap.facoquero );
                break;
            case "acdc":
                respuestaN.setImageResource(R.mipmap.acdc );
                break;
            case "freddy":
                respuestaN.setImageResource(R.mipmap.freddy );
                break;
            case "ledd":
                respuestaN.setImageResource(R.mipmap.ledd );
                break;
            case "mick":
                respuestaN.setImageResource(R.mipmap.mick );
                break;
            case "jabali":
                respuestaN.setImageResource(R.mipmap.jabali );
                break;

            default:
                break;
        }

    }
    public void mensajeTiempo(){//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.tiempo,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -290);
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
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -290);
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
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoTiempo().reproducir(Juego_Imagenes.this);
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
        numeropreguntaText=(TextView) findViewById(R.id.text_numero_pregunta);

        preguntaText=(TextView) findViewById(R.id.Pregunta);
        respuesta1 = (ImageButton) findViewById(R.id.respuesta1);
        respuesta2 = (ImageButton) findViewById(R.id.respuesta2);
        respuesta3 = (ImageButton) findViewById(R.id.respuesta3);
        respuesta4 = (ImageButton) findViewById(R.id.respuesta4);

        preguntaImage.setImageResource((R.drawable.fondoblanco_imagenes));
        numeropreguntaText.setTextColor(Color.rgb(255,255,255));

        int i = (int) (Math.random() * 2+ 1);
        switch (i) {
            case 1:
                fondo.setImageResource(R.drawable.fondocolor);
                preguntaImage.setImageResource((R.drawable.fondopreguntaimagenes_));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Morado));
                break;
            case 2:
                fondo.setImageResource(R.drawable.fondocolor2);
                preguntaImage.setImageResource((R.drawable.fondopreguntaimagenes_2));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Azul));
                break;


            /*case 4:
                fondo.setImageResource(R.drawable.fondocolo);
                break;*/
        }

        preguntaText.setTextColor(Color.rgb(255,255,255));

        respuesta1.setBackgroundColor(Color.rgb(255,255,255));
        //respuesta1.setTextColor(preguntaText.getContext().getResources().getColor(R.color.colorPrimary));

        respuesta2.setBackgroundColor(Color.rgb(255,255,255));


        respuesta3.setBackgroundColor(Color.rgb(255,255,255));


        respuesta4.setBackgroundColor(Color.rgb(255,255,255));








    }

}
/*

*/
