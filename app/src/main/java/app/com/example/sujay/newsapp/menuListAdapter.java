package app.com.example.sujay.newsapp;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sujay on 11-01-2016.
 */
public class menuListAdapter extends BaseAdapter {
Context context;
    int[] menuPic;
    String[] menu;
    ArrayList<menuRow> menuList=new ArrayList<>();

    public menuListAdapter(Context context)
    {
        this.context=context;
        menuPic=new int[]{R.drawable.india,R.drawable.world,R.drawable.business,R.drawable.technology,R.drawable.entertainment,
                R.drawable.sports,R.drawable.science,R.drawable.health,R.drawable.entertainment};
        menu=context.getResources().getStringArray(R.array.MenuOptions);
        for(int i=0;i<menuPic.length;i++)
        {
            menuList.add(new menuRow(menuPic[i],menu[i]));
        }
    }

        public int getCount()
        {
            return menuList.size();
        }
        public Object getItem(int position)
        {
            return menuList.get(position);

        }
        public long getItemId(int position)
        {
            return position;

        }
        public View getView(int position,View convertView,ViewGroup parent)
        {
            //get color values from shared preferences
            SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(context);
       //  String color= sp.getString("color_details_title_key","BLACK");
         //   Log.d("shared",color);
            View row=convertView;
            myHolder holder=null;


            if(row==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.drawer_row_layout, parent, false);
                holder=new myHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(myHolder)row.getTag();
            }
            holder.title.setText(menuList.get(position).edition);
            holder.image.setImageResource(menuList.get(position).logo);
            //changing color from options
           holder.title.setTextColor(colorChooser.changeColor(sp.getString("color_nav_title_key", "BLACK")));
            holder.title.setBackgroundColor(colorChooser.changeColor(sp.getString("color_nav_background_key", "GRAY")));
           // holder.title.setBackgroundColor(colorChooser.changeColor());

            return row;
        }

        class myHolder
        {
            TextView title;
            ImageView image;
            public myHolder(View v)
            {
                title=(TextView)v.findViewById(R.id.menutitle);
                image=(ImageView)v.findViewById(R.id.menulogo);

            }

        }

    class menuRow
    {
        String edition;
        int logo;
        public menuRow(int logo,String edition)
        {
            this.edition=edition;
            this.logo=logo;
        }
    }

    }

