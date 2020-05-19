package com.mtv.news.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.mtv.news.entity.New;

public interface AdapterListener {

    public void onItemClickListener(Object o , int pos, RecyclerView.ViewHolder holder);
    public void onClick(New n);

}
