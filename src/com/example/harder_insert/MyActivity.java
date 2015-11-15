package com.example.harder_insert;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MyActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */

    String t ;
    ListView txt;
    public static boolean a = false;
    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SQLiteDatabase db;
        String TABLE_NAME ="books";
        String col_1 = "author";
        String col_2 = "title";
        String col_3 = "genre";
        String col_4 = "price";
        String col_5="publish date";

        String sdCard = "data/data/com.example.harder_insert";
        String dbpath = sdCard + "/mydb.db";

        db = SQLiteDatabase.openDatabase(dbpath,null,SQLiteDatabase.CREATE_IF_NECESSARY);

       db.execSQL("drop table "+ TABLE_NAME + ";");

        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_1 + " TEXT ," + col_2 + " TEXT ," + col_3 + " TEXT ," + col_4 + " TEXT );");

        try {
            t = getXML(this);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        String[] items = t.split("\n" + " ");
        //String[] second = items[1].split(" ");

        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));

        ContentValues[] cv = new ContentValues[items.length];

        //System.out.print(items);
        //System.out.println(a);

        if(items.equals("genre")) {

            for (int i = 0; i < items.length; i++) {
                String temp = items[i];
                ContentValues c = new ContentValues();
                c.put(col_1, temp);
                cv[i] = c;
            }
            if (db != null) {
                for (int i = 0; i < cv.length; i++) {
                    db.insert(TABLE_NAME, null, cv[i]);
                }
            }

        }
       // for(int i =0;i<items.length;i++)
        //txt.setText(items[i]+ "\n");
    }

    public String getXML(Activity activity) throws IOException, XmlPullParserException {
        Resources res = activity.getResources();
        StringBuffer sb = new StringBuffer();


        XmlPullParser xr = res.getXml(R.xml.books);

        String author=null, title=null,genre=null,price=null,publish_date=null;

        xr.next();

        int event = xr.getEventType();

        while(event!=xr.END_DOCUMENT)
        {
            if(event==xr.START_TAG) {
                if (xr.getName().equals("book")) {
                    sb.append("\n");
                    sb.append(xr.getAttributeValue(null, "id") + "\n");
                } else if (xr.getName().equals("author")) {
                    author = xr.nextText();
                    sb.append("\n" + "author      " + author);
                   // return sb.toString();
                } else if (xr.getName().equals("title")) {
                    title = xr.nextText();
                    sb.append("\n" + title);
                } else if (xr.getName().equals("genre")) {
                    genre = xr.nextText();
                  // sb.append("genre");
                    sb.append("\n"  + "genre " + genre);
                } else if (xr.getName().equals("price")) {
                    price = xr.nextText();
                    sb.append("\n" + price);
                } else if (xr.getName().equals("publish_date")) {
                    publish_date = xr.nextText();
                    sb.append("\n" + publish_date);
                }
            }
         //   else if (event == XmlPullParser.TEXT) {
                //sb.append("\n" + xr.getText());

           // }

           event = xr.next();
          //  return author.toString();
        }
   //
       //  return null;
       // System.out.println(author);
       return sb.toString();


    }
}
