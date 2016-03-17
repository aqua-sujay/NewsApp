package app.com.example.sujay.newsapp;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sujay on 10-01-2016.
 */
public class ImageLoader  implements Runnable{
    public byte[] bits=null;
    String src=null;
    public ImageLoader(String src)
    {
        this.src=src;
        Log.d("xml","***********"+src);
    }
    public byte[] getImageArray()
    {
        return bits;
    }
    public void run()
    {



            URL url=null;
            InputStream is=null;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
            try {
                url = new URL(src);
            }  catch (Exception e)
            {
                Log.d("test", "1 here" + e);
            }
            HttpURLConnection con=null;
            int concount=0;

            while(con==null) {
                try{
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Accept-Encoding", "identity");  //disable gzip compression to get contentlength
                    is=con.getInputStream();
                }  catch (Exception e)
                {
                    Log.d("test","2 here"+e);
                }
                bos.reset();
                int read=-1;
                Log.d("test", "content length" + con.getContentLength());

               // bits=new byte[con.getContentLength()];
              //  int count=0;
                try{
                    while((read=is.read())!=-1)
                    {
                        bos.write((byte)read);
                        //bits[count++]=(byte)read;
                    }
                    bits=bos.toByteArray();
                }  catch (Exception e)
                {
                    Log.d("test","3 here"+e);
                }
                concount++;
                con.disconnect();
            }

        Log.d("test","picture in function array"+bits);
            Log.d("test", "loadimage" + concount);


    }
}
