package app.com.example.sujay.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.GpsStatus;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button bt;
    ListView list,dlist;
    ListAdapter listAdapter;
    BackgroundActivity backgroundActivity=null;
    listenerHelper listenerHelper;
    DrawerLayout drawerLayout;
    menuListAdapter menuListAdapter;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    ImageView dlistimage;
    ProgressBar pb;
    TextView loadtext;
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    URL url;
   public String topic="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);

     /*   try {
           // url = new URL("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=n&output=rss");

        }catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        */
        setContentView(R.layout.activity_main);
pb=(ProgressBar)findViewById(R.id.progressBar);
        loadtext=(TextView)findViewById(R.id.loadtext);
        //bt=(Button)finViewById(R.id.button);
        Intent intent=getIntent();

        try {

            if( intent.getExtras()!=null ) {
                //called when u choose edition from navigation drawer
                Log.d("xml","intent not null"+intent.getStringExtra("url")+intent.getStringExtra("topic"));
                if(intent.getStringExtra("url")!=null) {
                    url = new URL(intent.getStringExtra("url"));
                    topic = intent.getStringExtra("topic");
                }
                else
                {
                    Log.d("shared", "in main intent.else "+sp.getString("default_edition_key", "India"));
                    topic=sp.getString("default_edition_key", "India");
                    url=findURL(topic);

                }

            }
            else {
                //look here/*
            //    if(Build.VERSION.SDK_INT<Build.VERSION_CODES.ECLAIR) {
             //       url = new URL("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=n&output=rss");
             //       topic = "india";
            //    }
        /*        else{   */
                // else(Build.VERSION.SDK_INT>Build.VERSION_CODES.ECLAIR){
                Log.d("shared", "in main"+sp.getString("default_edition_key", "India"));
                topic = sp.getString("default_edition_key", "India");
                String u;
                url=findURL(topic);




            }

          //  Log.d("xml",url.toString());
        }catch(MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        netDetector detector=new netDetector(this);
        list=(ListView)findViewById(R.id.listView);
        //set news list divider color
         list.setDivider(new ColorDrawable(colorChooser.changeColor(sp.getString("color_list_divider_key","WHITE"))));
         list.setDividerHeight(1);
        Log.d("xml","main "+topic);
        listAdapter=new ListAdapter(this,topic);
        listenerHelper=new listenerHelper(this,topic);
        list.setOnItemClickListener(listenerHelper);        //feed click listener
        list.setAdapter(listAdapter);
        //navigation drawer
        drawerLayout=(DrawerLayout)findViewById(R.id.menudrawer);
        dlist=(ListView)findViewById(R.id.dlistid);
        //set navmenu divider color
        dlist.setDivider(new ColorDrawable(colorChooser.changeColor(sp.getString("color_nav_divider_key","WHITE"))));
        dlist.setDividerHeight(1);
      //  dlistimage=(ImageView)findViewById(R.id.drawerlistimage);
       // dlist.addHeaderView(dlistimage);
        menuListAdapter=new menuListAdapter(this);
        dlist.setAdapter(menuListAdapter);
        dlist.setOnItemClickListener(listenerHelper);           //menu click listner

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(topic);
        //toolbar.setNavigationIcon(R.drawable.ic_action_collapse);
        //toolbar.setBackgroundColor(android.R.color.holo_orange_light);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_closed){
            public void onDrawerOpened(View v)
            {
                super.onDrawerOpened(v);
                toggle.syncState();
               // getActionBar().setTitle("Choose Edition");
                            }
            public void onDrawerClosed(View v)
            {
                super.onDrawerClosed(v);
                toggle.syncState();
            }
        };

        drawerLayout.setDrawerListener(toggle);
        //check if net is on
        if(detector.isConnected()) {
            Toast.makeText(this, "NetConnected successfully", Toast.LENGTH_LONG).show();
            backgroundActivity=new BackgroundActivity(url,topic,this);
            backgroundActivity.execute( );
            Log.d("test", "background activity stART");

        }
        else
        {
            Toast.makeText(this, "Net not connected", Toast.LENGTH_LONG).show();
        }



       // listenerHelper listener=new listenerHelper(this);

   //    bt.setOnClickListener(listener);
    }


    public URL findURL(String topic) throws MalformedURLException,IOException
    {
        String u;
        switch (topic) {
            //case 1-india, case 2-world,case 3- business,case 4- technology,case 5-entertainment,case 6-sports,case 7-science health

            case "India":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=n&output=rss";
                url = new URL(u);
                break;
            case "World":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=w&output=rss";
                url = new URL(u);
                break;
            case "Business":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=b&output=rss";
                url = new URL(u);
                ;
                break;
            case "Technology":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=tc&output=rss";
                url = new URL(u);
                break;
            case "Entertainment":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=e&output=rss";
                url = new URL(u);
                break;
            case "Sports":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=s&output=rss";
                url = new URL(u);
                break;
            case "Science":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=snc&output=rss";
                url = new URL(u);
                break;
            case "Health":
                u = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&topic=m&output=rss";
                url = new URL(u);
                break;

        }
        return url;
    }
}
