package practica1_quiz.quiz;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import static practica1_quiz.quiz.Juego.Preguntas_preparadas;

public class Juego_Basico extends AppCompatActivity {

    TextView pregunta;
    Button respuesta1,respuesta2,respuesta3,respuesta4;
    private Button boton_respuesta1,boton_respuesta2,boton_respuesta3,boton_respuesta4;
    private int pregunta_elegida;//numero random que se  crea  para elegir una pregunta
    private Partida partida;//clase partida que contiene todos los datos de la partida nombre, puntuacion, numero de pregunta etc
    private CountDownTimer reloj;//reloj de cada pregunta
    private boolean respondida;//variable booleana que vale true cuando se ha respondido la pregunta, y false si se ha pasado el tiempo y no se ha respondido nada
    private ImageView fondo,preguntaImage;
    private TextView preguntaText,numeropreguntaText;
    private ProgressBar progreso;//variable que va aumentando el tiempo y disminuyendo el tamaño del circulo de tiempo
    private ArrayList<Pregunta> Preguntas_preparadas=new ArrayList<>();//copia todas las preguntas que hay
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_basico);//le dices en que layout actua esta activity

        partida = (Partida) Comunicador.getObjeto(partida);//carga la la partida que le pasan
        if(partida.getUsuario().getDarkMode()==false){
            cambiarColor();
        }
        /*for(int i=0;i<partida.getPreguntas_partida().size();i++){//añada todas las preguntas al array de preguntas de cada pregunta
            Preguntas_preparadas.add(partida.getPreguntas_partida().get(i));
        }*/

        final TextView textView = (TextView)findViewById(R.id.text_numero_pregunta);//busca el textView con el nombre "text_numero_pregunta"
        String string_puntuacion=Integer.toString(partida.getNumero_pregunta()+1);
        textView.setText(string_puntuacion);//manda el string a la text view


        pregunta_elegida = (int) (Math.random() * partida.getNumerodePreguntasConText());
        while(estaPreguntada()==true) {//solo coge las preguntas que no estén preguntadas
            pregunta_elegida = (int) (Math.random() * partida.getNumerodePreguntasConText());
        }
        // Preguntas_preparadas.get(pregunta_elegida).setPreguntada(true);//para que no repita la pregunta

        preguntadaTrue();

        pregunta = (TextView) findViewById(R.id.Pregunta);//
        respuesta1 = (Button) findViewById(R.id.respuesta1);
        respuesta2 = (Button) findViewById(R.id.respuesta2);
        respuesta3 = (Button) findViewById(R.id.respuesta3);
        respuesta4 = (Button) findViewById(R.id.respuesta4);

        respondida=false;//respondida de momento es igual a false para que mientras no responda el reloj siga corriendo

        progreso = findViewById(R.id.tiempo);//le dices donde tiene que aumentar o decrementar el tiempo
        progreso.setIndeterminate(false);
        hacerPregunta();//hace la pregunta
        reloj = crearReloj();//crea el reloj


       boton_respuesta1 = (Button) findViewById(R.id.respuesta1);//si toca el boton de la pregunta 1
        boton_respuesta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comprobarAcierto(1)==true){//si la respuesta elegida tiene la validacion a true
                    boton_respuesta1.setBackgroundColor(Color.rgb(24,173,11));//pinta en verde
                    partida.setPuntuacion(partida.getPuntuacion()+5);//aunmenta la puntuacion
                    partida.setAcertadas(partida.getAcertadas()+1);//aunmenta en 1 las acertadas
                    mensajeAcierto();//muestra el mensaje de acierto
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Basico.this);//reproduce el sonido de acierto
                    }
                    siguientePregunta();//crea siguiente pregunta
                }else{//si la validacion es false
                    boton_respuesta1.setBackgroundColor(Color.rgb(237, 22, 22  ));//pinta de rojo
                    partida.setPuntuacion(partida.getPuntuacion()-2);//quita 2 puntos
                    partida.setFalladas(partida.getFalladas()+1);//aumenta las falaldas
                    if(partida.getEfectosPreguntas()) {
                    partida.getSonidoFallo().reproducir(Juego_Basico.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");//muestra el mensaje de error

                }
                respondida=true;//respondida en true
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido


            }
        });
        boton_respuesta2 = (Button) findViewById(R.id.respuesta2);
        boton_respuesta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comprobarAcierto(2)==true){
                    boton_respuesta2.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    mensajeAcierto();
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Basico.this);//reproduce el sonido de acierto
                    }
                    siguientePregunta();
                }else{
                    boton_respuesta2.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Basico.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido

            }
        });
        boton_respuesta3 = (Button) findViewById(R.id.respuesta3);
        boton_respuesta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comprobarAcierto(3)==true){
                    boton_respuesta3.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    mensajeAcierto();
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Basico.this);//reproduce el sonido de acierto
                    }
                    siguientePregunta();

                }else{
                    boton_respuesta3.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Basico.this);
                    }
                    new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                }
                respondida=true;
                bloquearBotones();
                reloj.cancel();//para el reloj para que no siga contando una vez repondido

            }
        });
        boton_respuesta4 = (Button) findViewById(R.id.respuesta4);
        boton_respuesta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comprobarAcierto(4)==true){
                    boton_respuesta4.setBackgroundColor(Color.rgb(24,173,11));
                    partida.setPuntuacion(partida.getPuntuacion()+5);
                    partida.setAcertadas(partida.getAcertadas()+1);
                    mensajeAcierto();
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoAcierto().reproducir(Juego_Basico.this);//reproduce el sonido de acierto
                    }
                    siguientePregunta();
                }else{
                    boton_respuesta4.setBackgroundColor(Color.rgb(237, 22, 22  ));
                    partida.setPuntuacion(partida.getPuntuacion()-2);
                    partida.setFalladas(partida.getFalladas()+1);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoFallo().reproducir(Juego_Basico.this);
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

            if ((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas()-1)) {//si se ha llegado al numero maximo de pregunta
                reloj.cancel();//para el reloj antes de la siguiente actividad por si no lo hizo
                Intent intent = new Intent(Juego_Basico.this, Final.class);//va a la pantalla final de recuento
                startActivity(intent);//aqui entra en la activity
                finish();

            } else {//si aun no era la ultima pregunta
                reloj.cancel();//para el reloj antes de la siguiente actividad por si no lo hizo
                partida.incrementarNumPregunta();
                Comunicador.setObjeto(partida);

                int i = (int) (Math.random() * 4 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                switch (i) {
                    case 1:


                        Intent intent = new Intent(Juego_Basico.this, Juego_Lista.class);//si sale 1 va a Juego_Basico
                        startActivity(intent);
                        finish();
                        break;
                    case 2:

                        Intent intent2 = new Intent(Juego_Basico.this, Juego_Radio.class);//si sale 2 va a Juego_Radio
                        startActivity(intent2);
                        finish();
                        break;
                    case 3:
                        Intent intent3 = new Intent(Juego_Basico.this, Juego_Imagen.class);//si sale 3 va a juego Imagen
                        startActivity(intent3);
                        finish();
                        break;
                    case 4:
                        Intent intent4 = new Intent(Juego_Basico.this, Juego_Imagenes.class);//si sale 4 va a Juego_Lista
                        startActivity(intent4);
                        finish();
                        break;


                }
                //}
            }
        }
    }, 1000);//lo demoro 300 millisegundos para que se vea mejor el cambio de color de los botones


}


    public void hacerPregunta(){//pone en cada apartado textView o Button las respuestas y la pregunta correspondiente a la pregunta elegida del ArrayList de pregunta

        pregunta.setText((buscarTexto("pregunta", 0)));


        respuesta1.setText(buscarTexto("respuesta", 1));
        respuesta2.setText(buscarTexto("respuesta", 2));
        respuesta3.setText(buscarTexto("respuesta", 3));
        respuesta4.setText(buscarTexto("respuesta", 4));

    }
    public void mensajeTiempo(){//para que salga el toast de que el tiempo ha concluido
        int toastDurationInMilliSeconds = 900;//tiempo que dura el contador
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.tiempo,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -230);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        // Set the countdown to display the toast
        CountDownTimer temporizador;
        temporizador = new CountDownTimer(toastDurationInMilliSeconds, 100 /*Tick duration*/) {//da valores al temporizador
            public void onTick(long millisUntilFinished) {//mientras esté sin acabar enseña el toast
                toast.show();
            }
            public void onFinish() {//si ha terminado quita el toast, que es el mensaje de tiempo
                toast.cancel();
            }
        };
        toast.show();//desde el inicio muestra el toast
        temporizador.start();//inicia el temporizador

    }

    public void mensajeAcierto(){//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.acierto,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -230);
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
                    progreso.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 255, 255  )));
                }
                    if(progreso.getProgress()>=1000){
                        progreso.setProgressTintList(ColorStateList.valueOf(Color.rgb(237, 22, 22  )));
                        // progreso.setBackgroundColor(Color.rgb(237, 22, 22  ));

                }

                progreso.setProgress(progreso.getProgress() + 10);//aumenta el progreso que enseña la barra de tiempo de 10 en 10

            }

            @Override
            public void onFinish() {//si el tiempo concluye
                if(respondida==false){//y respondida es false
                    mensajeTiempo();//saca el toast de que el tiempo acabó
                    partida.setFalladas(partida.getFalladas()+1);//aumenta en 1 las falladas
                    partida.setPuntuacion(partida.getPuntuacion()-2);//te cuenta como fallada la pregunta
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoTiempo().reproducir(Juego_Basico.this);
                    }
                    siguientePregunta();}

            }
        }.start();

        return reloj;
    }
    public void reserProgreso() {
        progreso.setMax(1500);//la capacidad total de la barra de tiempo, se pueden poner otros numeros pero asi al ir de 10 en 10 hasta 1500 la barra avanxa poco a poco
        progreso.setProgress(0);
    }
    public void bloquearBotones(){
        respuesta1.setEnabled(false);
        respuesta2.setEnabled(false);
        respuesta3.setEnabled(false);
        respuesta4.setEnabled(false);

    }
    public void cambiarColor(){

         fondo= (ImageView) findViewById(R.id.fondoBasico);
         preguntaImage=(ImageView) findViewById(R.id.imagePregunta);

        preguntaText=(TextView) findViewById(R.id.Pregunta);
        respuesta1 = (Button) findViewById(R.id.respuesta1);
        respuesta2 = (Button) findViewById(R.id.respuesta2);
        respuesta3 = (Button) findViewById(R.id.respuesta3);
        respuesta4 = (Button) findViewById(R.id.respuesta4);
        numeropreguntaText=(TextView) findViewById(R.id.text_numero_pregunta);

        numeropreguntaText.setTextColor(Color.rgb(255,255,255));




        int i = (int) (Math.random() * 2+ 1);
        switch (i) {
            case 1:
                fondo.setImageResource(R.drawable.fondocolor);
                preguntaImage.setImageResource((R.drawable.fondobasico_));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Morado));

                break;
            case 2:
                fondo.setImageResource(R.drawable.fondocolor2);
                preguntaImage.setImageResource((R.drawable.fondobasico_2));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Azul));

                break;



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
    public boolean comprobarAcierto(Integer posicion){
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "preguntas",null,1);
        SQLiteDatabase BaseDeDatos= rankingAdmin.getWritableDatabase();
        boolean acierto=false;
        int validacionCorrecta=0;
        String[] preguntaPreguntada = new String[]{Integer.toString(pregunta_elegida)+"Pr"};
        String respuesta="respuesta"+Integer.toString(posicion)+"Cierta";
        Cursor fila1= BaseDeDatos.rawQuery("select "+respuesta+" from preguntas where numeroPregunta=?",preguntaPreguntada);
        if(fila1.moveToFirst()) {
            validacionCorrecta=fila1.getInt(0);
            if (validacionCorrecta==1) {
               // Toast.makeText(this, "Acertada", Toast.LENGTH_SHORT).show();
                acierto = true;
            }else{
              //  Toast.makeText(this, "Fallada", Toast.LENGTH_SHORT).show();
            }
        }else{
           // Toast.makeText(this, "Pregunta no Encontrada", Toast.LENGTH_SHORT).show();
        }

        BaseDeDatos.close();


        return acierto;




    }
    public void preguntadaTrue() {
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "preguntas", null, 1);
        SQLiteDatabase BaseDeDatos = rankingAdmin.getWritableDatabase();
        String[] numeroPr = new String[]{Integer.toString(pregunta_elegida) + "Pr"};
        ContentValues registro = new ContentValues();
        ContentValues registro2 = new ContentValues();

        Cursor fila = BaseDeDatos.rawQuery("select preguntada from preguntas where numeroPregunta=?", numeroPr);
        registro.put("preguntada", 1);

        if (fila.moveToFirst()) {
            BaseDeDatos.update("preguntas", registro, "numeroPregunta=?", numeroPr);
        } else {

        }
        fila.close();

        //partida.getUsuario().setRecienRegistrado(false);
        BaseDeDatos.close();
    }

    public boolean estaPreguntada() {
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "preguntas", null, 1);
        SQLiteDatabase BaseDeDatos = rankingAdmin.getWritableDatabase();
        Boolean estadoPreguntada=false;


        String[] preguntaPreguntada = new String[]{Integer.toString(pregunta_elegida) + "Pr"};

        Cursor fila1 = BaseDeDatos.rawQuery("select preguntada from preguntas where numeroPregunta=?", preguntaPreguntada);
        if (fila1.moveToFirst()) {
            int encontrado = fila1.getInt(0);
            if (encontrado==1) {
                estadoPreguntada=true;
                //Toast.makeText(this, "Tetx No encontrado", Toast.LENGTH_SHORT).show();
            }
        }
        fila1.close();
        BaseDeDatos.close();
        return estadoPreguntada;

    }

    public String buscarTexto(String textoABuscar, int numeroRespuesta) {
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "preguntas", null, 1);
        SQLiteDatabase BaseDeDatos = rankingAdmin.getWritableDatabase();
        String encontrado = "nada";
        if (textoABuscar.equals("pregunta")) {

            String[] preguntaPreguntada = new String[]{Integer.toString(pregunta_elegida) + "Pr"};

            Cursor fila1 = BaseDeDatos.rawQuery("select " + textoABuscar + " from preguntas where numeroPregunta=?", preguntaPreguntada);
            if (fila1.moveToFirst()) {
                encontrado = fila1.getString(0);
                if (!encontrado.equals("nada")) {
                    //  Toast.makeText(this, "Tetx No encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    //   Toast.makeText(this, "Text Encontrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Toast.makeText(this, "Pregunta no Encontrada", Toast.LENGTH_SHORT).show();
            }
        } else {
            String[] preguntaPreguntada = new String[]{Integer.toString(pregunta_elegida) + "Pr"};

            String respuesta = "respuesta" + Integer.toString(numeroRespuesta);
            Cursor fila1 = BaseDeDatos.rawQuery("select " + respuesta + " from preguntas where numeroPregunta=?", preguntaPreguntada);
            if (fila1.moveToFirst()) {
                encontrado = fila1.getString(0);
                if (!encontrado.equals("nada")) {
                    // Toast.makeText(this, "Tetx No encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(this, "Text Encontrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Toast.makeText(this, "Pregunta no Encontrada", Toast.LENGTH_SHORT).show();
            }
            fila1.close();
        }


        BaseDeDatos.close();


        return encontrado;


    }

}
/*

*/
