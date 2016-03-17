package app.com.example.sujay.newsapp;

import android.graphics.Color;

/**
 * Created by sujay on 15-03-2016.
 */
public class colorChooser {
    public static int changeColor(String color)
    {
        switch( color)
        {
            case "RED": return Color.RED;

            case "GREEN":return Color.GREEN;

            case "BLUE": return Color.BLUE;
            case "YELLOW": return Color.YELLOW;
            case "WHITE":return Color.WHITE;
            case "GRAY": return Color.GRAY;
            case "DKGRAY":return Color.DKGRAY;
            case "MAGENTA":return Color.MAGENTA;
            case "LTGRAY": return Color.LTGRAY;
            case "TRANSPARENT": return Color.TRANSPARENT;
            case "CYAN": return Color.CYAN;
            case "BLACK": return Color.BLACK;
        }
        return  Color.BLACK;
    }
}
