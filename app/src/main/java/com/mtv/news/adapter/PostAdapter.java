package com.mtv.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtv.news.R;
import com.mtv.news.entity.New;
import com.mtv.news.file.DownloadImageTask;
import com.mtv.news.interfaces.AdapterListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<New> newList;
    private AdapterListener listener;

    Context context;

    public PostAdapter(List<New> newList, AdapterListener listener, Context context) {
        this.newList = newList;
        this.listener = listener;
        this.context = context;
    }

    public PostAdapter(Context context , List<New> newList) {
        this.context = context;
        this.newList = newList;
    }

    public PostAdapter(List<New> newList) {
        this.newList = newList;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,null);
        PostViewHolder postViewHolder = new PostViewHolder(v);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
//        Picasso.with(context)
//                .load("https://bom.to/Abz1xM")
//                .resize(750,452).noFade().into(holder.imgThumb);
        Picasso.with(context)
                .load(newList.get(position).getUrlImg())
                .resize(750,452).noFade().into(holder.imgThumb);
        holder.tvTitle.setText(newList.get(position).getName());
        holder.tvDesc.setText(newList.get(position).getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(newList.get(position));
            }
        });
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumb;
        public TextView tvTitle;
        public TextView tvDesc;

        public PostViewHolder( View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.img_thumb);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }
}
