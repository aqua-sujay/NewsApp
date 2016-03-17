package app.com.example.sujay.newsapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sujay on 10-01-2016.
 */
public class sqLiteHelper extends SQLiteOpenHelper {
    Context context=null;
    private static String DATABASE_NAME="GOOGLE_NEWS";
    private static String TABLE_NAME="NEWS_TABLE";
    private static   int DATABASE_VERSION=10;
    private   String ID="ID";
    private   String TITLE="TITLE";
    private   String DESCRIPTION="DESCRIPTION";
    private   String SOURCE="SOURCE";
    private   String IMAGE="IMAGE";
    private   String DATE="DATE";
    private   String LINK="LINK";
    private String TOPIC="TOPIC";
    String CREATE_TABLE="create table "+TABLE_NAME+" ("+ID+" integer primary key autoincrement,"+TITLE+" text,"+DESCRIPTION+" text,"+SOURCE+" varchar(200),"+IMAGE+" blob,"+DATE+" text,"+LINK+" text"+",RowTime "+"TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+TOPIC+" varchar(20));";
    String DROP_TABLE="drop table "+TABLE_NAME;
    SQLiteDatabase db=null;
    public sqLiteHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);

        } catch (SQLException s) {
            Log.d("test", "onCreate data" + s.toString());
        }
        this.db=db;
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        try{
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_TABLE);
        }catch(SQLException s)
        {
            Log.d("test",s.toString());
        }
    }


}
