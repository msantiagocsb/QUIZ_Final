package practica1_quiz.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity {
    private Partida partida;
    private ListView listaDeUsuarios;
    private boolean poderseguir=false;//variable que controla si se pude iniciar partida o no.
    private ArrayList<Pregunta> Preguntas_preparadas= new ArrayList<>();
    private int numeroDePreguntas=5;
    private ImageView logoQuiz;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_usuarios);

        partida = (Partida) Comunicador.getObjeto(partida);
        logoQuiz=(ImageView) findViewById(R.id.imagenQuiz);
        logoQuiz.setImageResource(R.drawable.logo3);






        Button boton_Crear = (Button) findViewById(R.id.botonIniciar);
        boton_Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = ((EditText) findViewById(R.id.edit_Nick)).getText().toString();
                String contraseña = ((EditText) findViewById(R.id.edit_contraseña)).getText().toString();
                buscarUsuario(nick,contraseña);

                if(poderseguir) {
                    Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada
                    Intent intent = new Intent(v.getContext(), MainActivity.class);//le dice a que actividad quiere entrar
                    startActivity(intent);//intenta entrar en la actividad elegida
                    finish();
                }
            }
        });


        Button boton_Usuario = (Button) findViewById(R.id.botonAtras);
        boton_Usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Inicio.class);//le dice a que actividad quiere entrar
                startActivity(intent);//intenta entrar en la actividad elegida
                partida.getMusicaDePartida().parar();
                finish();
            }
        });

    }
    public void onBackPressed() {//para que no funcione el boton de atras mientras estas repondiendo
        Intent intent = new Intent(ListaUsuarios.this, Inicio.class);//le dice a que actividad quiere entrar
        startActivity(intent);//intenta entrar en la actividad elegida
        partida.getMusicaDePartida().parar();
        finish();
    }

    public void buscarUsuario(String NickIntroducido, String ContraseñaIntroducida) {
        SharedPreferences preferencias = getSharedPreferences("usuarios", Context.MODE_PRIVATE);

        String contraseña= preferencias.getString(NickIntroducido, "");
        //String contraseña = preferencias.getString(ContraseñaIntroducida, "");

        if (contraseña.length() == 0) {
            Toast.makeText(this, "No existe el usuario o las credenciales no coinciden", Toast.LENGTH_SHORT).show();
        } else if (contraseña.equals(ContraseñaIntroducida)) {
            Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show();
            Usuario u = new Usuario(NickIntroducido, ContraseñaIntroducida);
            Usuarios.añadirUsuarios(u);
            boolean darkModeGuardado = preferencias.getBoolean(u.getNick()+"b", false);
            u.setDarkMode(darkModeGuardado);
            poderseguir=true;
            partida.setUsuario(u);

        } else {
            Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
        }
    }


}
