package com.example.readnews.ui.news;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readnews.R;

import java.util.List;

/**
 * Created by QiWangming on 2015/6/13.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.NewsViewHolder> {

    private List<News> newses;
    private Context context;

    public MyRecycleViewAdapter(List<News> newses, Context context) {
        this.newses = newses;
        this.context = context;
    }


    //自定义ViewHolder类
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView news_photo;
        TextView news_title;
        TextView news_desc;


        public NewsViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            news_photo = (ImageView) itemView.findViewById(R.id.news_photo);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_desc = (TextView) itemView.findViewById(R.id.news_desc);
            //设置TextView背景为半透明
            news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));

        }


    }

    @Override
    public MyRecycleViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(MyRecycleViewAdapter.NewsViewHolder personViewHolder, int i) {
        personViewHolder.news_photo.setImageResource(newses.get(i).getPhotoId());
        personViewHolder.news_title.setText(newses.get(i).getTitle());
        personViewHolder.news_desc.setText(newses.get(i).getDesc());
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }
}
