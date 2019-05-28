package com.example.trabalho2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trabalho2.R;
import com.example.trabalho2.model.Datum;
import com.example.trabalho2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items;
    private int rowLayout;
    private Context context;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemsLayout;
        TextView itemName;
        TextView data;
        TextView movieDescription;
        TextView rating;


        public ItemViewHolder(View v) {
            super(v);
            itemsLayout = (LinearLayout) v.findViewById(R.id.items_layout);
            itemName = (TextView) v.findViewById(R.id.name);
//            data = (TextView) v.findViewById(R.id.subtitle);
//            movieDescription = (TextView) v.findViewById(R.id.description);
//            rating = (TextView) v.findViewById(R.id.rating);
        }
    }

    public ItemsAdapter(List<Datum> datums, int rowLayout, Context context) {
        this.items = new ArrayList<Item>();

        for (Datum datum : datums) {
            this.items.add(datum.getItem());
        }
        
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.itemName.setText(items.get(position).getName());
//        holder.data.setText(items.get(position).getReleaseDate());
//        holder.movieDescription.setText(items.get(position).getOverview());
//        holder.rating.setText(items.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}