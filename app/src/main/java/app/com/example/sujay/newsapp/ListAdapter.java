package app.com.example.sujay.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sujay on 10-01-2016.
 */
public class ListAdapter extends BaseAdapter {

        Context context;
      public  sqLiteHelper helper=null;
        SQLiteDatabase db=null;
      public Cursor cursorAdapter=null;
        Cursor cursorTitle=null;
      //  feeds feed=null;

        String title=null;
        String description=null;
        String source=null;
        String date=null;
        String link=null;
        byte[] image=null;
String ctopic;
  // Cursor cursorAdapter=db.query("NEWS_TABLE", new String[]{"TITLE", "SOURCE", "DATE", "DESCRIPTION", "LINK", "IMAGE"}, null, null, null, null, "RowTime DESC");
       // ContentValues cvalue=new ContentValues();

        public ListAdapter(Context context,String ctopic)
        {

            this.context=context;
this.ctopic=ctopic;

             selectQuery();
        }


        public void selectQuery(){
            helper=new sqLiteHelper(context);
            db=helper.getWritableDatabase();
            Log.d("xml","listadapter"+ctopic);
            cursorAdapter=db.query("NEWS_TABLE", new String[]{"TITLE", "SOURCE", "DATE", "DESCRIPTION", "LINK", "IMAGE"}, "TOPIC=?", new String[]{ctopic}, null, null, "RowTime DESC");
        }

        public int getCount()
        {
            Log.d("testcount","get count: "+ cursorAdapter.getCount());
            return  cursorAdapter.getCount();

            //return list.size();
        }
        public Object getItem(int position)
        {
            cursorAdapter.moveToNext();
            return  cursorAdapter.moveToPosition(position);
            // return list.get(position);
        }
        public long getItemId(int position)
        {
            return position;

        }
        public View getView(int position,View convertView,ViewGroup parent)
        {
            View row=convertView;
            myHolder holder;
            Bitmap bmp=null;

            if(row==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.list_row, parent, false);
                holder=new myHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(myHolder)row.getTag();
            }

          //  holder.image.setImageBitmap(bmp);
            //setting list from database
            cursorAdapter.moveToPosition(position);

            //changing color from options
            SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(context);
            holder.title.setTextColor(colorChooser.changeColor(sp.getString("color_list_title_key", "BLACK")));
            holder.title.setBackgroundColor(colorChooser.changeColor(sp.getString("color_list_background_key", "GRAY")));

            holder.title.setText(cursorAdapter.getString(cursorAdapter.getColumnIndex("TITLE")));
          //  holder.source.setText(cursorAdapter.getString(cursorAdapter.getColumnIndex("SOURCE")));
         //   holder.description.setText(cursorAdapter.getString(cursorAdapter.getColumnIndex("DATE")));
           // holder.date.setText(cursorAdapter.getString(cursorAdapter.getColumnIndex("DESCRIPTION")));
         //   holder.link.setText(cursorAdapter.getString(cursorAdapter.getColumnIndex("LINK")));

            try {
                bmp = BitmapFactory.decodeByteArray(cursorAdapter.getBlob(cursorAdapter.getColumnIndex("IMAGE")), 0, cursorAdapter.getBlob(cursorAdapter.getColumnIndex("IMAGE")).length);
            }catch(Exception e)
            {
                Log.d("test","bmp file: "+e.toString());
            }
            holder.image.setImageBitmap(bmp);


            Log.d("test", "title from db " + cursorAdapter.getString(cursorAdapter.getColumnIndex("TITLE")));
            Log.d("test", "link from db " + cursorAdapter.getString(cursorAdapter.getColumnIndex("LINK")));

            return row;
        }
        class myHolder
        {
            TextView title;
            TextView description;
            TextView source;
            TextView date;
            TextView link;
            ImageView image;
            public myHolder(View v)
            {
                title=(TextView)v.findViewById(R.id.titletext);
                description=(TextView)v.findViewById(R.id.descriptiontext);
                source=(TextView)v.findViewById(R.id.sourcetext);
                date=(TextView)v.findViewById(R.id.datetext);
                link=(TextView)v.findViewById(R.id.linktext);
                image=(ImageView)v.findViewById(R.id.imageView);
            }
        }

}
