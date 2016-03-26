package app.com.example.sujay.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;


public class settingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
     //   SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getActivity());
       String value= sp.getString(key,"default");
        Toast.makeText(getActivity(), "changeed" + value, Toast.LENGTH_LONG).show();
        switch(key)
        {
            case "delete_posts_older_than_key":
                sqLiteHelper helper=new sqLiteHelper(getActivity());
                SQLiteDatabase db=helper.getWritableDatabase();



                java.util.Date date= new java.util.Date();
                new Timestamp(date.getTime());
                long sec=24*60*60*1000;
                long day=Integer.parseInt(sp.getString("delete_posts_older_than_key", "5"));
                Log.d("shared", "time " + date.getTime() + "TIMESTAMP-" + new Timestamp(date.getTime() - (sec * day)).toString());

              db.delete("NEWS_TABLE", "RowTime" + "<=?", new String[]{ new Timestamp(date.getTime()).toString()});
                Cursor cursortest=db.query("NEWS_TABLE", new String[]{"DATE"}, "RowTime<="+"?", new String[]{new Timestamp(date.getTime()-(sec*day)).toString()}, null, null, null);

                while(cursortest.moveToNext())
                {
                    Log.d("shared",cursortest.getString(cursortest.getColumnIndex("DATE")));
                }



                break;
        }
    }

}
