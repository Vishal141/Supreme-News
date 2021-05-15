package com.example.supremenews.Adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supremenews.R;
import com.example.supremenews.asynctasks.DownloadAsyncTask;
import com.example.supremenews.models.Global;
import com.example.supremenews.models.News;
import com.example.supremenews.ui.newsactivity.NewsActivity;

import java.time.LocalDateTime;

import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.ViewHolder> {

    private Context mContext;
    private LiveData<List<News>> mNews;

    public HomeNewsAdapter(Context mContext, LiveData<List<News>> mNews) {
        this.mContext = mContext;
        this.mNews = mNews;
    }

    public void setmNews(LiveData<List<News>> mNews){
        this.mNews = mNews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = mNews.getValue().get(position);
        String date = news.getPublished_at().substring(0,10);
        String time = news.getPublished_at().substring(11,16);
        //System.out.println(date);
        //System.out.println(time);
        holder.date.setText(getTimeDiff(date,time));
        holder.title.setText(news.getTitle());
        Glide.with(mContext).load(news.getImage()).into(holder.newsImage);

        holder.topRel.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,NewsActivity.class);
            Global.news = news;
            mContext.startActivity(intent);
        });

        holder.image_download.setOnClickListener(v -> {
            DownloadAsyncTask task = new DownloadAsyncTask(mContext,news);
            task.execute();
        });
    }

    public static String getTimeDiff(String date, String time){
        String ans="just now";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            LocalDateTime dateTime = null;
            dateTime = LocalDateTime.now();
            int yy = Integer.parseInt(date.substring(0,4));
            int mm = Integer.parseInt(date.substring(5,7));
            int dd = Integer.parseInt(date.substring(8));
            int DD = dateTime.getDayOfMonth()-dd;
            int MM = dateTime.getMonthValue()-mm;
            int YY = dateTime.getYear()-yy;
            if(YY!=0)
                return YY+" years ago";
            if(MM!=0)
                return MM+" months ago";
            if(DD!=0)
                return DD+" days ago";

            int hh = dateTime.getHour() - Integer.parseInt(time.substring(0,2));
            if(hh!=0)
                return hh+" hours ago";

            int mi = dateTime.getMinute() - Integer.parseInt(time.substring(3));
            if(mi!=0)
                return mi+" minutes ago";
        }

        return ans;
    }

    @Override
    public int getItemCount() {
        return mNews.getValue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,title;
        ImageView newsImage,image_like,image_download;
        RelativeLayout topRel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.published_date);
            title = itemView.findViewById(R.id.news_title);
            newsImage = itemView.findViewById(R.id.news_image);
            image_like = itemView.findViewById(R.id.image_like);
            topRel = itemView.findViewById(R.id.top_rel);
            image_download = itemView.findViewById(R.id.image_download);
        }
    }
}
