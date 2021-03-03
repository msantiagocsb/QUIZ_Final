package practica1_quiz.quiz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class DialogoFallo extends DialogFragment {
    private Partida partida;

    public DialogoFallo(Partida partida) {
        this.partida=partida;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog(partida);
    }

    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog(final Partida partida) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        boolean modoNegro=partida.getUsuario().getDarkMode();

        builder.setCancelable(false);
        if(!modoNegro){

        }
        builder.setTitle((Html.fromHtml(" <font color='#FF0000'>¡Te has equivocado!</font>")));
        builder
                .setMessage("¿Quieres continuar la partida? ¿o empezar una nueva?")
                .setPositiveButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if ((partida.getNumero_pregunta() >= partida.getNumeroDePreguntas()-1)) {
                                    Comunicador.setObjeto(partida);
                                    Intent intent = new Intent(getContext(), Final.class);
                                    startActivity(intent);

                                } else {
                                    Comunicador.setObjeto(partida);
                                    partida.incrementarNumPregunta();
                                    int i = (int) (Math.random() * 5 + 1);//lo uso para que de manera aleatoria salga un tipo de pregunta u otra
                                    switch (i) {
                                        case 1:


                                            Intent intent = new Intent(getContext(), Juego_Basico.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                            break;
                                        case 2:

                                            Intent intent2 = new Intent(getContext(), Juego_Radio.class);
                                            startActivity(intent2);
                                            getActivity().finish();
                                            break;
                                        case 3:
                                            Intent intent3 = new Intent(getContext(), Juego_Imagen.class);
                                            startActivity(intent3);
                                            getActivity().finish();
                                            break;
                                        case 4:
                                            Intent intent4 = new Intent(getContext(), Juego_Lista.class);
                                            startActivity(intent4);
                                            getActivity().finish();
                                            break;
                                        case 5:
                                            Intent intent5 = new Intent(getContext(), Juego_Lista.class);
                                            startActivity(intent5);
                                            getActivity().finish();
                                            break;
                                        default:
                                            Toast.makeText(getContext(), "No se ha podido iniciar la partida", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    //}
                                }

                            }
                        })
                .setNegativeButton("Empezar de nuevo",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(getContext(), Juego.class);
                                startActivity(intent);
                            }
                        });

        return builder.create();
    }


}

