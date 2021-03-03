package practica1_quiz.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Partida partida;
    private ArrayList<Pregunta> Preguntas_preparadas= new ArrayList<>();
    TextView titulo,bienvenida,nombre;     //desclaramos nuestroTEXTVIEW de nuestro LAYOUT
   private int numeroDePreguntas;
    private Spinner spiner;
    private ImageView fondo,imageSpinner,fotoPerfil;
    private TextView tituloText,bienvenidaText,nombreText,numeroPreguntasText;
    private Button boton_Jugar,boton_Salir,boton_Ajustes,boton_Musica,boton_DarkMode,boton_foto;


    public static Context getAppContext(){//devuelve el contexto de la actividad
        return MainActivity.getAppContext();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {//captura si uno de los intent tiene valor EXIT cuando se crea la actividad y finaliza

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        partida = (Partida) Comunicador.getObjeto(partida);


        numeroDePreguntas=partida.getNumeroDePreguntas();



         //interruptorDarkMode = (Switch) findViewById(R.id.darkMode);
        nombreText=(TextView) findViewById(R.id.infoNombre);
        nombreText.setText(partida.getNombre());

        if(partida.getUsuario().getDarkMode()==false) {
            cambiarColorInical(partida);
        }


        if(partida.getMusicaMenu()==false){
            partida.getMusicaDePartida().parar();
        }else{
            if(partida.getMusicaDePartida().getReproduciendo()==true){

            }else {
                partida.getMusicaDePartida().parar();
                partida.setMusicaDePartida(new Musica(3, MainActivity.this));
                partida.getMusicaDePartida().reproducirLoop();
            }
        }



         boton_Jugar = (Button) findViewById(R.id.botonJugar);
        boton_Jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                guardarUsuario(partida.getUsuario());

                if(partida.getMusicaPreguntas()==false){
                    partida.getMusicaDePartida().parar();}
                    else{
                    partida.getMusicaDePartida().parar();
                    partida.setMusicaDePartida(new Musica(6,MainActivity.this));
                    partida.getMusicaDePartida().reproducirLoop();
                }


                partida.setNumero_pregunta(numeroDePreguntas);
                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), Juego.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();

            }
        });
        boton_Salir = (Button) findViewById(R.id.botonSalir);
        boton_Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla
            guardarUsuario(partida.getUsuario());
                partida.getMusicaDePartida().parar();
                finish();

            }
        });
        boton_foto = (Button) findViewById(R.id.botonFoto);
        boton_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla


                partida.setNumero_pregunta(numeroDePreguntas);
                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), HacerFoto.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();



            }
        });
        boton_Ajustes = (Button) findViewById(R.id.botonAjustes);
        boton_Ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla


                partida.setNumero_pregunta(numeroDePreguntas);
                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), Ajustes.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();



            }
        });
        boton_DarkMode = (Button) findViewById(R.id.modoOscuro);
        boton_DarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla

                    cambiarColor(partida);
                    guardarUsuario(partida.getUsuario());
                }


        });
        boton_Musica = (Button) findViewById(R.id.musica);
        boton_Musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//esto se hace para salir de la app sin que haga cosas raras se podría poner finish(); pero falla

                if (partida.getMusicaDePartida().getReproduciendo() == true) {
                    partida.getMusicaDePartida().parar();
                    partida.setMusicaMenu(false);
                    partida.setMusicaPreguntas(false);
                    partida.setEfectosPreguntas(false);
                } else {
                    partida.setMusicaDePartida(new Musica(3,MainActivity.this));
                    partida.getMusicaDePartida().reproducirLoop();
                    partida.setMusicaMenu(true);
                    partida.setMusicaPreguntas(true);
                    partida.setEfectosPreguntas(true);
                }
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
                View view = super.getView(position, convertView, parent);


                TextView tv = (TextView) view.findViewById(android.R.id.text1);


                tv.setTextColor(Color.rgb(255,255,255));


                return view;
            }
        };

        spiner.setAdapter(arrayAdapter);




        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        partida.setNumeroDePreguntas(5);

                        //llamarToast(5);
                        break;
                    case 1:
                        partida.setNumeroDePreguntas(10);
                        llamarToast(10);

                        break;
                    case 2:
                        partida.setNumeroDePreguntas(15);
                        llamarToast(15);

                        break;
                    case 3:
                        partida.setNumeroDePreguntas(20);
                        llamarToast(20);


                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void llamarToast(Integer i){
        Toast.makeText(this, "Pr"+partida.getNumeroDePreguntas(), Toast.LENGTH_SHORT).show();
    }
    public void llamarToast1(Integer i){
        Toast.makeText(this, "Nr"+partida.getNombre(), Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {//cuandos se quiera ir a atras vuelve a hacer lo mismo para cerrar la app
        partida.getMusicaDePartida().parar();
        finish();

    }





    public void guardarUsuario(Usuario u){
        if(u.getNick().equals("Invitado")){

        }else{
        SharedPreferences preferencias= getSharedPreferences("usuarios",Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();
        obj_editor.putBoolean(u.getNick()+"b",u.getDarkMode());
        obj_editor.commit();}
    }




    @SuppressLint("ResourceAsColor")
    public void cambiarColor(Partida partida){
        fondo= (ImageView) findViewById(R.id.fondo);
        tituloText=(TextView) findViewById(R.id.titulo);
        bienvenidaText=(TextView) findViewById(R.id.Bienvenida);
        numeroPreguntasText=(TextView) findViewById(R.id.numeroPregunta);
        nombreText=(TextView) findViewById(R.id.infoNombre);
        boton_Jugar = (Button) findViewById(R.id.botonJugar);
        boton_Salir = (Button) findViewById(R.id.botonSalir);
        boton_Ajustes=(Button) findViewById(R.id.botonAjustes);
        boton_DarkMode=(Button) findViewById(R.id.modoOscuro);
        boton_Musica=(Button) findViewById(R.id.musica);
        spiner= (Spinner) findViewById(R.id.spinnerNumPreguntas);
        imageSpinner=(ImageView) findViewById(R.id.imageSpiner);
        boton_foto = (Button) findViewById(R.id.botonFoto);

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

        if(partida.getUsuario().getDarkMode()==true) {

            fondo.setImageResource(R.drawable.fondocolorjuego);
            tituloText.setTextColor(Color.rgb(255, 255, 255));
            bienvenidaText.setTextColor(Color.rgb(255, 255, 255));
            nombreText.setTextColor(Color.rgb(255, 255, 255));
            // interruptorDarkMode.setTextColor(tituloText.getContext().getResources().getColor(R.color.Azul));
            numeroPreguntasText.setTextColor(Color.rgb(255, 255, 255));
            //spiner.setBackgroundColor(nombreText.getContext().getResources().getColor(R.color.Mostaza));
            boton_Jugar.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Jugar.setTextColor(boton_Jugar.getContext().getResources().getColor(R.color.colorPrimary));
            imageSpinner.setImageResource(R.drawable.fondospinner);

            boton_Salir.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Salir.setTextColor(boton_Salir.getContext().getResources().getColor(R.color.colorPrimary));

            boton_Ajustes.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Ajustes.setTextColor(boton_Ajustes.getContext().getResources().getColor(R.color.colorPrimary));

            boton_DarkMode.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_DarkMode.setTextColor(boton_DarkMode.getContext().getResources().getColor(R.color.colorPrimary));

            boton_Musica.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Musica.setTextColor(boton_Musica.getContext().getResources().getColor(R.color.colorPrimary));

            boton_foto.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_foto.setTextColor(boton_foto.getContext().getResources().getColor(R.color.colorPrimary));
            partida.getUsuario().setDarkMode(false);
        }
        else{
            fondo.setImageResource(R.drawable.fondooscuro);
            tituloText.setTextColor(Color.rgb(255, 255, 255));
            bienvenidaText.setTextColor(Color.rgb(255, 255, 255));
            nombreText.setTextColor(Color.rgb(255, 255, 255));
            // interruptorDarkMode.setTextColor(tituloText.getContext().getResources().getColor(R.color.Azul));
            numeroPreguntasText.setTextColor(Color.rgb(255, 255, 255));
            //spiner.setBackgroundColor(nombreText.getContext().getResources().getColor(R.color.Mostaza));
            boton_Jugar.setBackgroundColor(boton_Jugar.getContext().getResources().getColor(R.color.colorPrimary));
            boton_Jugar.setTextColor(Color.rgb(255, 255, 255));
            imageSpinner.setImageResource(R.drawable.fondospineroscuro);

            boton_Salir.setBackgroundColor(boton_Salir.getContext().getResources().getColor(R.color.colorPrimary));
            boton_Salir.setTextColor(Color.rgb(255, 255, 255));

            boton_Ajustes.setBackgroundColor(boton_Ajustes.getContext().getResources().getColor(R.color.colorPrimary));
            boton_Ajustes.setTextColor(Color.rgb(255, 255, 255));

            boton_DarkMode.setBackgroundColor(boton_DarkMode.getContext().getResources().getColor(R.color.colorPrimary));
            boton_DarkMode.setTextColor(Color.rgb(255, 255, 255));

            boton_Musica.setBackgroundColor(boton_Musica.getContext().getResources().getColor(R.color.colorPrimary));
            boton_Musica.setTextColor(Color.rgb(255, 255, 255));

            boton_foto.setBackgroundColor(boton_foto.getContext().getResources().getColor(R.color.colorPrimary));
            boton_foto.setTextColor(Color.rgb(255, 255, 255));
            partida.getUsuario().setDarkMode(true);
        }

    }
    public void cambiarColorInical(Partida partida){
        fondo= (ImageView) findViewById(R.id.fondo);
        tituloText=(TextView) findViewById(R.id.titulo);
        bienvenidaText=(TextView) findViewById(R.id.Bienvenida);
        numeroPreguntasText=(TextView) findViewById(R.id.numeroPregunta);
        nombreText=(TextView) findViewById(R.id.infoNombre);
        boton_Jugar = (Button) findViewById(R.id.botonJugar);
        boton_Salir = (Button) findViewById(R.id.botonSalir);
        boton_Ajustes=(Button) findViewById(R.id.botonAjustes);
        boton_DarkMode=(Button) findViewById(R.id.modoOscuro);
        boton_Musica=(Button) findViewById(R.id.musica);
        spiner= (Spinner) findViewById(R.id.spinnerNumPreguntas);
        imageSpinner=(ImageView) findViewById(R.id.imageSpiner);
        boton_foto = (Button) findViewById(R.id.botonFoto);


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


            fondo.setImageResource(R.drawable.fondocolorjuego);
            tituloText.setTextColor(Color.rgb(255, 255, 255));
            bienvenidaText.setTextColor(Color.rgb(255, 255, 255));
            nombreText.setTextColor(Color.rgb(255, 255, 255));
            // interruptorDarkMode.setTextColor(tituloText.getContext().getResources().getColor(R.color.Azul));
            numeroPreguntasText.setTextColor(Color.rgb(255, 255, 255));
            //spiner.setBackgroundColor(nombreText.getContext().getResources().getColor(R.color.Mostaza));
            boton_Jugar.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Jugar.setTextColor(boton_Jugar.getContext().getResources().getColor(R.color.colorPrimary));
            imageSpinner.setImageResource(R.drawable.fondospinner);

            boton_Salir.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Salir.setTextColor(boton_Salir.getContext().getResources().getColor(R.color.colorPrimary));

            boton_Ajustes.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Ajustes.setTextColor(boton_Ajustes.getContext().getResources().getColor(R.color.colorPrimary));

            boton_DarkMode.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_DarkMode.setTextColor(boton_DarkMode.getContext().getResources().getColor(R.color.colorPrimary));

            boton_Musica.setBackgroundColor(Color.rgb(255, 255, 255));
            boton_Musica.setTextColor(boton_Musica.getContext().getResources().getColor(R.color.colorPrimary));

        boton_foto.setBackgroundColor(Color.rgb(255, 255, 255));
        boton_foto.setTextColor(boton_foto.getContext().getResources().getColor(R.color.colorPrimary));


    }
}

