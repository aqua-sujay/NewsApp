package app.com.example.sujay.newsapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sujay on 10-01-2016.
 */
public class listenerHelper implements View.OnClickListener,AdapterView.OnItemClickListener{
    URL urls;
    BackgroundActivity backgroundActivity;
    Context context=null;
    String topic;
  MainActivity mact;
    public listenerHelper(MainActivity mcontext,String topic)
    {
        this.context=(Context)mcontext;
        mact=mcontext;
        this.topic=topic;
    }
    //button click listener
    public void onClick(View v)
    {
        netDetector detector=new netDetector(context);
        if(detector.isConnected()) {
            Toast.makeText(context, "NetConnected successfully", Toast.LENGTH_LONG).show();
            // backgroundActivity=new BackgroundActivity(urls,topic);
          //  backgroundActivity.execute( context);

        }
        else
        {
            Toast.makeText(context, "Net not connected", Toast.LENGTH_LONG).show();
        }
    }

    public void onItemClick(AdapterView<?> adapter,View parent,int rid,long cid)
    {
        switch(adapter.getId())
        {
            case R.id.listView: {
                Toast.makeText(context, "position" + rid, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("position", rid);
                intent.putExtra("total",adapter.getCount());
                intent.putExtra("topic",topic);
                context.startActivity(intent);
               Log.d("shared", "count of adapter" + adapter.getCount());
                break;
            }
            case R.id.dlistid:{
                String[] option=context.getResources().getStringArray(R.array.MenuOptions);
                Toast.makeText(context, "position" + option[rid], Toast.LENGTH_LONG).show();
               try{
                   String u;
                   switch(rid)
                   {
                       //case 1-india, case 2-world,case 3- business,case 4- technology,case 5-entertainment,case 6-sports,case 7-science health
                       case 0:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=n&output=rss";
                           urls=new URL(u); inten(u,"india");break;
                       case 1:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=w&output=rss";
                           urls=new URL(u);  inten(u,"world");break;
                       case 2:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=b&output=rss";
                           urls=new URL(u);  inten(u,"business");break;
                       case 3:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=tc&output=rss";
                           urls=new URL(u);  inten(u,"technology");break;
                       case 4:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=e&output=rss";
                           urls=new URL(u);  inten(u,"entertainment");break;
                       case 5:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=s&output=rss";
                           urls=new URL(u); inten(u,"sports"); break;
                       case 6:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=snc&output=rss";
                           urls=new URL(u); inten(u,"science"); break;
                       case 7:u="https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=m&output=rss";
                           urls=new URL(u); inten(u,"health"); break;
                       case 8: callSettings();
                           break;
                   }
                   /* destroying old activity and recreating
                   new activity
                    */


               }catch(MalformedURLException m)
               {
                   m.printStackTrace();
               }

                break;
            }
        }
    }

    public void inten(String urls,String topic)
    {
        Intent intent=mact.getIntent();
        intent.putExtra("url",urls);
        intent.putExtra("topic",topic);
        mact.finish();
        mact.startActivity(intent);
    }
    public void callSettings()
    {
        Intent intent=new Intent(context,settings.class);
        context.startActivity(intent);

    }
}
