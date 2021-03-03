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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static practica1_quiz.quiz.Juego.Preguntas_preparadas;

public class Juego_Lista extends AppCompatActivity {

    TextView pregunta;
    Button respuesta1,respuesta2,respuesta3,respuesta4;
    private ListView respuestas;
    private int pregunta_elegida;
    private Partida partida;
    private CountDownTimer reloj;
    private ImageView fondo,preguntaImage,listaImage;
    private TextView preguntaText,numeropreguntaText;
    private boolean respondida;//variable booleana que vale true cuando se ha respondido la pregunta, y false si se ha pasado el tiempo y no se ha respondido nada

    private ProgressBar progreso;//variable que va aumentando el tiempo y disminuyendo el tamaño del circulo de tiempo
    private ArrayList<Pregunta> Preguntas_preparadas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_lista);

        partida = (Partida) Comunicador.getObjeto(partida);
        /*for(int i=0;i<partida.getPreguntas_partida().size();i++){
            Preguntas_preparadas.add(partida.getPreguntas_partida().get(i));
        }*/

        final TextView textView = (TextView)findViewById(R.id.text_numero_pregunta);
        String string_puntuacion=Integer.toString(partida.getNumero_pregunta()+1);
        textView.setText(string_puntuacion);
        if(partida.getUsuario().getDarkMode()==false){
            cambiarColor();
        }


        pregunta_elegida = (int) (Math.random() * partida.getNumerodePreguntasConText());
        while (estaPreguntada()==true) {//solo coge las preguntas que no estén preguntadas
            pregunta_elegida = (int) (Math.random() * partida.getNumerodePreguntasConText());
        }
        // Preguntas_preparadas.get(pregunta_elegida).setPreguntada(true);//para que no repita la pregunta

        preguntadaTrue();
        respuestas = (ListView)findViewById(R.id.respuestas);
        respuestas.getCount();

        pregunta = (TextView) findViewById(R.id.Pregunta);

        respondida=false;

        progreso = findViewById(R.id.tiempo);//le dices donde tiene que aumentar o decrementar el tiempo
        progreso.setIndeterminate(false);


        hacerPregunta(respuestas);
        reloj = crearReloj();
       // reloj.start();
       // reserProgreso();
        respuestas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                switch (position){//position es una variable que viene dada por android studio y controla que opcion de la imagen view has elegido
                    case 0:
                        if(comprobarAcierto(1)==true){
                            parent.getChildAt(position).setBackgroundColor((Color.rgb(24,173,11)));
                            partida.setPuntuacion(partida.getPuntuacion()+5);
                            partida.setAcertadas(partida.getAcertadas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoAcierto().reproducir(Juego_Lista.this);//reproduce el sonido de acierto
                            }
                            mensajeAcierto();
                            siguientePregunta();
                        }else{
                            parent.getChildAt(position).setBackgroundColor(Color.rgb(237, 22, 22  ));
                            partida.setPuntuacion(partida.getPuntuacion()-2);
                            partida.setFalladas(partida.getFalladas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoFallo().reproducir(Juego_Lista.this);
                            }
                            new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                        }
                        respondida=true;
                        bloquearBotones();
                        reloj.cancel();//para el reloj para que no siga contando una vez repondido


                        break;
                    case 1:
                        if(comprobarAcierto(2)==true){
                            parent.getChildAt(position).setBackgroundColor((Color.rgb(24,173,11)));
                            partida.setPuntuacion(partida.getPuntuacion()+5);
                            partida.setAcertadas(partida.getAcertadas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoAcierto().reproducir(Juego_Lista.this);//reproduce el sonido de acierto
                            }
                            mensajeAcierto();
                            siguientePregunta();
                        }else{
                            parent.getChildAt(position).setBackgroundColor(Color.rgb(237, 22, 22  ));
                            partida.setPuntuacion(partida.getPuntuacion()-2);
                            partida.setFalladas(partida.getFalladas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoFallo().reproducir(Juego_Lista.this);
                            }
                            new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                        }
                        respondida=true;
                        bloquearBotones();
                        reloj.cancel();//para el reloj para que no siga contando una vez repondido
                        break;
                    case 2:
                        if(comprobarAcierto(3)==true){
                            parent.getChildAt(position).setBackgroundColor((Color.rgb(24,173,11)));
                            partida.setPuntuacion(partida.getPuntuacion()+5);
                            partida.setAcertadas(partida.getAcertadas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoAcierto().reproducir(Juego_Lista.this);//reproduce el sonido de acierto
                            }
                            mensajeAcierto();
                            siguientePregunta();
                        }else{
                            parent.getChildAt(position).setBackgroundColor(Color.rgb(237, 22, 22  ));
                            partida.setPuntuacion(partida.getPuntuacion()-2);
                            partida.setFalladas(partida.getFalladas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoFallo().reproducir(Juego_Lista.this);
                            }
                            new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                        }
                        respondida=true;
                        bloquearBotones();
                        reloj.cancel();//para el reloj para que no siga contando una vez repondido
                        break;
                    case 3:
                        if(comprobarAcierto(4)==true){
                            parent.getChildAt(position).setBackgroundColor((Color.rgb(24,173,11)));
                            partida.setPuntuacion(partida.getPuntuacion()+5);
                            partida.setAcertadas(partida.getAcertadas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoAcierto().reproducir(Juego_Lista.this);//reproduce el sonido de acierto
                            }
                            mensajeAcierto();
                            siguientePregunta();
                        }else{
                            parent.getChildAt(position).setBackgroundColor(Color.rgb(237, 22, 22  ));
                            partida.setPuntuacion(partida.getPuntuacion()-2);
                            partida.setFalladas(partida.getFalladas()+1);
                            if(partida.getEfectosPreguntas()) {
                                partida.getSonidoFallo().reproducir(Juego_Lista.this);
                            }
                            new DialogoFallo(partida).show(getSupportFragmentManager(), "Fallo");

                        }
                        respondida=true;
                        bloquearBotones();
                        reloj.cancel();//para el reloj para que no siga contando una vez repondido
                        break;
                }

            }

        });







    }
    @Override
    public void onBackPressed() {//para que no funcione el boton de atras mientras estas repondiendo

    }
    public int getCount() { return 4; }


    public void siguientePregunta() {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        public void run() {//para demorar el tiempo x millisegundos

            if ((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas()-1)) {
                Comunicador.setObjeto(partida);
                reloj.cancel();
                Intent intent = new Intent(Juego_Lista.this, Final.class);
                startActivity(intent);
                finish();


            }
            else if((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas()-2)){
                Comunicador.setObjeto(partida);
                partida.incrementarNumPregunta();
                reloj.cancel();
                int i = (int) (Math.random() * 4 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                switch (i) {
                    case 1:
                        Intent intent = new Intent(Juego_Lista.this, Juego_Basico.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:

                        Intent intent2 = new Intent(Juego_Lista.this, Juego_Radio.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case 3:
                        Intent intent3 = new Intent(Juego_Lista.this, Juego_Imagen.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case 4:
                        Intent intent5 = new Intent(Juego_Lista.this, Juego_Imagenes.class);
                        startActivity(intent5);
                        finish();
                        break;
                    default:
                        Toast.makeText(Juego_Lista.this, "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                        break;
                }
            }
                else{
                reloj.cancel();
                partida.incrementarNumPregunta();
                Comunicador.setObjeto(partida);

                int i = (int) (Math.random() * 4 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                switch (i) {
                    case 1:


                        Intent intent = new Intent(Juego_Lista.this, Juego_Basico.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:

                        Intent intent2 = new Intent(Juego_Lista.this, Juego_Radio.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case 3:
                        Intent intent3 = new Intent(Juego_Lista.this, Juego_Imagen.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case 4:
                        Intent intent4 = new Intent(Juego_Lista.this, Juego_Imagenes.class);
                        startActivity(intent4);
                        finish();
                        break;

                    default:
                        Toast.makeText(Juego_Lista.this, "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                        break;
                }
                //}
            }
        }
    }, 1000);//lo demoro 300 millisegundos para que se vea mejor el cambio de color de los botones


}


    public void hacerPregunta(ListView respuestas){
        pregunta.setText((buscarTexto("pregunta", 0)));


        //añade al string la 4 respuestas
        //se necesita usar ArrayAdapter clase de android studio para meter cada respuesta de las listView
        String[] arrayRespuestas = new String[]{(buscarTexto("respuesta", 1)),buscarTexto("respuesta", 2),buscarTexto("respuesta", 3),buscarTexto("respuesta", 4)};
        //creamos el adaptador para  meter cada respuesta en su sitio
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayRespuestas);

        respuestas.setAdapter(adapter);//crea  la lista con lo valores

    }
    public void mensajeTiempo(){//para que salga el toast de correcto
        int toastDurationInMilliSeconds = 900;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.tiempo,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -235);
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
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -235);
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
                        partida.getSonidoTiempo().reproducir(Juego_Lista.this);
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
       respuestas.setEnabled(false);

    }
    public void cambiarColor(){


        fondo= (ImageView) findViewById(R.id.fondo);
        preguntaImage=(ImageView) findViewById(R.id.imageView2);
        listaImage=(ImageView)  findViewById(R.id.fondoListView);

        preguntaText=(TextView) findViewById(R.id.Pregunta);
        respuestas = (ListView) findViewById(R.id.respuestas);
        numeropreguntaText=(TextView) findViewById(R.id.text_numero_pregunta);


        preguntaImage.setImageResource((R.drawable.fondoblanco_imagenes));
        numeropreguntaText.setTextColor(Color.rgb(255,255,255));


        int i = (int) (Math.random() * 2+ 1);
        switch (i) {
            case 1:
                fondo.setImageResource(R.drawable.fondocolor);
                preguntaImage.setImageResource((R.drawable.fondopreguntalista_));
                listaImage.setImageResource(R.drawable.fondolista_);
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Morado));
                break;
            case 2:
                fondo.setImageResource(R.drawable.fondocolor2);
                preguntaImage.setImageResource((R.drawable.fondopreguntalista_2));
                listaImage.setImageResource(R.drawable.fondolista_2);
                numeropreguntaText.setBackgroundColor(numeropreguntaText.getContext().getResources().getColor(R.color.Azul));
                break;



        }

        preguntaText.setTextColor(Color.rgb(255,255,255));

        respuestas.setBackgroundColor(Color.TRANSPARENT);
        String[] arrayRespuestas = new String[]{(buscarTexto("respuesta", 1)),buscarTexto("respuesta", 2),buscarTexto("respuesta", 3),buscarTexto("respuesta", 4)};
        List<String> respuestasList = new ArrayList<String>(Arrays.asList(arrayRespuestas));

        // creamos adaptador para cambiar color a la lista
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, respuestasList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);


                TextView tv = (TextView) view.findViewById(android.R.id.text1);


                tv.setTextColor(Color.rgb(255,255,255));

                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        respuestas.setAdapter(arrayAdapter);

        /*respuesta2.setBackgroundColor(Color.rgb(255,255,255));


        respuesta3.setBackgroundColor(Color.rgb(255,255,255));


        respuesta4.setBackgroundColor(Color.rgb(255,255,255));
*/








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
               // Toast.makeText(this, "Fallada", Toast.LENGTH_SHORT).show();
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
        }BaseDeDatos.close();
        fila1.close();
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
