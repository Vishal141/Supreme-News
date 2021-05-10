package com.example.supremenews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.supremenews.R;
import com.example.supremenews.models.Global;
import com.example.supremenews.models.News;
import com.example.supremenews.ui.newsactivity.NewsActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
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
        holder.date.setText(date + " "+ time);
        holder.title.setText(news.getTitle());
        holder.subtitle.setText(news.getSubtitle());
        Glide.with(mContext).load(news.getImage()).into(holder.newsImage);

        holder.topRel.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,NewsActivity.class);
            Global.news = news;
            mContext.startActivity(intent);
        });

        holder.image_download.setOnClickListener(v -> {
            try{
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    File dataDir = mContext.getDataDir();
                    String downloadPath = dataDir.getAbsolutePath().toString()+File.separator+"download";
                    File downloadDir = new File(downloadPath);
                    if(!downloadDir.exists())
                    {
                        if(downloadDir.mkdir())
                            System.out.println("created");
                    }

                    File file = new File(downloadDir+File.separator+news.get_id()+".ser");
                    if(!file.exists()){
                        if(file.createNewFile())
                            System.out.println("created");
                    }

                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
                    os.writeObject(news);
                    os.flush();
                    os.close();
                    Toast.makeText(mContext,"download successful",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNews.getValue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,title,subtitle;
        ImageView newsImage,image_like,image_download;
        RelativeLayout topRel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.published_date);
            title = itemView.findViewById(R.id.news_title);
            subtitle = itemView.findViewById(R.id.news_subtitle);
            newsImage = itemView.findViewById(R.id.news_image);
            image_like = itemView.findViewById(R.id.image_like);
            topRel = itemView.findViewById(R.id.top_rel);
            image_download = itemView.findViewById(R.id.image_download);
        }
    }
}
