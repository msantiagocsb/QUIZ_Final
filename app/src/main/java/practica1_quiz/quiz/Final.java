package practica1_quiz.quiz;

import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Final extends AppCompatActivity {
    private Partida partida,partidaux;
    private String retroalimentacion;
    private ListView ranking;
    private TextView textView1,tuText,puntuacionesText,puntuacionText,acertadasText,falladasText,retroalimentacionText,rankingText;
    private Button boton_Jugar,boton_Salir,boton_MenuPrincipal;
    private int maxVolume=50;
    private ArrayList<String> rankingLocal=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        partida = (Partida) Comunicador.getObjeto(partida);//carga  la partida para la pregunta
        //aqui ponemos el nombre del jugador
        ponerPreguntasCero();
        for(int i=0;i<5;i++) {

           rankingLocal.add(i+1+"º Sin datos");

        }
        if(partida.getMusicaPreguntas()==false){
            if(partida.getMusicaMenu()==true){
                partida.setMusicaDePartida(new Musica(3,Final.this));
                partida.getMusicaDePartida().reproducirLoop();
            }
        }else{
            partida.getMusicaDePartida().parar();
            if(partida.getMusicaMenu()==false){
                partida.getMusicaDePartida().parar();
            }else{
                partida.setMusicaDePartida(new Musica(3,Final.this));
                partida.getMusicaDePartida().reproducirLoop();
                float log1=(float)(Math.log(maxVolume-0)/Math.log(maxVolume));
                partida.getMusicaDePartida().setVolume((log1),(log1));
            }

        }

        final TextView textView = (TextView)findViewById(R.id.nombre);
        if(partida.getNombre().equals("Nombre")){//si no pone nombre sale por defecto solo "TU" esta puesto por defecto, por eso esta vacio dentro del if

        }else{
        textView.setText(partida.getUsuario().getNick()+", tu");}

        //aqui ponemos la puncion obtenida
         textView1 = (TextView)findViewById(R.id.puntuacion);
        //Ranking.añadirPartidaRanking(partida);
        guardarRanking(partida);
        cargarRanking();




        ranking=(ListView)findViewById(R.id.listRanking);

        String[] rankingActual= { rankingLocal.get(0),rankingLocal.get(1),rankingLocal.get(2),rankingLocal.get(3),rankingLocal.get(4)};



        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,rankingActual);

        ranking.setAdapter(adaptador);




        textView1.setText(Integer.toString(partida.getPuntuacion())+"/"+(partida.getNumeroDePreguntas()*5));

        //aqui buscamos el texto para poner la retroalimentacion
        final TextView textView2 = (TextView)findViewById(R.id.retroalimentacion);

        final TextView textView3 = (TextView)findViewById(R.id.acertadas);
        final TextView textView4 = (TextView)findViewById(R.id.falladas);
        textView3.setText("Acertadas: "+Integer.toString(partida.getAcertadas()));
        textView4.setText("Falladas: "+Integer.toString(partida.getFalladas()));
        float aciertosFallos=(float)((partida.getPuntuacion())*10/(partida.getNumeroDePreguntas()*5));//comprueba la proporcion de puntos obtenidos y los maximos posibles
        //mensajes de retroalimentacion segun  la proporcion de puntos obtenidos y los maximos posibles
        if(aciertosFallos<2){
            retroalimentacion="¡Partida Horrible!";
        } else if((aciertosFallos>=2)&&(aciertosFallos<4)){
            retroalimentacion="Puedes hacerlo mejor...";
        } else if(((aciertosFallos>=4)&&(aciertosFallos<6))){
            retroalimentacion="¡Sigue asi!";
        } else if((aciertosFallos>=6)&&(aciertosFallos<8)){
            retroalimentacion="¡Muy buena!";
        }else if(((aciertosFallos>=8)&&(aciertosFallos<10))){
            retroalimentacion="¡De lo Mejor!";
        } else if(aciertosFallos==10){
            retroalimentacion="¡Legendario!";
        }


        textView2.setText(retroalimentacion);

        if(partida.getUsuario().getDarkMode()==false){
            cambiarColor();
        }







         boton_Jugar = (Button) findViewById(R.id.botonJugar);
        boton_Jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Partida nuevaPartida=new  Partida(partida);
                nuevaPartida.setNumerodePreguntasConText(partida.getNumerodePreguntasConText());
                Comunicador.setObjeto(nuevaPartida);
                Intent intent = new Intent(v.getContext(), Juego.class);
                startActivity(intent);
                finish();


            }
        });
       boton_Salir = (Button) findViewById(R.id.botonSalir);
        boton_Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//cierra la app
                partida.getMusicaDePartida().parar();
                Intent intent = new Intent(Final.this, Inicio.class);
                startActivity(intent);
                finish();

            }
        });
         boton_MenuPrincipal = (Button) findViewById(R.id.volverMenu);
        boton_MenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//vuelve al menú principal y como alli crea el objeto partida pues... no se lo paso aunque podría
                Partida nuevaPartida=new  Partida(partida);
                nuevaPartida.setNumerodePreguntasConText(partida.getNumerodePreguntasConText());
                Comunicador.setObjeto(nuevaPartida);
                Intent intent = new Intent(Final.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
    @Override
    public void onBackPressed() {//para que no funcione el boton de atras cuando has terminado la partida

    }
    public void cambiarColor(){
        ImageView fondo=(ImageView)findViewById(R.id.fondo);
            tuText=(TextView)findViewById(R.id.nombre);
            puntuacionesText=(TextView)findViewById(R.id.mensaje2);
            puntuacionText=(TextView)findViewById(R.id.puntuacion);
            acertadasText=(TextView)findViewById(R.id.acertadas);
            falladasText=(TextView)findViewById(R.id.falladas);
            retroalimentacionText=(TextView)findViewById(R.id.retroalimentacion);
            rankingText=(TextView)findViewById(R.id.textRanking);


            boton_Jugar=(Button) findViewById(R.id.botonJugar);
             boton_Salir=(Button) findViewById(R.id.botonSalir);
        boton_MenuPrincipal=(Button) findViewById(R.id.volverMenu);

        fondo.setImageResource(R.drawable.fondocolorfinal);
        tuText.setTextColor(Color.rgb(255,255,255));
       puntuacionText.setTextColor(Color.rgb(255,255,255));
       puntuacionesText.setTextColor(Color.rgb(255,255,255));
        acertadasText.setTextColor(Color.rgb(255,255,255));
        falladasText.setTextColor(Color.rgb(255,255,255));
        retroalimentacionText.setTextColor(Color.rgb(255,255,255));
        rankingText.setTextColor(Color.rgb(255,255,255));


        boton_Jugar.setBackgroundColor(Color.rgb(255,255,255));
        boton_Jugar.setTextColor(boton_Jugar.getContext().getResources().getColor(R.color.colorPrimary));

        boton_Salir.setBackgroundColor(Color.rgb(255,255,255));
        boton_Salir.setTextColor(boton_Salir.getContext().getResources().getColor(R.color.colorPrimary));

        boton_MenuPrincipal.setBackgroundColor(Color.rgb(255,255,255));
        boton_MenuPrincipal.setTextColor(boton_MenuPrincipal.getContext().getResources().getColor(R.color.colorPrimary));

        ranking.setBackgroundColor(Color.TRANSPARENT);





    }
    public void guardarRanking(Partida partida) {
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "ranking", null, 1);
        SQLiteDatabase BaseDeDatos = rankingAdmin.getWritableDatabase();
        String[] usario = new String[]{partida.getUsuario().getNick()};
        ContentValues registro = new ContentValues();
        ContentValues registro2 = new ContentValues();

        Cursor fila = BaseDeDatos.rawQuery("select nick from ranking where nick=?", usario);
        registro.put("puntuacion", partida.getPuntuacion());
        registro2.put("puntuacion", partida.getPuntuacion());
        registro2.put("nick", partida.getUsuario().getNick());



             if ((buscarValor(partida) == true)) {//solo actualiza la puntuacion si es mejor que la que está ya guardada
                    if (fila.moveToFirst()) {
                        BaseDeDatos.update("ranking", registro, "nick=?", usario);
                    } else {
                        BaseDeDatos.insert("ranking", null, registro2);
                    }


            }else{
               BaseDeDatos.insert("ranking", null, registro2);
        }
        //partida.getUsuario().setRecienRegistrado(false);
        BaseDeDatos.close();
    }


    public void cargarRanking(){
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "ranking",null,1);
         SQLiteDatabase BaseDeDatos= rankingAdmin.getWritableDatabase();


        Cursor fila= BaseDeDatos.query("ranking",null,null,null,null,null,"puntuacion DESC","5");


        int i=0;
        while(i<5){
            if(i==0){
                if(fila.moveToFirst()){
                    rankingLocal.set(i,i+1+"º "+fila.getString(0)+" --> "+fila.getString(1));
                    i++;
                }else{
                    break;
                }
            }else{
                if(fila.moveToNext()){
                    if(i==1){
                        rankingLocal.set(i,i+1+"º "+fila.getString(0)+" --> "+fila.getString(1));
                        i++;
                    }else if(i==2){
                        rankingLocal.set(i,i+1+"º "+fila.getString(0)+" --> "+fila.getString(1));
                        i++;
                    }else if(i==3){
                        rankingLocal.set(i,i+1+"º "+fila.getString(0)+" --> "+fila.getString(1));
                        i++;
                    }else if(i==4){
                        rankingLocal.set(i,i+1+"º "+fila.getString(0)+" --> "+fila.getString(1));
                        i++;
                    }
                }else{
                    break;
                }
            }


        }


        BaseDeDatos.close();
    }
    public boolean buscarValor(Partida partida){
        RankingDbHelper rankingAdmin = new RankingDbHelper(this, "ranking",null,1);
        SQLiteDatabase BaseDeDatos= rankingAdmin.getWritableDatabase();
        boolean mejor=false;
        int valorViejo=-99;
        String[] usario = new String[]{partida.getUsuario().getNick().toString()};
            int valorNuevo=partida.getPuntuacion();
            Cursor fila1= BaseDeDatos.rawQuery("select puntuacion from ranking where nick=?",usario);
            if(fila1.moveToFirst()) {
                valorViejo=fila1.getInt(0);
                if (valorViejo < valorNuevo) {
                    Toast.makeText(this, "¡Nueva mejor Marca Personal!", Toast.LENGTH_SHORT).show();
                    if(partida.getEfectosPreguntas()==true) {
                        partida.getSonidoMejorPuntuacion().reproducir(Final.this);
                    }
                    mejor = true;
                }else{
                    Toast.makeText(this, "¡oh! No has mejorado :(", Toast.LENGTH_SHORT).show();
                    if(partida.getEfectosPreguntas()==true) {
                        partida.getSonidoSinMejora().reproducir(Final.this);
                    }
                }
            }else{
                Toast.makeText(this, "Aun no se tenian registros de este jugador", Toast.LENGTH_SHORT).show();
            }

            BaseDeDatos.close();


        return mejor;




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
