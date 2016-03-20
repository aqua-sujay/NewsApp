package app.com.example.sujay.newsapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.FileOutputStream;
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
        //store html file locally

        Document doc= Jsoup.connect(page).get();

                Log.d("webExtract", Jsoup.connect(page).ignoreContentType(true).followRedirects(true).header("Accept-Language", "pt-BR,pt;q=0.8").header("Accept-Encoding", "gzip,deflate,sdch") .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36").maxBodySize(0).timeout(23000).get().outerHtml());
  /*      .followRedirects(true)
            .ignoreContentType(true)
            .timeout(12000) // optional
            .header("Accept-Language", "pt-BR,pt;q=0.8") // missing
            .header("Accept-Encoding", "gzip,deflate,sdch") // missing
            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36") // missing
            .referrer("http://www.google.com") // optional
            .execute()
            */
        String host=Uri.parse(Uri.parse(page).getQueryParameter("url")).getHost();
        Log.d("webExtract","firstpart "+ host);
      //  Element img=doc.select("img[id="+"Intex Cloud Force With 5-Inch Display Launched at Rs. 4999"+"]").first();
        Element imgs=null;
        if(host.equals("www.ndtv.com"))
        {
            Log.d("webExtract", "in ndtv.com");
            imgs = doc.select("img[id=story_image_main]").first();
          // doc= Jsoup.parseBodyFragment(doc.getElementById("story_image_main").text());
            Log.d("webExtract"," text"+"  text"+doc.select("img[id=story_image_main]").text());
         //   Log.d("webExtract", "img tag "+imgs.tagName());
       //       imgsrc = imgs.attr("src");
            Log.d("webExtract", "src "+imgsrc);
        }
       else  if(host.equals("gadgets.ndtv.com")) {
          //  img = doc.getElementsByAttributeValue("id", "ContentPlaceHolder1_FullstoryCtrl_mainstoryimage").first();
            Log.d("webExtract", doc.body().toString());
            imgs = doc.getElementById("ContentPlaceHolder1_FullstoryCtrl_mainstoryimage");
            imgsrc = imgs.attr("src");
            Log.d("webExtract", "src "+imgsrc);
        }
       else if(host.equals("_profit.ndtv.com")) {
            imgs = doc.getElementsByAttributeValue("id", "ContentPlaceHolder1_FullstoryCtrl_mainstoryimage").first();
            imgsrc = imgs.attr("src");
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
