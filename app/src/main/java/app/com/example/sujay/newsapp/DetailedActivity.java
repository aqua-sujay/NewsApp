package app.com.example.sujay.newsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.io.IOException;

/**
 * Created by sujay on 11-01-2016.
 */
public class DetailedActivity extends FragmentActivity{
    ViewPager pager;
    int pos;
    int total;
    sqLiteHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    String topic;
    String lin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent=getIntent();
        pos=intent.getIntExtra("position",0);
        total=intent.getIntExtra("total",10);
        topic=intent.getStringExtra("topic");
        pager=(ViewPager)findViewById(R.id.pager);
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager());
        helper=new sqLiteHelper(this);
        db=helper.getWritableDatabase();
        cursor=db.query("NEWS_TABLE", new String[]{"TITLE", "SOURCE", "DATE", "DESCRIPTION", "LINK", "IMAGE","TOPIC"},"TOPIC=?", new String[]{topic}, null, null, "RowTime DESC");

        pager.setAdapter(adapter);
        pager.setCurrentItem(pos);
        //adapter.setTotal(total);


    }

    class PagerAdapter extends FragmentPagerAdapter
    {
        // ArrayList<Fragment> fragmentlist=new ArrayList<>();



        public PagerAdapter(FragmentManager fm)

        {

            super(fm);
            // fragmentlist.add(new fragmentX());
            // fragmentlist.add(new fragmentY());
            //  fragmentlist.add(new fragmentZ());
        }
        public Fragment getItem(int position)
        {


            cursor.moveToNext();
            cursor.moveToPosition(position);
            NewsView fx=new NewsView();
            Bundle data=new Bundle();   //setting one set of data to show on viewpage
            data.putString("title",cursor.getString(cursor.getColumnIndex("TITLE")));
            data.putString("source",cursor.getString(cursor.getColumnIndex("SOURCE")));
            data.putString("date",cursor.getString(cursor.getColumnIndex("DATE")));
            data.putString("description",cursor.getString(cursor.getColumnIndex("DESCRIPTION")));

            data.putString("link", cursor.getString(cursor.getColumnIndex("LINK")));
            data.putByteArray("image", cursor.getBlob(cursor.getColumnIndex("IMAGE")));

            fx.setArguments(data);
            //to show link to website on post
            cursor.moveToPosition(position-1);
            lin=cursor.getString(cursor.getColumnIndex("LINK"));



            return fx;
        }
        public int getCount()
        {
            Log.d("test", cursor.getCount() + ":getCount");
            return  cursor.getCount();
        }

    }
    public void clickLink(View v)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        // intent.setType("text/html");
        intent.setData(Uri.parse(lin));
        if (intent.resolveActivity(this.getPackageManager())!=null)
        {
            startActivity(intent);
        }

    }


}
