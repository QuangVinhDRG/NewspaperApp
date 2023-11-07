package com.example.newspaperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ListView lvTitle;
    ArrayList<String> arrayLink, arrayDescription, arrayImageLink, arrayTest;
    CustomListViewAdapter customListViewAdapter;
    ArrayList<News> newsArrayList = new ArrayList<>();
    String dateTimeFormatStr = "Mon, 06 Nov 2023 19:57:18";
    String imageLinkStartStr = "img src=\"";
    String imageLinkEndStr = "\" ></a>";
    String descStartStr = "</br>";
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTitle = findViewById(R.id.listViewTitle);
        arrayLink = new ArrayList<>();
        arrayTest = new ArrayList<>();
        arrayDescription = new ArrayList<>();
        arrayImageLink = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTest);
        customListViewAdapter = new CustomListViewAdapter(newsArrayList);
        lvTitle.setAdapter(customListViewAdapter);
        new ReadRSS().execute("https://vnexpress.net/rss/khoa-hoc.rss");
        lvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("newsLink", arrayLink.get(position));
                startActivity(intent);
            }
        });
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String title = parser.getValue(element, "title");
                NodeList descContent = element.getElementsByTagName("description");
                Element descLine = (Element) descContent.item(0);
                String descString = getCharacterDataFromElement(descLine);
                String imageLink = descString.substring(descString.indexOf(imageLinkStartStr) + imageLinkStartStr.length(), descString.indexOf(imageLinkEndStr));
                String description =  descString.substring(descString.indexOf(descStartStr) + descStartStr.length());
                String releaseTime = parser.getValue(element, "pubDate").substring(0, dateTimeFormatStr.length());
                newsArrayList.add(new News(title, description, imageLink, releaseTime));
                arrayLink.add(parser.getValue(element, "link"));
            }
            customListViewAdapter.notifyDataSetChanged();

//            Toast.makeText(MainActivity.this, title, Toast.LENGTH_LONG).show();
        }
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}