package practica1_quiz.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PreguntasDbHelper extends SQLiteOpenHelper {

    public PreguntasDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase BDRanking) {
        BDRanking.execSQL("create table preguntas(numeroPregunta text primary key, pregunta text, respuesta1 text, respuesta1Cierta int, respuesta2 text, respuesta2Cierta int,respuesta3 text, respuesta3Cierta int,respuesta4 text, respuesta4Cierta int, preguntada int)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase BDRanking, int oldVersion, int newVersion) {

    }

}

