package com.example.newspaperapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {
    List<News> list;

    public CustomListViewAdapter(List<News> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);
        TextView tvNewsTitle = view.findViewById(R.id.tvNewsTitle);
        ImageView ivNewsImage = view.findViewById(R.id.ivNewsImage);
        TextView tvNewsDesc = view.findViewById(R.id.tvNewsDesc);
        TextView tvReleaseTime = view.findViewById(R.id.tvReleaseTime);
        News news = list.get(position);
        tvNewsTitle.setText(news.getTitle());
        tvNewsDesc.setText(news.getDescription());
        String bitmapLink = news.getBitmapLink();
        Picasso.get().load(bitmapLink).into(ivNewsImage);
        tvReleaseTime.setText(news.getReleaseTime());
        return view;
    }
}
