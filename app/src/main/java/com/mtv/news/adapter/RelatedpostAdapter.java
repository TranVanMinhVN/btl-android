package com.mtv.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtv.news.R;
import com.mtv.news.entity.New;
import com.mtv.news.interfaces.AdapterListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RelatedpostAdapter  extends RecyclerView.Adapter<RelatedpostAdapter.RelatepostViewHolder>{

    private List<New> newList;
    private AdapterListener listener;


    public List<New> getNewList() {
        return newList;
    }

    public void setNewList(List<New> newList) {
        this.newList = newList;
    }

    Context context;

    public RelatedpostAdapter(List<New> newList, Context context) {
        this.newList = newList;
        this.context = context;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatepostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_relatedpost,null);
        RelatepostViewHolder relatepostViewHolder = new RelatepostViewHolder(v);
        return relatepostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RelatepostViewHolder holder, final int position) {
        Picasso.with(context)
                .load(newList.get(position).getUrlImg())
                .resize(100,70).noFade().into(holder.imgThumb2);
        holder.tvTitle2.setText(newList.get(position).getName());
        holder.tvNote.setText(newList.get(position).getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(newList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }
    public class RelatepostViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumb2;
        public TextView tvTitle2;
        public TextView tvNote;

        public RelatepostViewHolder( View itemView) {
            super(itemView);
            imgThumb2 = itemView.findViewById(R.id.img1);
            tvTitle2 = itemView.findViewById(R.id.tv1);
            tvNote = itemView.findViewById(R.id.tv_note);
        }
    }
}
