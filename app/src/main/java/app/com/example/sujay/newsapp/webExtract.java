package app.com.example.sujay.newsapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URL;


/**
 * Created by sujay on 17-03-2016.
 * getting fulllength content
 */

public class webExtract {
    public String ndtvImage(String page) throws IOException
    {
        String key="id",imgsrc=null;

        Document doc= Jsoup.connect(page).get();
        String host=Uri.parse(Uri.parse(page).getQueryParameter("url")).getHost();
        Log.d("webExtract","firstpart "+ host);
      //  Element img=doc.select("img[id="+"Intex Cloud Force With 5-Inch Display Launched at Rs. 4999"+"]").first();
        Element img=null;
        if(host.equals("_www.ndtv.com"))
        {
            Log.d("webExtract", "in ndtv.com");
            img = doc.getElementById("story_image_main");
              imgsrc = img.attr("src");
            Log.d("webExtract", "src "+imgsrc);
        }
       else  if(host.equals("gadgets.ndtv.com")) {
          //  img = doc.getElementsByAttributeValue("id", "ContentPlaceHolder1_FullstoryCtrl_mainstoryimage").first();
            img = doc.getElementById("ContentPlaceHolder1_FullstoryCtrl_mainstoryimage");
            imgsrc = img.attr("src");
            Log.d("webExtract", "src "+imgsrc);
        }
       else if(host.equals("_profit.ndtv.com")) {
            img = doc.getElementsByAttributeValue("id", "ContentPlaceHolder1_FullstoryCtrl_mainstoryimage").first();
            imgsrc = img.attr("src");
            Log.d("webExtract", "src "+imgsrc);
        }
        else
        {
            imgsrc = null;
        }
         //   String imgsrc = img.attr("src");





      //  Log.d("webExtract", "src "+imgsrc);
        return imgsrc;
    }


}
