package practica1_quiz.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

public class RankingDbHelper extends SQLiteOpenHelper {

    public RankingDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory,version);
    }
   @Override
    public void onCreate(SQLiteDatabase BDRanking) {
        BDRanking.execSQL("create table ranking(nick text primary key, puntuacion int)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase BDRanking, int oldVersion, int newVersion) {

    }

}
