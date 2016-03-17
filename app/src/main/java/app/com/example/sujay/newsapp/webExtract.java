package app.com.example.sujay.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;


/**
 * Created by sujay on 17-03-2016.
 * getting fulllength content
 */

public class webExtract {
    public String ndtvImage(String page,String key,String value) throws IOException
    {
        Document doc= Jsoup.connect(page).get();
        Element img=doc.select("img[title="+"Intex Cloud Force With 5-Inch Display Launched at Rs. 4999"+"]").first();

            String imgsrc = img.attr("src");

        Log.d("webExtract", "src"+imgsrc);
        return imgsrc;
    }


}
