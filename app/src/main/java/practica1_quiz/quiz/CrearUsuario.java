package practica1_quiz.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class CrearUsuario extends AppCompatActivity {
    private int numeroDePreguntas = 5;
    private ArrayList<Pregunta> Preguntas_preparadas = new ArrayList<>();
    private ImageView logoQuiz;

    private Partida partida;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuario);

        partida = (Partida) Comunicador.getObjeto(partida);
        logoQuiz=(ImageView) findViewById(R.id.imagenQuiz);
        logoQuiz.setImageResource(R.drawable.logo3);



        Button boton_Crear = (Button) findViewById(R.id.botonCrear);
        boton_Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nick = ((EditText) findViewById(R.id.edit_nick)).getText().toString();
                String contraseña = ((EditText) findViewById(R.id.edit_contraseña)).getText().toString();
                boolean existe=buscarUsuario(nick,contraseña);
                if(existe==false){
                Usuario u = new Usuario(nick, contraseña);
                Usuarios.añadirUsuarios(u);
                guardarUsuario(u);
                //int  numero_preguntas=partida.getNumeroDePreguntas();//coge el numero de preguntas por si acaso

                partida.setUsuario(u);
                Comunicador.setObjeto(partida);
                Intent intent = new Intent(v.getContext(), MainActivity.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                finish();}


            }
        });

        Button boton_Atras = (Button) findViewById(R.id.botonAtras);
        boton_Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                partida.getMusicaDePartida().parar();
                finish();
            }
        });



    }
    public void onBackPressed() {//para que no funcione el boton de atras mientras estas repondiendo
        Intent intent = new Intent(CrearUsuario.this, Inicio.class);//le dice a que actividad quiere entrar
        startActivity(intent);//intenta entrar en la actividad elegida
        partida.getMusicaDePartida().parar();
        finish();
    }

    public void guardarUsuario(Usuario u) {

        SharedPreferences preferencias = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();
        obj_editor.putString(u.getNick(), u.getContraseña());
        obj_editor.commit();
        obj_editor.putBoolean(u.getNick() + "b", u.getDarkMode());
        obj_editor.commit();
    }
    public boolean buscarUsuario(String NickIntroducido, String ContraseñaIntroducida) {
        SharedPreferences preferencias = getSharedPreferences("usuarios", Context.MODE_PRIVATE);

        String contraseña = preferencias.getString(NickIntroducido, "");
        boolean existe=false;
        //String contraseña = preferencias.getString(ContraseñaIntroducida, "");

        if (contraseña.length() != 0) {
            Toast.makeText(this, "Usuario ya registrado con ese nombre prueba con otro", Toast.LENGTH_SHORT).show();
            existe=true;
        } else  {
            Toast.makeText(this, "Registro con exito", Toast.LENGTH_SHORT).show();


        }
        return existe;
    }
}