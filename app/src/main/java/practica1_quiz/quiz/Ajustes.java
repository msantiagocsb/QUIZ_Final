package practica1_quiz.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class Ajustes extends AppCompatActivity {
    private Partida partida;
    private ArrayList<Pregunta> Preguntas_preparadas= new ArrayList<>();
    TextView titulo,bienvenida,nombre;     //desclaramos nuestroTEXTVIEW de nuestro LAYOUT
    private int numeroDePreguntas;
    private Spinner spiner;
    private ImageView fondo,imageSpinner;
    private TextView tituloText,bienvenidaText,nombreText,numeroPreguntasText;
    private Button boton_atras,boton_guardar;
    private Switch interruptorDarkMode,interruptorMusicaMenu,interruptorMusicaPreguntas,interruptorEfectos;

    public static Context getAppContext(){//devuelve el contexto de la actividad
        return MainActivity.getAppContext();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {//captura si uno de los intent tiene valor EXIT cuando se crea la actividad y finaliza

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        partida = (Partida) Comunicador.getObjeto(partida);



        nombreText=(TextView) findViewById(R.id.infoNombre);
        //nombreText.setText(partida.getNombre());
        interruptorDarkMode = (Switch) findViewById(R.id.darkMode);
        interruptorMusicaMenu = (Switch) findViewById(R.id.musicaMenu);
        interruptorMusicaPreguntas = (Switch) findViewById(R.id.MusicaPreguntas);
        interruptorEfectos=(Switch) findViewById(R.id.efectosPreguntas);

        if(partida.getUsuario().getDarkMode()==false){
            cambiarColor();
        }
        if(partida.getUsuario().getDarkMode()==true){
            interruptorDarkMode.setChecked(true);
        }else if(partida.getUsuario().getDarkMode()==false){
            interruptorDarkMode.setChecked(false);
        }
        if(partida.getMusicaMenu()==true){
            interruptorMusicaMenu.setChecked(true);
        }else if(partida.getMusicaMenu()==false){
            interruptorMusicaMenu.setChecked(false);
        }
        if(partida.getMusicaPreguntas()==true){
            interruptorMusicaPreguntas.setChecked(true);
        }else if(partida.getMusicaPreguntas()==false){
            interruptorMusicaPreguntas.setChecked(false);
        }
        if(partida.getEfectosPreguntas()==true){
            interruptorEfectos.setChecked(true);
        }else if(partida.getEfectosPreguntas()==false){
            interruptorEfectos.setChecked(false);
        }



        boton_guardar = (Button) findViewById(R.id.cambiar);
        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla

                if (interruptorDarkMode.isChecked()){
                    partida.getUsuario().setDarkMode(true);


                }else {
                    partida.getUsuario().setDarkMode(false);

                }

                if(interruptorEfectos.isChecked()){
                    partida.setEfectosPreguntas(true);
                }else{
                    partida.setEfectosPreguntas(false);
                }

                if(interruptorMusicaMenu.isChecked()){
                    partida.setMusicaMenu(true);
                }else{
                    partida.setMusicaMenu(false);
                }

                if(interruptorMusicaPreguntas.isChecked()){
                    partida.setMusicaPreguntas(true);
                }else{
                    partida.setMusicaPreguntas(false);
                }
                guardarUsuario(partida.getUsuario());


                partida.setNumero_pregunta(numeroDePreguntas);
                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), MainActivity.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();

            }
        });
        boton_atras  = (Button) findViewById(R.id.atras);
        boton_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla



                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), MainActivity.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();

            }
        });


        spiner= (Spinner) findViewById(R.id.spinnerNumPreguntas);
        ArrayList<String> datos= new ArrayList<String>();
        datos.add("5");
        datos.add("10");
        datos.add("15");
        datos.add("20");
        // ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,datos);//usamos el ArrayAdapter para poner todas las opciones al spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.rgb(255,255,255));

                //tv.setBackgroundColor();

                // Generate ListView Item using TextView
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        spiner.setAdapter(arrayAdapter);




        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        numeroDePreguntas=5;

                        //llamarToast(5);
                        break;
                    case 1:
                       numeroDePreguntas=10;


                        break;
                    case 2:
                        numeroDePreguntas=15;


                        break;
                    case 3:
                        numeroDePreguntas=20;



                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onBackPressed() {//para que no funcione el boton de atras cuando has terminado la partida

    }
    public void guardarUsuario(Usuario u){
        if(u.getNick().equals("Invitado")){

        }else{
            SharedPreferences preferencias= getSharedPreferences("usuarios",Context.MODE_PRIVATE);
            SharedPreferences.Editor obj_editor = preferencias.edit();
            obj_editor.putBoolean(u.getNick()+"b",u.getDarkMode());
            obj_editor.commit();}
    }
    public void cambiarColor(){
        fondo=(ImageView) findViewById(R.id.fondo);
        imageSpinner=(ImageView)findViewById(R.id.imageSpiner);
        tituloText=(TextView) findViewById(R.id.ajustes);
        numeroPreguntasText=(TextView) findViewById(R.id.numeroPregunta);
        spiner= (Spinner) findViewById(R.id.spinnerNumPreguntas);
        interruptorDarkMode = (Switch) findViewById(R.id.darkMode);
        interruptorMusicaMenu = (Switch) findViewById(R.id.musicaMenu);
        interruptorMusicaPreguntas = (Switch) findViewById(R.id.MusicaPreguntas);
        interruptorEfectos=(Switch) findViewById(R.id.efectosPreguntas);
        boton_atras=(Button) findViewById(R.id.atras);
        boton_guardar=(Button) findViewById(R.id.cambiar);



        ArrayList<String> datos= new ArrayList<String>();
        datos.add("5");
        datos.add("10");
        datos.add("15");
        datos.add("20");
        // ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,datos);//usamos el ArrayAdapter para poner todas las opciones al spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                tv.setTextColor(Color.rgb(255, 255, 255));


                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        spiner.setAdapter(arrayAdapter);

        fondo.setImageResource(R.drawable.fondocolorajustes);
        imageSpinner.setImageResource(R.drawable.fondospinner);
        tituloText.setTextColor(Color.rgb(255, 255, 255));
        numeroPreguntasText.setTextColor(Color.rgb(255, 255, 255));
        interruptorDarkMode.setTextColor(Color.rgb(255, 255, 255));
        interruptorEfectos.setTextColor(Color.rgb(255, 255, 255));
        interruptorMusicaMenu.setTextColor(Color.rgb(255, 255, 255));
        interruptorMusicaPreguntas.setTextColor(Color.rgb(255, 255, 255));

        boton_atras.setBackgroundColor(Color.rgb(255, 255, 255));
        boton_atras.setTextColor(boton_atras.getContext().getResources().getColor(R.color.colorPrimary));

        boton_guardar.setBackgroundColor(Color.rgb(255, 255, 255));
        boton_guardar.setTextColor(boton_guardar.getContext().getResources().getColor(R.color.colorPrimary));







    }
}
