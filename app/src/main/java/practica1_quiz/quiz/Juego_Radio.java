package practica1_quiz.quiz;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Juego_Radio extends AppCompatActivity {
    private RadioGroup grupoRespuestas;
    private Partida partida;
    private CountDownTimer reloj;
    private ProgressBar progreso;//variable que va aumentando el tiempo y disminuyendo el tamaño del circulo de tiempo
    private boolean respondida;//variable booleana que vale true cuando se ha respondido la pregunta, y false si se ha pasado el tiempo y no se ha respondido nada
    private ArrayList<Pregunta> Preguntas_preparadas = new ArrayList<>();
    private int pregunta_elegida;
    private TextView pregunta;
    private ImageView fondo, preguntaImage, radioImage;
    private TextView preguntaText, numeropreguntaText;
    private RadioButton respuesta1, respuesta2, respuesta3, respuesta4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_radio);

        partida = (Partida) Comunicador.getObjeto(partida);//carga  la partida para la pregunta

        if (partida.getUsuario().getDarkMode() == false) {
            cambiarColor();
        }

        /*for (int i = 0; i < partida.getPreguntas_partida().size(); i++) {
            Preguntas_preparadas.add(partida.getPreguntas_partida().get(i));
        }*/
        pregunta_elegida = (int) (Math.random() * partida.getNumerodePreguntasConText());
        while (estaPreguntada()==true) {//solo coge las preguntas que no estén preguntadas
            pregunta_elegida = (int) (Math.random() * partida.getNumerodePreguntasConText());
        }
        // Preguntas_preparadas.get(pregunta_elegida).setPreguntada(true);//para que no repita la pregunta

        preguntadaTrue();

        final TextView textView = (TextView) findViewById(R.id.text_numero_pregunta);
        String string_puntuacion = Integer.toString(partida.getNumero_pregunta() + 1);
        textView.setText(string_puntuacion);

        pregunta = (TextView) findViewById(R.id.pregunta);
        respuesta1 = (RadioButton) findViewById(R.id.respuesta1);
        respuesta2 = (RadioButton) findViewById(R.id.respuesta2);
        respuesta3 = (RadioButton) findViewById(R.id.respuesta3);
        respuesta4 = (RadioButton) findViewById(R.id.respuesta4);


        respondida = false;

        progreso = findViewById(R.id.tiempo3);//le dices donde tiene que aumentar o decrementar el tiempo
        progreso.setIndeterminate(false);
        hacerPregunta();//pone todas las opciones de la pregunta
        reloj = crearReloj();


        grupoRespuestas = (RadioGroup) findViewById(R.id.radioGroup);

        grupoRespuestas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//checkedId variable que te dice que radioButton ha elegido el usuario
                // TODO Auto-generated method stub
                if (checkedId == R.id.respuesta1) {
                    if (comprobarAcierto(1) == true) {
                        partida.setPuntuacion(partida.getPuntuacion() + 5);
                        respuesta1.setTextColor(Color.rgb(24, 173, 11));
                        partida.setAcertadas(partida.getAcertadas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoAcierto().reproducir(Juego_Radio.this);//reproduce el sonido de acierto
                        }
                        mensajeAcierto();
                        siguientePregunta();
                    } else {
                        partida.setPuntuacion(partida.getPuntuacion() - 2);
                        respuesta1.setTextColor(Color.rgb(237, 22, 22));
                        partida.setFalladas(partida.getFalladas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoFallo().reproducir(Juego_Radio.this);
                        }
                        new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                        //siguientePregunta();//para el reloj para que no siga contando una vez repondido
                    }
                    respondida = true;
                    bloquearBotones();
                    reloj.cancel();//para el reloj para que no siga contando una vez repondido
                } else if (checkedId == R.id.respuesta2) {
                    if (comprobarAcierto(2) == true) {
                        partida.setPuntuacion(partida.getPuntuacion() + 5);
                        respuesta2.setTextColor(Color.rgb(24, 173, 11));
                        partida.setAcertadas(partida.getAcertadas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoAcierto().reproducir(Juego_Radio.this);//reproduce el sonido de acierto
                        }
                        mensajeAcierto();
                        siguientePregunta();
                    } else {
                        partida.setPuntuacion(partida.getPuntuacion() - 2);
                        respuesta2.setTextColor(Color.rgb(237, 22, 22));
                        partida.setFalladas(partida.getFalladas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoFallo().reproducir(Juego_Radio.this);
                        }
                        new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                        //siguientePregunta();
                    }
                    respondida = true;
                    bloquearBotones();
                    reloj.cancel();//para el reloj para que no siga contando una vez repondido


                } else if (checkedId == R.id.respuesta3) {
                    if (comprobarAcierto(3) == true) {
                        partida.setPuntuacion(partida.getPuntuacion() + 5);
                        respuesta3.setTextColor(Color.rgb(24, 173, 11));
                        partida.setAcertadas(partida.getAcertadas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoAcierto().reproducir(Juego_Radio.this);//reproduce el sonido de acierto
                        }
                        mensajeAcierto();
                        siguientePregunta();
                    } else {
                        partida.setPuntuacion(partida.getPuntuacion() - 2);
                        respuesta3.setTextColor(Color.rgb(237, 22, 22));
                        partida.setFalladas(partida.getFalladas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoFallo().reproducir(Juego_Radio.this);
                        }
                        new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                        // siguientePregunta();
                    }
                    respondida = true;
                    bloquearBotones();
                    reloj.cancel();//para el reloj para que no siga contando una vez repondido
                } else if (checkedId == R.id.respuesta4) {
                    if (comprobarAcierto(4) == true) {
                        partida.setPuntuacion(partida.getPuntuacion() + 5);
                        respuesta4.setTextColor(Color.rgb(24, 173, 11));
                        partida.setAcertadas(partida.getAcertadas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoAcierto().reproducir(Juego_Radio.this);//reproduce el sonido de acierto
                        }
                        mensajeAcierto();
                        siguientePregunta();
                    } else {
                        partida.setPuntuacion(partida.getPuntuacion() - 2);
                        respuesta4.setTextColor(Color.rgb(237, 22, 22));
                        partida.setFalladas(partida.getFalladas() + 1);
                        if(partida.getEfectosPreguntas()) {
                            partida.getSonidoFallo().reproducir(Juego_Radio.this);
                        }
                        new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");
                        //siguientePregunta();
                    }
                    respondida = true;
                    bloquearBotones();
                    reloj.cancel();//para el reloj para que no siga contando una vez repondido
                }

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
                if ((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas() - 1)) {
                    Comunicador.setObjeto(partida);
                    reloj.cancel();
                    Intent intent = new Intent(Juego_Radio.this, Final.class);
                    startActivity(intent);
                    finish();

                } else {
                    reloj.cancel();
                    partida.incrementarNumPregunta();
                    Comunicador.setObjeto(partida);

                    int i = (int) (Math.random() * 4 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                    switch (i) {
                        case 1:


                            Intent intent = new Intent(Juego_Radio.this, Juego_Basico.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 2:

                            Intent intent2 = new Intent(Juego_Radio.this, Juego_Lista.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case 3:
                            Intent intent3 = new Intent(Juego_Radio.this, Juego_Imagen.class);
                            startActivity(intent3);
                            finish();
                            break;
                        case 4:
                            Intent intent4 = new Intent(Juego_Radio.this, Juego_Imagenes.class);
                            startActivity(intent4);
                            finish();
                            break;
                        default:
                            Toast.makeText(Juego_Radio.this, "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                            break;
                    }
                    //}
                }
            }
        }, 1000);
    }

    public void hacerPregunta() {

        pregunta.setText(buscarTexto("pregunta", 0));
        //respuesta1=(Button) findViewById(R.id.respuesta2);

        respuesta1.setText(buscarTexto("respuesta", 1));
        respuesta2.setText(buscarTexto("respuesta", 2));
        respuesta3.setText(buscarTexto("respuesta", 3));
        respuesta4.setText(buscarTexto("respuesta", 4));

    }

    public void mensajeAcierto() {//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.acierto, null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -380);
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
        // Show the toast and starts the countdown


    }

    public void mensajeTiempo() {//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.tiempo, null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -380);
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
                if (partida.getUsuario().getDarkMode() == false) {
                    progreso.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 255, 255)));
                }
                if (progreso.getProgress() >= 1000) {
                    progreso.setProgressTintList(ColorStateList.valueOf(Color.rgb(237, 22, 22)));
                    // progreso.setBackgroundColor(Color.rgb(237, 22, 22  ));
                }
                //textView.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
                progreso.setProgress(progreso.getProgress() + 10);

            }

            @Override
            public void onFinish() {
                if (respondida == false) {
                    mensajeTiempo();
                    partida.setFalladas(partida.getFalladas() + 1);
                    partida.setPuntuacion(partida.getPuntuacion() - 3);
                    if(partida.getEfectosPreguntas()) {
                        partida.getSonidoTiempo().reproducir(Juego_Radio.this);
                    }
                    siguientePregunta();
                }

            }
        }.start();

        return reloj;
    }

    public void reserProgreso() {
        progreso.setMax(1500);
        progreso.setProgress(0);
    }

    public void bloquearBotones() {
        respuesta1.setEnabled(false);
        respuesta2.setEnabled(false);
        respuesta3.setEnabled(false);
        respuesta4.setEnabled(false);

    }

    public void cambiarColor() {
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{

                        Color.rgb(255, 255, 255),
                        Color.rgb(255, 255, 255),
                }
        );
        fondo = (ImageView) findViewById(R.id.fondo);
        preguntaImage = (ImageView) findViewById(R.id.imageView2);
        radioImage = (ImageView) findViewById(R.id.fondoRadio);


        preguntaText = (TextView) findViewById(R.id.pregunta);
        respuesta1 = (RadioButton) findViewById(R.id.respuesta1);
        respuesta2 = (RadioButton) findViewById(R.id.respuesta2);
        respuesta3 = (RadioButton) findViewById(R.id.respuesta3);
        respuesta4 = (RadioButton) findViewById(R.id.respuesta4);
        numeropreguntaText = (TextView) findViewById(R.id.text_numero_pregunta);


        numeropreguntaText.setTextColor(Color.rgb(255, 255, 255));


        int i = (int) (Math.random() * 2 + 1);
        switch (i) {
            case 1:
                fondo.setImageResource(R.drawable.fondocolor);
                radioImage.setImageResource(R.drawable.fondocolor_);
                preguntaImage.setImageResource((R.drawable.fondopregunta_));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Morado));
                break;
            case 2:
                fondo.setImageResource(R.drawable.fondocolor2);
                radioImage.setImageResource(R.drawable.fondocolor_2);
                preguntaImage.setImageResource((R.drawable.fondopregunta_2));
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Azul));
                break;


        }

        preguntaText.setTextColor(Color.rgb(255, 255, 255));

        //respuesta1.setBackgroundColor(Color.rgb(255,255,255));
        respuesta1.setTextColor(Color.rgb(255, 255, 255));
        respuesta1.setButtonTintList(colorStateList);

        // respuesta2.setBackgroundColor(Color.rgb(255,255,255));
        respuesta2.setTextColor(Color.rgb(255, 255, 255));
        respuesta2.setButtonTintList(colorStateList);

        // respuesta3.setBackgroundColor(Color.rgb(255,255,255));
        respuesta3.setTextColor(Color.rgb(255, 255, 255));
        respuesta3.setButtonTintList(colorStateList);

        //respuesta4.setBackgroundColor(Color.rgb(255,255,255));
        respuesta4.setTextColor(Color.rgb(255, 255, 255));
        respuesta4.setButtonTintList(colorStateList);

    }

    public boolean comprobarAcierto(Integer posicion) {
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "preguntas", null, 1);
        SQLiteDatabase BaseDeDatos = rankingAdmin.getWritableDatabase();
        boolean acierto = false;
        int validacionCorrecta = 0;
        String[] preguntaPreguntada = new String[]{Integer.toString(pregunta_elegida) + "Pr"};
        String respuesta = "respuesta" + Integer.toString(posicion) + "Cierta";
        Cursor fila1 = BaseDeDatos.rawQuery("select " + respuesta + " from preguntas where numeroPregunta=?", preguntaPreguntada);
        if (fila1.moveToFirst()) {
            validacionCorrecta = fila1.getInt(0);
            if (validacionCorrecta == 1) {
              //  Toast.makeText(this, "Acertada", Toast.LENGTH_SHORT).show();
                acierto = true;
            } else {
              // Toast.makeText(this, "Fallada", Toast.LENGTH_SHORT).show();
            }
        } else {
           // Toast.makeText(this, "Pregunta no Encontrada", Toast.LENGTH_SHORT).show();
        }

        BaseDeDatos.close();


        return acierto;


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
        }fila1.close();
        BaseDeDatos.close();
        return estadoPreguntada;

    }
}




