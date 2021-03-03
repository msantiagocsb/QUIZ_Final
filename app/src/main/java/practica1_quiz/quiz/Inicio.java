package practica1_quiz.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    private ArrayList<Pregunta> Preguntas_preparadas= new ArrayList<>();
    private Partida partida,partidaux;
    private Usuario usuario;
    private int numeroDePreguntas=5;
    private ImageView logoQuiz;
    private Musica musica;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio1);
        Usuario invitado= new Usuario("Invitado","DefalulyNick");
        crearPreguntas(invitado);

        guardarPreguntasBD(partida);//guarda las preguntas en la bbd
        cargarMusica(partida);//inicializa la musica

        borrarPreguntasText();//borra las preguntas con solo text del array de preguntas
        logoQuiz=(ImageView) findViewById(R.id.imagenQuiz);
        logoQuiz.setImageResource(R.drawable.logo3);






        Button boton_Crear = (Button) findViewById(R.id.botonCrearUsuario);
        boton_Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), CrearUsuario.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();

            }
        });

        Button boton_Invitado = (Button) findViewById(R.id.botonInvitado);
        boton_Invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //partida.setUsuario(usuario);
                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada

                Intent intent = new Intent(v.getContext(), MainActivity.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();
            }
        });

        Button boton_Usuario = (Button) findViewById(R.id.botonInicioUsuario);
        boton_Usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                Intent intent = new Intent(v.getContext(), ListaUsuarios.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();
            }
        });

    }

    public void onBackPressed() {//cuandos se quiera ir a atras vuelve a hacer lo mismo para cerrar la app
        partida.getMusicaDePartida().parar();
        startActivity(new Intent(getBaseContext(), Inicio.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();

    }
    /*public void onStop(){
        super.onStop();
        partida.getMusicaDePartida().parar();
        finish();
    }*/




    public void crearPreguntas(Usuario usuario) {//crea todas las preguntas
        int preguntasañadidas = 0;
        Pregunta pregunta = new Pregunta("¿En que año llegó Cristobal Colon a América?", "1942", false, "1432", false, "1482", false, "1492", true);
        Preguntas_preparadas.add(pregunta);
        preguntasañadidas++;
        Pregunta pregunta1 = new Pregunta("¿Cúal es el nombre del actual rey de España?", "Felipe VI", true, "Felipe V", false, "Felipe IV", false, "Carlos III", false);
        Preguntas_preparadas.add(pregunta1);
        preguntasañadidas++;
        Pregunta pregunta2 = new Pregunta("¿Cuales la película mas taquillera de la historia?", "Titanic", false, "Avatar", false, "Avengers: Endgame", true, "Fast and Furious 7", false);
        Preguntas_preparadas.add(pregunta2);
        preguntasañadidas++;
        Pregunta pregunta3 = new Pregunta("En el golf ¿Cómo se llama el césped alrededor del hoyo? ", "Fore", false, "Bunker", false, "Tee", false, "Green", true);
        Preguntas_preparadas.add(pregunta3);
        preguntasañadidas++;
        Pregunta pregunta0 = new Pregunta("¿Cómo era la sangre de las criaturas de la pelicula de 1979 'Alien, el octavo pasajero'?", "Dulce", false, "Luminosa", false, "Corrosiva", true, "Transparente", false);
        Preguntas_preparadas.add(pregunta0);
        preguntasañadidas++;
        Pregunta pregunta11 = new Pregunta("El estrecho de Ormuz separa los paises de...", "Alemania y Francia", false, "Estados Unidos y México", false, "Australia y Nueva Zelanda", false, "Omán e Irán", true);
        Preguntas_preparadas.add(pregunta11);
        preguntasañadidas++;
        Pregunta pregunta12 = new Pregunta("¿Qué frutas se secan para convertirse en pasas?", "Las frambuesas", false, "Las uvas", true, "Las manzanas", false, "Los arándanos", false);
        Preguntas_preparadas.add(pregunta12);
        preguntasañadidas++;
        Pregunta pregunta13 = new Pregunta("El Sol se compone principalmente de hidrógeno y...", "Agua", false, "Sodio", false, "Helio", true, "Oxígeno", false);
        Preguntas_preparadas.add(pregunta13);
        preguntasañadidas++;
        Pregunta pregunta14 = new Pregunta("¿Cual es el edificio mas alto del mundo?", "One World Trade Center", false, "Burj Khalifa", true, "Taipei 101", false, "Torres Petronas", false);
        Preguntas_preparadas.add(pregunta14);
        preguntasañadidas++;
        //Pregunta pregunta15=new Pregunta("Completa la conocida frase del guión de Star Wars: 'El miedo conduce a la ira…", "…la ira conduce al Lado Oscuro",false,"…la ira conduce al odio y el odio conduce al sufrimiento'",true,"...y la ira te hace ser más fuerte'",false,"...y la ira te hace ser más fuerte'",false);
        // Preguntas_preparadas.add(pregunta15);
        Pregunta pregunta16 = new Pregunta("¿Cómo se llama la cárcel mágica de la saga ‘Harry Potter’?", "Karnego", false, "Azkaban", true, "Rakletto", false, "Arrazen", false);
        Preguntas_preparadas.add(pregunta16);
        preguntasañadidas++;
        Pregunta pregunta17 = new Pregunta("¿Cúal es el ingrediente principal en la elaboración del ron?", "Caña de Azucar", true, "Cebada", false, "Uva", false, "Manzana", false);
        Preguntas_preparadas.add(pregunta17);
        preguntasañadidas++;
        Pregunta pregunta18 = new Pregunta("El río mas largo del mundo es…", "Nilo", false, "Yangtsé", false, "Mississippi", false, "Amazonas", true);
        Preguntas_preparadas.add(pregunta18);
        preguntasañadidas++;
        Pregunta pregunta19 = new Pregunta("¿De qué país es el futbolista Zlatan Ibrahimović?", "Suecia", true, "Suiza", false, "Alemania", false, "Noruega", false);
        Preguntas_preparadas.add(pregunta19);
        preguntasañadidas++;
        Pregunta pregunta20 = new Pregunta("¿Qué era el Concorde?", "Un avión de pasajeros supersónico", true, "Tren bala", false, "Cohete espacial", false, "Un helicóptero", false);
        Preguntas_preparadas.add(pregunta20);
        preguntasañadidas++;
        Pregunta pregunta21 = new Pregunta("¿Cuál es la nacionalidad de Pablo Neruda?", "Española", false, "Chilena", true, "Colombiana", false, "Mexicana", false);
        Preguntas_preparadas.add(pregunta21);
        preguntasañadidas++;
        Pregunta pregunta22 = new Pregunta("El triángulo que tiene sus tres lados iguales ¿Cómo se llama?", "Isósceles", false, "Equilátero", true, "Escaleno", false, "Rectángulo", false);
        Preguntas_preparadas.add(pregunta22);
        preguntasañadidas++;
        Pregunta pregunta23 = new Pregunta("¿Quíen escribió la saga de libros de ‘El señor de los Anillos’?", "R.R.Martin", false, "J.R.R. Tolkin", true, "J.K. Rowling", false, "E. L. James", false);
        Preguntas_preparadas.add(pregunta23);
        preguntasañadidas++;
        Pregunta pregunta24 = new Pregunta("¿Cómo se llama el caballo de Gandalf, personaje de la película del señor de los anillos?", "Sombragris", true, "Pies de Fuego", false, "Trancos", false, "Hoja de viento", false);
        Preguntas_preparadas.add(pregunta24);
        preguntasañadidas++;


        //Preguntas con imagenes
        Pregunta pregunta104 = new Pregunta("¿A qué país pertenece esta bandera?", "Dinamarca", true, "Suecia", false, "Suiza", false, "Finlandia", false, "Dinamarca");
        Preguntas_preparadas.add(pregunta104);
        Pregunta pregunta105 = new Pregunta("¿Como se llama esta conocida nave de Star wars?", "AT-AT", false, "Caza Imperial", false, "Halcon Milenario", true, "R2D2", false, "StarWars");
        Preguntas_preparadas.add(pregunta105);
        Pregunta pregunta106 = new Pregunta("¿Qué rio pasa por esta ciudad?", "Rin", false, "Volga", false, "Támesis", false, "Danibio", true, "Budapest");
        Preguntas_preparadas.add(pregunta106);
        Pregunta pregunta107 = new Pregunta("¿En que ciudad encontramos este monumento?", "Roma", false, "Florencia", false, "Paris", true, "Niza", false, "Paris");
        Preguntas_preparadas.add(pregunta107);
        Pregunta pregunta108 = new Pregunta("¿A que organismo internacional pertenece esta bandera?", "UNESCO", false, "ONU", true, "OMS", false, "FMI", false, "ONU");
        Preguntas_preparadas.add(pregunta108);
        Pregunta pregunta109 = new Pregunta("¿A que pelicula pertenece este personaje?", "SuperMan", false, "Wonder Woman", false, "The Advergers", true, "DeadPool", false, "Capitan_America");
        Preguntas_preparadas.add(pregunta109);
        Pregunta pregunta100 = new Pregunta("En la conocida serie Juego de tronos, ¿Cómo se llama este personaje?", "Melisandre", false, "Daenerys Targaryen", false, "Brienne de Tarth", false, "Cersei Lannister", true, "Cersei");
        Preguntas_preparadas.add(pregunta100);
        Pregunta pregunta110 = new Pregunta("¿Quién pintó este cuadro?", "Diego Velazquez", false, "Miguel Ángel", false, "Leonardo Da vinci", true, "El Greco", false, "cena");
        Preguntas_preparadas.add(pregunta110);
        Pregunta pregunta111 = new Pregunta("¿En qué país podemos encontrar este monumento?", "Arabía Saudí", false, "Turquía", false, "India", true, "Tailandia", false, "tajMahal");
        Preguntas_preparadas.add(pregunta111);
        Pregunta pregunta112 = new Pregunta("¿En qué museo podemos encontrar este cuadro?", "Museo Reina Sofía, Madrid", true, "Museo del prado, Madrid", false, "Museo Louvre, Paris", false, "Museo Moma, Nueva York", false, "gernica");
        Preguntas_preparadas.add(pregunta112);
        Pregunta pregunta113 = new Pregunta("¿Cúal es la capital de este país?", "Sydney", false, "Melbourne", false, "Camberra", true, "Perth", false, "australia");
        Preguntas_preparadas.add(pregunta113);
        Pregunta pregunta114 = new Pregunta("¿A qué país pertenece esta bandera?", "Austria", true, "Hungría", false, "República Checa", false, "Polonia", false, "austria");
        Preguntas_preparadas.add(pregunta114);
        Pregunta pregunta115 = new Pregunta("¿Cómo se llama este personaje de Star wars?", "Darth Sidious", false, "Darth Vader", false, "Jar Jar Binks", false, "Darth Maul", true, "maul");
        Preguntas_preparadas.add(pregunta115);
        Pregunta pregunta116 = new Pregunta("¿Quíen es este personaje de la saga The Avergers?", "Venom", false, "Hulk", false, "Thanos", true, "Ultron", false, "thanos");
        Preguntas_preparadas.add(pregunta116);
        Pregunta pregunta117 = new Pregunta("¿Cuál es la bandera de Alemania?", "alemania", true, "austria", false, "belgica", false, "rcheca", false, true);
        Preguntas_preparadas.add(pregunta117);
        Pregunta pregunta118 = new Pregunta("¿Cuál es la bandera de Republica Checa?", "belgica", false, "austria", false, "rcheca", true, "alemania", false, true);
        Preguntas_preparadas.add(pregunta118);
        Pregunta pregunta119 = new Pregunta("¿Cuál de los siguientes personajes de StarWars es Jar Jar Binks?", "jarjar", true, "jaba", false, "chewbaca", false, "yoda", false, true);
        Preguntas_preparadas.add(pregunta119);
        Pregunta pregunta120 = new Pregunta("¿Cuál de los siguientes animales es de la familia de los delfines?", "ballena", false, "beluga", false, "orca", true, "tiburon", false, true);
        Preguntas_preparadas.add(pregunta120);
        Pregunta pregunta121 = new Pregunta("De estas imagenes, ¿cuál corresponde al personaje de la Pelicula Star Wars Ovi-Wan Kenobi?", "lando", false, "oviwan", true, "luke", false, "hansolo", false, true);
        Preguntas_preparadas.add(pregunta121);
        Pregunta pregunta122 = new Pregunta("¿Cúal de estos escudos corresponde a la casa de el personaje Draco Malfoy de saga de peliculas 'Harry Potter'?", "gryffindor", false, "hufflepuff", false, "ravenclaw", false, "slytherin", true, true);
        Preguntas_preparadas.add(pregunta122);
        Pregunta pregunta123 = new Pregunta("De los siguientes animales, ¿A cuál de ellos se le denomina 'facoquero'?", "macaco", false, "jabali", false, "facoquero", true, "nu", false, true);
        Preguntas_preparadas.add(pregunta123);
        Pregunta pregunta124 = new Pregunta("¿Cúal de estos vocalistas perteneció a la mitica banda de rock 'Queen'?", "mick", false, "freddy", true, "ledd", false, "acdc", false, true);
        Preguntas_preparadas.add(pregunta123);


        //Usuario real= partida.getUsuario();//coge el usuario de la primera partida creada
        partida = new Partida(Preguntas_preparadas, usuario, numeroDePreguntas);//crea la partida y sobre escribe la anterior
        partida.setNumerodePreguntasConText(preguntasañadidas);
    }

    public void cargarMusica(Partida partida){

            partida.setMusicaDePartida(new Musica(3,Inicio.this));
            partida.getMusicaDePartida().reproducirLoop();
            partida.setSonidoAcierto(new Musica(0,Inicio.this));
            partida.setSonidoFallo(new Musica(1,Inicio.this));
            partida.setSonidoTiempo(new Musica(2,Inicio.this));
            partida.setSonidoMejorPuntuacion(new Musica(4,Inicio.this));
            partida.setSonidoSinMejora(new Musica(5,Inicio.this));


        }





    public void guardarPreguntasBD(Partida partida) {
        PreguntasDbHelper preguntasAdmin = new PreguntasDbHelper(this, "preguntas", null, 1);
        SQLiteDatabase BaseDeDatos = preguntasAdmin.getWritableDatabase();

        for(int i=0;i<Preguntas_preparadas.size();i++) {
            if(Preguntas_preparadas.get(i).getRespuestaImagenes()==false&&Preguntas_preparadas.get(i).getHayImagen()==false) {//guarda las que no tienen imagenes

                String[] datos = new String[]{Preguntas_preparadas.get(i).getPregunta(), Preguntas_preparadas.get(i).getRespuestas().get(0).getRespuesta(), Preguntas_preparadas.get(i).getRespuestas().get(1).getRespuesta(), Preguntas_preparadas.get(i).getRespuestas().get(2).getRespuesta(),Preguntas_preparadas.get(i).getRespuestas().get(3).getRespuesta()};
                Integer[] validacion=new Integer[]{0,0,0,0};
               String[] posicion=new String[]{Integer.toString(i)+"Pr"};
                for(int j=0;j<4;j++){
                    if(Preguntas_preparadas.get(i).getRespuestas().get(j).getValidacion()==true){
                        validacion[j]=1;
                    }else{
                        validacion[j]=0;

                    }
                }
                ContentValues registro = new ContentValues();
                ContentValues registro2 = new ContentValues();

                Cursor fila = BaseDeDatos.rawQuery("select numeroPregunta from preguntas where numeroPregunta=?",posicion);
                registro.put("numeroPregunta",i+"Pr");
                registro2.put("numeroPregunta", i+"Pr");
                registro2.put("pregunta", datos[0]);
                registro2.put("respuesta1", datos[1]);
                registro2.put("respuesta1Cierta", validacion[0]);
                registro2.put("respuesta2", datos[2]);
                registro2.put("respuesta2Cierta", validacion[1]);
                registro2.put("respuesta3", datos[3]);
                registro2.put("respuesta3Cierta", validacion[2]);
                registro2.put("respuesta4", datos[4]);
                registro2.put("respuesta4Cierta", validacion[3]);
                registro2.put("preguntada",0);




                if (fila.moveToFirst()) {
                    BaseDeDatos.update("preguntas", registro, "numeroPregunta=?", posicion);
                } else {
                    BaseDeDatos.insert("preguntas", null, registro2);
                }
            }
        }

        BaseDeDatos.close();
    }
    public void borrarPreguntasText(){

        for(int i=0;i<partida.getPreguntas_partida().size();i++){
            if((partida.getPreguntas_partida().get(i).getRespuestaImagenes()==false)&&(partida.getPreguntas_partida().get(i).getHayImagen()==false)){
            partida.getPreguntas_partida().remove(i);}
        }
    }
}
