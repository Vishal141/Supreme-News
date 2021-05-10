package com.example.supremenews.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supremenews.R;
import com.example.supremenews.models.Global;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private Context mContext;
    private List<String> mList;

    public CategoryAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.un_selected.setText(mList.get(position));
        holder.selected.setText(mList.get(position));
        if (position==0)
        {
            Global.prevSelected = holder.itemView;
            holder.un_selected.setVisibility(View.GONE);
            holder.selected.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selected,unSelected;
                selected = Global.prevSelected.findViewById(R.id.category_text_selected);
                unSelected = Global.prevSelected.findViewById(R.id.category_text_un_selected);
                selected.setVisibility(View.GONE);
                unSelected.setVisibility(View.VISIBLE);
                Global.prevSelected = holder.itemView;
                holder.selected.setVisibility(View.VISIBLE);
                holder.un_selected.setVisibility(View.GONE);

                //TODO other functionality

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView selected,un_selected;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selected = itemView.findViewById(R.id.category_text_selected);
            un_selected = itemView.findViewById(R.id.category_text_un_selected);
        }
    }
}
