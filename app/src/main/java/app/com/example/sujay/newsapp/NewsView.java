package app.com.example.sujay.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

/**
 * Created by sujay on 11-01-2016.
 */
public class NewsView extends Fragment {
    View v=null;
    TextView title;
    TextView description;
    TextView source;
    TextView date;
    TextView link;
    ImageView image;


    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle instance)
    {


        v= inflater.inflate(R.layout.newsviewlayout,parent,false);
        title=(TextView)v.findViewById(R.id.titletext);
        description=(TextView)v.findViewById(R.id.descriptiontext);
        source=(TextView)v.findViewById(R.id.sourcetext);
        date=(TextView)v.findViewById(R.id.datetext);
        link=(TextView)v.findViewById(R.id.linktext);
        image=(ImageView)v.findViewById(R.id.imageView);
        //changing title color from options
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getActivity());
       title.setTextColor(colorChooser.changeColor(sp.getString("color_details_title_key", "BLACK")));
       title.setBackgroundColor(colorChooser.changeColor(sp.getString("color_details_title_background_key", "GRAY")));
        //changing description color from options
        description.setTextColor(colorChooser.changeColor(sp.getString("color_details_description_title_key", "BLACK")));
        description.setBackgroundColor(colorChooser.changeColor(sp.getString("color_details_description_background_key", "GRAY")));
        link.setBackgroundColor(colorChooser.changeColor(sp.getString("color_details_description_background_key", "GRAY")));
        image.setBackgroundColor(colorChooser.changeColor(sp.getString("color_details_description_background_key", "GRAY")));
        v.setBackgroundColor(colorChooser.changeColor(sp.getString("color_details_description_background_key", "GRAY")));

        link.setTextColor(Color.BLUE);

        //set details from bundle arguements avilable inside this object
        Bundle details=getArguments();
        title.setText(details.getString("title"));
        source.setText(details.getString("source"));
        date.setText(details.getString("date"));

        description.setText(details.getString("description"));
        String li="<a href='"+details.getString("link")+"'>LINK</a>";
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(li));

        Bitmap bmp=null;
        // if image is not stored
        if(details.getByteArray("image")!=null) {
            bmp = BitmapFactory.decodeByteArray(details.getByteArray("image"), 0, details.getByteArray("image").length);
            image.setImageBitmap(bmp);
        }


        return v;
    }


}
