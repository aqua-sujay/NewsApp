package app.com.example.sujay.newsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by sujay on 10-01-2016.
 */
public  class BackgroundActivity extends AsyncTask<Void,HashMap<String,String>,Integer>
{
    Context context;
    URL url=null;
    String topic;

    String TITLE = "";
    String DATE = "";
    String SOURCE = "";
    String DESCRIPTION = "";
    String LINK = "";
    String SRC = "";
    String TOPIC=topic;
    byte[] imagearray=null;
    URLConnection con;
    //************************************************************************************************************

    public BackgroundActivity(URL url,String topic,Context con)
    {
        this.url=url;
        this.topic=topic;
        this.context=con;
        Log.d("shared","background url:"+url);
    }
    //*****************************************************************************************************
    public Integer doInBackground(Void... params)
    {
//this.context=cont[0];
  try{
      xmlParser();
  }catch(XmlPullParserException x)
  {
      x.printStackTrace();
  }catch(IOException i)
  {
      i.printStackTrace();
  }


       //  domParse(cont[0]);
       return 0;
    }
    public void domParse(Context cont)
    {
        Log.d("test", "DO IN background activity stART");
        this.context=cont;
        try {
            // url=new URL("https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss");
            // ContentValues cvalue = new ContentValues();
            //sqliteHelper helper = new sqliteHelper(context);
            //SQLiteDatabase db = helper.getWritableDatabase();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(url.openStream());
            org.w3c.dom.Element root = doc.getDocumentElement();

            String tag = root.getTagName();
            NamedNodeMap map;
            Log.d("test", tag);
            NodeList items = root.getElementsByTagName("item");  //returns all <item> tags
            Node curItem = null;
            NodeList childItems = null;
            Node curChild = null;
            //    NamedNodeMap attributes = null;
            //   Node curAttribute = null;
            int count = 0;

            HashMap<String,String> feedData=null;
            // feeds feed = null;
            for (int i =items.getLength()-1; i >=0; i--)            //loop through all item tags
            {
                feedData=new HashMap<>();
                // feed = new feeds();

                curItem = items.item(i);
                Log.d("test", curItem.getNodeName());
                childItems = curItem.getChildNodes();
                for (int j = 0; j < childItems.getLength(); j++) {
                    curChild = childItems.item(j);
                    if (curChild.getNodeName().equals("title")) {
                        TITLE = curChild.getTextContent();
                        feedData.put("title", TITLE);

                        Log.d("test", "title="+TITLE);
                    }
                    if (curChild.getNodeName().equalsIgnoreCase("pubdate")) {
                        DATE = curChild.getTextContent();
                        feedData.put("date", DATE);
                        //feed.setDate(DATE);
                        // Log.d("test", "Date="+DATE);
                    }
                    if (curChild.getNodeName().equalsIgnoreCase("description")) {
                        //  Log.d("test", "description");
                        String html = curChild.getTextContent();      //get html content inside description tag
                        org.jsoup.nodes.Document htmldoc;
                        htmldoc = Jsoup.parse(html);                  //parse string containing html
                        Log.d("test",htmldoc.toString());
                        org.jsoup.select.Elements htmele = htmldoc.getElementsByTag("img");
                        count = 0;
                        for (org.jsoup.nodes.Element e : htmele) {
                            if (count == 0) {
                                SRC = e.attr("src");
                                feedData.put("src", "http:" + SRC);
                                // feed.setImage(imagearray = loadImage("http:" + SRC));
                                Log.d("test", "imgage="+feedData.get("src"));
                            }
                            count++;
                        }
                        // extract description text from font tag
                        org.jsoup.select.Elements desce = htmldoc.getElementsByTag("font");
                        count = 0;
                        for (org.jsoup.nodes.Element e : desce) {
                            if (count == 4) {
                                SOURCE = e.text();
                                feedData.put("source", SOURCE);
                                // feed.setSource(SOURCE);
                                //  Log.d("test", SOURCE);
                            }
                            if (count == 5) {
                                DESCRIPTION = e.text();
                                feedData.put("description",DESCRIPTION);
                                // feed.setDescription(DESCRIPTION);
                                Log.d("test", "\n*********************************************************************" +
                                        "**************************************************************************************");
                            }

                            count++;
                        }

                    }
                    if (curChild.getNodeName().equalsIgnoreCase("link")) {
                        LINK = curChild.getTextContent();
                        LINK = LINK.substring(LINK.indexOf("url=") + 4);
                        feedData.put("link", LINK);
                        // feed.setLink(LINK);
                        Log.d("test", "link" + LINK);
                    }


                }
                int tcount=1;
                Log.d("test", "statement above publishProgress() "+tcount);tcount++;
                publishProgress(feedData);


            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
    public void onPostExecute(Integer i)
    {
        ((MainActivity)context).pb.setVisibility(View.GONE);
        ((MainActivity)context).loadtext.setVisibility(View.GONE);

    }
    public void onPreExecute()
    {
        ((MainActivity)context).pb.setVisibility(View.VISIBLE);
        ((MainActivity)context).loadtext.setVisibility(View.VISIBLE);

    }
    public void onProgressUpdate(HashMap<String,String>... feedData)
    {
            Log.d("xml", "update\n" + feedData[0].get("title"));
        Log.d("xml", "update\n" + feedData[0].get("src"));
        Log.d("xml", "update\n" + feedData[0].get("date"));
        Log.d("xml", "update\n" + feedData[0].get("source"));
        Log.d("xml", "update\n" + feedData[0].get("description"));
        Log.d("xml", "update\n" + feedData[0].get("link"));

        addToDB(feedData[0]);
         MainActivity ma=(MainActivity)context;
        ma.listAdapter.selectQuery();
       // ListAdapter la=new ListAdapter(context);
      // la.selectQuery();
       // la.notifyDataSetChanged();
        ((BaseAdapter) ma.list.getAdapter()).notifyDataSetInvalidated();
                ((BaseAdapter) ma.list.getAdapter()).notifyDataSetChanged();



    }


    public void addToDB(HashMap<String,String> feedData)
    {
        sqLiteHelper helper=null;
        SQLiteDatabase db=null;
        Cursor cursorAdapter=null;
        Cursor cursorTitle=null;
        ContentValues cvalue=new ContentValues();
        byte[] image=null;
        helper=new sqLiteHelper(context);
        db=helper.getWritableDatabase();

        //feedData.get("src"));
        if(feedData.get("title")!=null)
        {
            cursorTitle=db.query("NEWS_TABLE", new String[]{"TITLE"}, "TITLE="+"?", new String[]{feedData.get("title")}, null, null, null);
            Log.d("test", "image url " + feedData.get("src"));

         //   Log.d("test", db.query("NEWS_TABLE", new String[]{"TITLE"}, "TITLE="+"?", new String[]{feedData.get("title")}, null, null, null));
            if(cursorTitle.getCount()<1) {
                //list.add(feed);

                String src;
                 if(feedData.get("src").length()<6)
                 {
                       src="https://en.wikipedia.org/wiki/File:Google_News_Logo.png";
                    Log.d("test", "source url size if choosed" + feedData.get("src").length());
                 }
                 else
                 {
                     Log.d("test","source url size else choosed"+ feedData.get("src").length());
                     src=feedData.get("src");
                 }


                Log.d("test","source url size "+ src);
                ImageLoader imageLoader=new ImageLoader(src);
                Thread imageThread=new Thread(imageLoader);
                imageThread.start();
                try {
                   imageThread.join();
                }catch (Exception e)
                {
                    Log.d("test","imageThread.join()"+e);
                }
                    image=imageLoader.getImageArray();
                Log.d("test", "picture array" + image);
                cvalue.put("TITLE", feedData.get("title"));
                cvalue.put("DESCRIPTION", feedData.get("description"));
                cvalue.put("SOURCE", feedData.get("source"));


                cvalue.put("DATE",feedData.get("date"));
                cvalue.put("IMAGE",image);
                cvalue.put("LINK", feedData.get("link"));
                cvalue.put("TOPIC",topic);
                Log.d("test", "db saved" + db.insert("NEWS_TABLE", null, cvalue));
            }

        }
    }

    public void xmlParser() throws XmlPullParserException,IOException
    {
        Log.d("parse","xmlParser()");
        String name;
            XmlPullParser parser=Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new InputStreamReader(getStream(url)));
            parser.nextTag();
            parser.nextTag();
        while(parser.next()!= XmlPullParser.END_TAG)
        {
            Log.d("parse","nside while xmlParser()"+parser.getName());
            if(parser.getEventType()!=XmlPullParser.START_TAG)
            {
                Log.d("parse","nside while xmlParser() skip"+parser.getName());
                continue;
            }

            if(parser.getName().equalsIgnoreCase("item")) {
                Log.d("parse", parser.getName());
                parsePost(parser);

            }
            else
            {
                Log.d("parse","skipping"+parser.getName());
                skipParse(parser);
            }
        }

    }
    public void parsePost(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        parser.require(XmlPullParser.START_TAG, null, "item");
        Log.d("parse", "parsePost");
        HashMap<String,String> feedMap=new HashMap<>();
        parser.require(XmlPullParser.START_TAG, null, "item");
        while(parser.next()!=XmlPullParser.END_TAG)
        {
            HashMap<String,String> map=new HashMap<>();
            if(parser.getEventType()!=XmlPullParser.START_TAG)
            {
                continue;
            }
            String name=parser.getName();
            Log.d("parse","name="+name);
            if(name.equalsIgnoreCase("title"))
            {

                  parseTitle(parser) ;
                feedMap.put("title", TITLE);
               // Log.d("parse", "title matched");
            }
            else if(name.equals("pubDate"))
            {
                Log.d("parse", "pubDate matched");
                parseDate(parser) ;
                feedMap.put("date", DATE);
            }
            else if(name.equalsIgnoreCase("link"))
            {
                Log.d("parse", "link matched");
                parseLink(parser) ;

                feedMap.put("link", LINK);
            }
            else if(name.equalsIgnoreCase("description"))
            {
                 parseDescription(parser) ;
                // get detailed article full length image
                String l=null;
                webExtract web=new webExtract();
                switch(SOURCE) {
                    case "NDTV":

                        if((l=web.ndtvImage(LINK))!=null)
                        {
                            Log.d("webExtract", "switch back "+SRC);
                            SRC=l;
                            Log.d("webExtract", "switch back "+SRC);
                        }
                        break;
                }
                //test end
                feedMap.put("description", DESCRIPTION);
                feedMap.put("source", SOURCE);
                feedMap.put("src", "https:"+SRC);


            }
            else
            {
                skipParse(parser);
            }

        }
        Log.d("parse", "statement above publishProgress()");
        publishProgress(feedMap);
        Log.d("parse", "***********************************************************");
Log.d("parse",TITLE+" "+DATE+" "+SOURCE+" "+SRC+" "+LINK+" "+DESCRIPTION);
        Log.d("parse","***********************************************************");
    }

    public void parseDescription(XmlPullParser parser) throws XmlPullParserException,IOException
    {


       Log.d("parse","description");
                   if(parser.next()==XmlPullParser.TEXT)
                   {
                       parseHtml(parser.getText());
                   }

            parser.next();

    }

   public void parseHtml(String html)
    {
       // html = curChild.getTextContent();      //get html content inside description tag
        org.jsoup.nodes.Document htmldoc;
        htmldoc = Jsoup.parse(html);                  //parse string containing html
      //  Log.d("parse",htmldoc.toString());
        org.jsoup.select.Elements htmele = htmldoc.getElementsByTag("img");
       int  count = 0;
        for (org.jsoup.nodes.Element e : htmele) {
            if (count == 0) {
                SRC = e.attr("src");
            //    feedData.put("src", "http:" + SRC);
                // feed.setImage(imagearray = loadImage("http:" + SRC));
                Log.d("parse", "imgage="+SRC);
                break;
            }
            count++;
        }
        // extract description text from font tag
        org.jsoup.select.Elements desce = htmldoc.getElementsByTag("font");
          count = 0;
        for (org.jsoup.nodes.Element e : desce) {
            if (count == 4) {
                SOURCE = e.text();
            //    feedData.put("source", SOURCE);
                // feed.setSource(SOURCE);
                 Log.d("parse", SOURCE);
            }
            if (count == 5) {
                DESCRIPTION = e.text();
             //   feedData.put("description",DESCRIPTION);
                // feed.setDescription(DESCRIPTION);
                Log.d("parse", DESCRIPTION);
            }

            count++;
        }
    }
    public void parseTitle(XmlPullParser parser) throws XmlPullParserException,IOException
    {
Log.d("parse","parseTitle()");
            if(parser.next()==XmlPullParser.TEXT)
            {
              //  parser.require(XmlPullParser.START_TAG, null, "title");
                Log.d("parse", "parse title" + parser.getText());
                TITLE=parser.getText();
                parser.next();
                parser.require(XmlPullParser.END_TAG, null, "title");
                Log.d("parse","parse title /"+parser.getName());
            }



    }

    public void parseLink(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        Log.d("parse","parse link");
        if(parser.next()==XmlPullParser.TEXT)
        {
            //parser.require(XmlPullParser.START_TAG, null, "link");
            LINK=parser.getText();
            parser.next();
            Log.d("parse", "parse link"+LINK);
            parser.require(XmlPullParser.END_TAG, null, "link");
            Log.d("parse","parse link/");
        }



    }

    public void parseDate(XmlPullParser parser) throws XmlPullParserException,IOException
    {
            Log.d("parse","parseDate");

            if(parser.next()==XmlPullParser.TEXT)
            {
              //  parser.require(XmlPullParser.START_TAG, null, "pubDate");
                DATE=parser.getText();
                parser.next();
                Log.d("parse", "parseDate"+DATE);
                parser.require(XmlPullParser.END_TAG, null, "pubDate");
            }



    }
    public void skipParse(XmlPullParser parser) throws XmlPullParserException,IOException
    {

        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth=1;
        while(depth!=0)
        {
          //  Log.d("parse",parser.getName());
            switch(parser.next())
            {
                case XmlPullParser.START_TAG:depth++; Log.d("parse","switch start_tag"+parser.getName());
                    break;
                case XmlPullParser.END_TAG: depth--;Log.d("parse","switch eend_tag"+parser.getName());
                    break;
            }
        }
        Log.d("parse",parser.getName());
    }
    public InputStream getStream(URL url) throws MalformedURLException,IOException
    {

             con = url.openConnection();


        return con.getInputStream();
    }


}

